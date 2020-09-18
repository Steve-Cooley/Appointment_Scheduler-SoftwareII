package utils;

import Model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

public class CustomerSQL {
    public static void insertCustomer(String name, int addrId) {
        System.out.println("insert customer is running");
        try {
            String insertString = "INSERT INTO customer(customerName, addressId," +
                    "active, createDate, createdBy, lastUpdate, lastUpdateBy) values(?, ?, ?, ?, ?, ?, ?)";
            Connection conn = DBConnection.startConnection();
//            DBQuery.setPreparedStatement(conn, insertString);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
            PreparedStatement ps = conn.prepareStatement(insertString, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            ps.setInt(2, addrId);
            //filler fields
            ps.setInt(3, 0);   //todo not sure what active is supposed to do, might need to fix
            ps.setString(4, LocalDateTime.now().toString());
            ps.setString(5, "Temp User");  //createdBy //fixme  probably need to implement this once I have users
            ps.setString(6, LocalDateTime.now().toString()); //lastUpdate
            ps.setString(7, "TempUser2");  //lastUpdateBy //todo

            ps.execute();

            DBConnection.closeConnection(); // not necessary bc connection was opened in calling function.  Might change
        } catch (SQLException e) {
            System.out.println("SQLException, save button, insert user");
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //deletes both customer and address
    public static void deleteCustomer(int id, int addressId) {
        try {
            Connection conn = DBConnection.startConnection();
            String delStatement = "DELETE FROM customer WHERE customerId = ?";
//            DBQuery.setPreparedStatement(conn, delStatement);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
            PreparedStatement ps = conn.prepareStatement(delStatement);
            ps.setInt(1, id);
            ps.execute();
            // This is probably not necessary, but I'll attempt to delete the address as well fixme
            String delAddress = "DELETE FROM address WHERE addressId = ? ";
//            DBQuery.setPreparedStatement(conn, delAddress);
//            ps = DBQuery.getPreparedStatement();
            ps = conn.prepareStatement(delAddress);
            ps.setInt(1, addressId);
            ps.execute();
            DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customer customer, String newName, String newAddress, String newPhone) {
        // identifying values
        int customerId = customer.getId();
        int addressId = customer.getAddressId();

        //new values

        String cStatement = "UPDATE customer SET customerName = ? WHERE customerId = ?; ";
        String aStatement = "UPDATE address SET address = ?, phone = ? WHERE addressId = ?;";

        try {
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = conn.prepareStatement(cStatement);
            ps.setString(1, newName);
            ps.setInt(2, customerId);
            ps.execute();
            DBConnection.closeConnection();
        } catch(Exception e) {
            System.out.println("problem updating name");
            e.printStackTrace();
        }

        try {
            Connection conn = DBConnection.startConnection();
//            DBQuery.setPreparedStatement(conn, aStatement);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
            PreparedStatement ps = conn.prepareStatement(aStatement);
            ps.setString(1, newAddress);
            ps.setString(2, newPhone);
            ps.setInt(3, addressId);
            ps.execute();
            DBConnection.closeConnection();
        } catch(Exception e) {
            System.out.println("Problem updating address");
            e.printStackTrace();
        }
    }
}
