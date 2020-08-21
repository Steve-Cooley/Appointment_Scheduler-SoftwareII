package utils;

import Model.Address;

import java.sql.*;
import java.time.LocalDateTime;

public class DBQuery {

    private static PreparedStatement statement;

    // create statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS); //
        //added ', Statement.RETURN_GENERATED_KEYS' in an attempt to fix external key problems.  Might not work.
    }

    //Return statement Object
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

    public static String getAddressString(int id) {
        System.out.println("DBQuery.getAddress is running");
        String addr = "Address was not retrieved";  // Initialized to informative string
        String selectStatement = "SELECT address FROM address WHERE addressId = ?";


        Connection conn = DBConnection.startConnection();
        try {
            setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = getPreparedStatement();
            ps.setInt(1, id);
            System.out.println(ps.toString());
            ps.execute();
            ResultSet rs = ps.getResultSet();

            if (rs.next()) {
                addr = rs.getString("address");
            }

            DBConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println("SQLException in  DBQuery.getAddressString");
            e.printStackTrace();

        } catch (Exception e) {
            System.out.println("Exception in DBQuery.getAddressString");
            e.printStackTrace();
        }
        System.out.println(addr);
        return addr;
    }




    //      address related

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
        } catch (SQLException throwables) {
            System.out.println("SQLException, save button, get addressId ");
            throwables.printStackTrace();
        }

        return addrId;  //fixme
    }

    // fixme Should this return the address number? I think it might prevent errors, and also eliminate the need for getAddrID
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

    //      Customer related

    public static void insertCustomer(String name, int addrId, Connection conn) {
        System.out.println("insert customer is running");
        try {
            String insertString = "INSERT INTO customer(customerName, addressId," +
                    "active, createDate, createdBy, lastUpdate, lastUpdateBy) values(?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertString);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setInt(2, addrId);
            //filler fields
            ps.setInt(3, 0);   //todo not sure what active is supposed to do, might need to fix
            ps.setString(4, LocalDateTime.now().toString());
            ps.setString(5, "Temp User");  //createdBy //fixme  probably need to implement this once I have users
            ps.setString(6, LocalDateTime.now().toString()); //lastUpdate
            ps.setString(7, "TempUser2");  //lastUpdateBy //todo

            ps.execute();

        } catch (SQLException e) {
            System.out.println("SQLException, save button, insert user");
            e.printStackTrace();
        }
    }


}
