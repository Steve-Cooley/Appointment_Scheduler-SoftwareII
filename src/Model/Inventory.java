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
        clearCustomers();
        int customerId;
        String customerName;
        int addressId;

        System.out.println("fetchCustomers for table is running");

        Connection conn = DBConnection.startConnection();
        String selectStatement = "SELECT * FROM customer";

        try {
            DBQuery.setPreparedStatement(conn, selectStatement);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.execute();

            ResultSet rs = ps.getResultSet();

            // go through each line in DB, adding a new customer to customers
            while (rs.next()) {
                //System.out.println("while loop, fetchCustomerFromDB");
                customerId = rs.getInt("customerId");
                //System.out.println(customerId);
                customerName = rs.getString("customerName");
                //System.out.println(customerName);
                addressId = rs.getInt("addressId");

                //Customer cust = new Customer(customerId, customerName, addressId);
                Customer cust = new Customer(customerId, customerName, addressId,
                        AddressSQL.getAddressString(addressId),AddressSQL.getPhone(addressId));
                customers.add(cust);

                DBConnection.closeConnection();
            }
            //System.out.println("List of customers: " + customers.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void delCustomer(Customer customer) {
        customers.remove(customer);
    }
}
