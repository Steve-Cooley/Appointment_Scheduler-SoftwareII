package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AppointmentSQL {

    public static void deleteFromDbByCustID(int customerId) {
        try {
            Connection conn = DBConnection.startConnection();
            String delStatement = "DELETE FROM appointment WHERE customerId = ?";
            PreparedStatement ps = conn.prepareStatement(delStatement);

            ps.setInt(1, customerId);
            ps.execute();

            DBConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void deleteFromDbByAppointmentID(int appointmentId) {
            System.out.println("delete by appt id is running. ID number is: " + appointmentId);
        try {
            Connection conn = DBConnection.startConnection();
            String delStatement = "DELETE FROM appointment WHERE appointmentId = ?";
            PreparedStatement ps = conn.prepareStatement(delStatement);

            ps.setInt(1, appointmentId);
            ps.execute();

            DBConnection.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAppointment(int customerId, int userId, String typeOfAppointment, Timestamp start,
                                              Timestamp end, int apptId){
        String sqlStatement = "UPDATE appointment SET " +
                "customerId = ?, " +
                "title = ?, " +  // pretty sure title is the right field.  todo test
                "start = ?, " +
                "userId = ? " +
                "WHERE appointmentId = ?";
        System.out.println("sqlStatement: " + sqlStatement);
        try {
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            ps.setInt(1, customerId);
            ps.setString(2, typeOfAppointment);
            ps.setTimestamp(3, start);
            ps.setInt(4, userId);
            ps.setInt(5, apptId);
            ps.execute();
            System.out.println(ps.toString());
            DBConnection.closeConnection();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void insertAppointmentsToDB(int customerId, int userId, String typeOfAppointment, Timestamp start,
                                              Timestamp end) {
        System.out.println("Insert appointments to db is running");
        String sqlInsert =
                "INSERT INTO appointment" +
                        "(customerId, userId, title, description, location, contact, type, url, start, end, " +
                        "createDate, createdBy, lastUpdate, lastUpdateBy) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        System.out.println(sqlInsert);
        try {
            Connection conn = DBConnection.startConnection();
            //DBQuery.setPreparedStatement(conn, sqlInsert);
            //PreparedStatement ps = DBQuery.getPreparedStatement();
            PreparedStatement ps = conn.prepareStatement(sqlInsert);

            ps.setInt(1, customerId);
            ps.setInt(2, userId);
            ps.setString(3, typeOfAppointment);  // Using title as "type of appointment" (should meet reqs)  ***
            ps.setString(4, typeOfAppointment); //description
            ps.setString(5, typeOfAppointment); //location
            ps.setString(6, typeOfAppointment); //contact
            ps.setString(7, typeOfAppointment); //type
            ps.setString(8, typeOfAppointment); //url
            ps.setTimestamp(9, start);          //start  ***
            ps.setTimestamp(10, end);           //end    ***
            ps.setTimestamp(11, start);         //createdate
            ps.setString(12, "");         //createBy
            ps.setTimestamp(13, start);
            ps.setString(14,"");

            ps.execute();

            DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
