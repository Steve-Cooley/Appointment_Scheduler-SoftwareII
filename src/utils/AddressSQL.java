package utils;

import java.sql.*;
import java.time.LocalDateTime;

public class AddressSQL {

    // delete?  fixme
    public static String getPhone(int id) {
        String phone = "Phone not retrieved";
        String selectStatment = "SELECT phone FROM address WHERE addressId = ?";

        Connection conn = DBConnection.startConnection();
        try {
            //DBQuery.setPreparedStatement(conn, selectStatment);
            PreparedStatement ps = conn.prepareStatement(selectStatment);
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

    public static int insertAddressAndReturnID(String addr, String phone) {
        int lastInsertedId = -1;
        try {
            String insertAddr = "Insert INTO address(address, phone, address2, cityId, postalCode, createDate," +
                    " createdBy, lastUpdate, lastUpdateBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
//            DBQuery.setPreparedStatement(conn, insertAddr);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = conn.prepareStatement(insertAddr, Statement.RETURN_GENERATED_KEYS);

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

            DBConnection.closeConnection();
        } catch(SQLException e) {
            System.out.println("SQLException, save button, insert addr");
            e.printStackTrace();
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return lastInsertedId;
    }

}
