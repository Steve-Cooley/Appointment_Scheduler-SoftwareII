package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AddressSQL {

    // delete?  fixme
    public static String getPhone(int id) {
        String phone = "Phone not retrieved";
        String selectStatment = "SELECT phone FROM address WHERE addressId = ?";

        Connection conn = DBConnection.startConnection();
        try {
            DBQuery.setPreparedStatement(conn, selectStatment);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            if (rs.next()) {
                phone = rs.getString("phone");
            }
            DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return phone;
    }

    // Deprecate?  fixme
    public static String getAddressString(int id) {
        System.out.println("DBQuery.getAddress is running");
        String addr = "Address was not retrieved";  // Initialized to informative string
        String selectStatement = "SELECT address FROM address WHERE addressId = ?";


        Connection conn = DBConnection.startConnection();
        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                addr = rs.getString("address");
            }

            DBConnection.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(addr);
        return addr;
    }

    // delete?  fixme
    public static int getAddressID(String addr, int addrId, Connection conn) {
        System.out.println("getAddressID is running");
        try {
            String selectS = "SELECT addressId FROM address WHERE address = ?";
            DBQuery.setPreparedStatement(conn, selectS);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, addr);

            ps.execute();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                addrId = rs.getInt("addressId");
                System.out.println("The address ID is: " + addrId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return addrId;  //fixme
    }

    public static int insertAddressAndReturnID(String addr, String phone, Connection conn) {
        int lastInsertedId = -1;
        try {
            String insertAddr = "Insert INTO address(address, phone, address2, cityId, postalCode, createDate," +
                    " createdBy, lastUpdate, lastUpdateBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            DBQuery.setPreparedStatement(conn, insertAddr);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Normal fields
            ps.setString(1, addr);
            ps.setString(2, phone);
            //Filler fields
            ps.setString(3, "0"); //address 2
            ps.setInt(4, 1);  //cityId
            ps.setString(5, "0"); //postalCode
            ps.setString(6, LocalDateTime.now().toString()); //createDate
            ps.setString(7, "0");  //createdBy
            ps.setString(8, LocalDateTime.now().toString()); //lastUpdate
            ps.setString(9, "0"); //lastUpdateBy

            System.out.println("Insert String is: \n" + ps.toString());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                lastInsertedId = rs.getInt(1);
                System.out.println("***Is this the address key? " + lastInsertedId);
            }
        } catch(SQLException e) {
            System.out.println("SQLException, save button, insert addr");
            e.printStackTrace();
        }
        return lastInsertedId;
    }

}
