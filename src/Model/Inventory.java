package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.AddressSQL;
import utils.CustomerSQL;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Inventory {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList getCustomers() {
        return customers;
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
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

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

    public static void fetchAppointmentsFromDB() {
        // From appointments
        appointments.clear();
        int appointmentId;
        int customerId;
        int userId;
        LocalDateTime start;
        LocalDateTime end;
        // From customers
        String customerName;

        String selectStatement =
                "SELECT appointment.appointmentId, appointment.customerId, appointment.userId, " +
                    "appointment.title, appointment.start, appointment.end, customer.customerName" +
                "FROM appointment, customer" +
                "WHERE appointment.customerId = customer.customerId ";

        try {
            Connection conn = DBConnection.startConnection();
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();
            // go through field in the DB, adding a new appointment to appointments
            while (rs.next()) {
                appointmentId = rs.getInt("appointmentId");
                customerId = rs.getInt("customerId");
                userId = rs.getInt("userId");
                java.sql.Timestamp ts = rs.getTimestamp("start");
                Instant instant = ts.toInstant();
                ZoneId z = ZoneId.of( "Globals.LOCALZONEID" ) ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}