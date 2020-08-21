package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.DBConnection;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Inventory {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    public static ObservableList getCustomers() {
        return customers;
    }

    //      Customer section
    // fetch all customers (from DB)
    public static void fetchCustomersFromDB() {
        customers.clear();
        //System.out.println("Just ran custemers.removeAll.  What remains is: " + customers);
        int customerId;
        String customerName;

        System.out.println("fetchCustomers for table is running");

        Connection conn = DBConnection.startConnection();
        String selectStatement = "SELECT * FROM customer";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            //System.out.println("fetch customer string: " + );

            ps.execute();

            ResultSet rs = ps.getResultSet();

            // go through each line in DB, adding a new customer to customers
            while (rs.next()) {
                //System.out.println("while loop, fetchCustomerFromDB");
                customerId = rs.getInt("customerId");
                //System.out.println(customerId);
                customerName = rs.getString("customerName");
                //System.out.println(customerName);
                //addressId = rs.getInt("addressId");

                Customer cust = new Customer(customerId, customerName);
                customers.add(cust);
            }
            //System.out.println("List of customers: " + customers.toString());
        } catch(SQLException e) {
            System.out.println("SQLException, fetchCustomer, Inventory");
        }
    }
    // get all customers
    // add customer todo is this necessary?  I have this in the customerController.  Should it be here?
    // delete customer
    // modify customer

    //      User section

    //      Appointment section
    // fetch all appointments (from db)
    // get all appointments
    // add appointment
    // delete appointment
    // modify appointment

}
