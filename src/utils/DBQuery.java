package utils;

import Model.Address;

import java.sql.*;
import java.time.LocalDateTime;

public class DBQuery {

    private static PreparedStatement statement;

    // create statement object
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
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

    public static int getAddressID() {
        return 0;  //fixme
    }

    public static void insertAddressString(String addr, String phone) {
        try {
            Connection conn = DBConnection.startConnection();
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
        } catch(SQLException e) {
            System.out.println("SQLException, save button, insert addr");
        }

    }



}
