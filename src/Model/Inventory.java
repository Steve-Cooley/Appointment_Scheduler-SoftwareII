package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.*;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Inventory {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static User activeUser;

    public static ObservableList getCustomers() {
        return customers;
    }

    public static ObservableList getAppointments() {
        return appointments;
    }

    public static void clearCustomers() {
        customers.clear();
    }

    //      Customer section
    // fetch all customers (from DB)
    public static void fetchCustomersFromDB() {
        clearCustomers();  //fixme this could be replaced by customers.clear() and method deleted.  Just don't want to play with it now, cuz it'll need to be tested.
        int customerId;
        String customerName;
        int addressId;
        String address;
        String phone;

        System.out.println("fetchCustomers for table is running");

        Connection conn = DBConnection.startConnection();
        String selectStatement = "SELECT customerId, customerName, customer.addressId, address.address, address.phone" +
                " FROM customer, address" +
                " WHERE customer.addressId = address.addressId;";

        try {
//            DBQuery.setPreparedStatement(conn, selectStatement);
//            PreparedStatement ps = DBQuery.getPreparedStatement();
            PreparedStatement ps = conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();

            // go through each field in DB, adding a new customer to customers
            while (rs.next()) {
                customerId = rs.getInt("customerId");
                customerName = rs.getString("customerName");
                addressId = rs.getInt("addressId");
                address = rs.getString("address");
                phone = rs.getString("phone");

                Customer cust = new Customer(customerId, customerName, addressId,
                        address, phone);
                customers.add(cust);
            }
            DBConnection.closeConnection();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void delCustomer(Customer customer) {
        customers.remove(customer);
    }

    //      Appointment section

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

    public static void fetchAppointmentsFromDB() {
        System.out.println("fetchAppointmentsFromDB is running");
        //DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"); //S.D. used "kk" instead of 'HH'
        // From appointments
        appointments.clear();
        int appointmentId;
        int customerId;
        int userId;
        String title = "";
        //time stuff
        LocalDateTime localStartTime;
        LocalDateTime localEndTime;
        // From customers
        String customerName;

        String selectStatement =
                "SELECT appointment.appointmentId, appointment.customerId, appointment.userId, " +
                    "appointment.title, appointment.start, appointment.end, customer.customerName " +
                "FROM appointment, customer " +
                "WHERE appointment.customerId = customer.customerId; ";
        //System.out.println("select statement is: " + selectStatement);

        try {
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = conn.prepareStatement(selectStatement);

            ps.execute();

            ResultSet rs = ps.getResultSet();


            // go through field in the DB, adding a new appointment to appointments
            while (rs.next()) {
                appointmentId = rs.getInt("appointmentId");
                customerId = rs.getInt("customerId");
                userId = rs.getInt("userId");
                java.sql.Timestamp st = rs.getTimestamp("start");
                java.sql.Timestamp et = rs.getTimestamp("end");
                customerName = rs.getString("customerName");
                //todo appointment type
                //System.out.println("@@@@@ customer name: " + customerName);

                localStartTime = TimeMachine.utcToLocal(st);
                localEndTime = TimeMachine.utcToLocal(et);

                // make the objects
                Appointment appointment = new Appointment(appointmentId, customerId, customerName, userId, title,
                        localStartTime, localEndTime);
                appointments.add(appointment);
                //System.out.println("appointments: " + appointments.toString());
            }

            DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setActiveUser(User user) {
        System.out.println("setActiveUser is running");
        activeUser = user;
        System.out.println("the active user is: " + user.userName);
    }

    public static void removeActiveUser() {
        activeUser = null;
        System.out.println("logged out");
    }

}