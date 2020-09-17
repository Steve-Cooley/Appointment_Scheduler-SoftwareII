package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.*;

import java.sql.*;
import java.time.*;

public class Inventory {
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();
    private static ObservableList<User> users = FXCollections.observableArrayList();
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();
    private static User activeUser;

    public static boolean isTimeSlotAlreadyTaken(LocalDateTime potential) {
        //*** Requirement G
        //This use of lambdas is much more compact (and allegedly faster) than the multiline version below.
        return appointments.stream().map(appointment -> appointment.getStart()).anyMatch(o -> potential.equals(o));
        // original version of this code:
/*        for (Appointment appointment : appointments) {
            LocalDateTime existing = appointment.getStart();
            if (potential.equals(existing)) {
                return true;
            }
        }
        return false;*/
    }



    //This method for use in AppointmentUpdateController is a variation on the above isTimeSlotAlreadyTaken
    //checks for scheduling conflicts, but ignores them if they have the same appointmentId (An appointment
    //can't conflict with itself)
    public static boolean isTimeSlotAlreadyTakenByOther(LocalDateTime potential, Appointment currentAppointment) {
        int currentID = currentAppointment.getAppointmentID();
        for (Appointment appointment : appointments) {
            LocalDateTime existing = appointment.getStart();
            if (potential.equals(existing) && (currentID != appointment.getAppointmentID())) {
                return true;
            }
        }
        return false;
    }

    public static ObservableList<Customer> getCustomers() {
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

    public static Customer getCustomerById(int id) {
        //**** Requirement G
        // this method uses a lambda expression to find a Customer.  This is justified for two reasons:
        // 1.  Lambda expressions are more compact
        // 2.  Lambda expressions are much faster at this sort of work (as far as I understand)
        return customers.stream().filter(customer -> customer.getId() == id).findFirst().orElse(null);
    }

    //      Appointment section

    public static void delAppointmentFromList(Appointment appointment){
        appointments.remove(appointment);
    }

    public static ObservableList<Appointment> getAppointments() {
        return appointments;
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
                java.sql.Timestamp et = rs.getTimestamp("end"); // not used
                customerName = rs.getString("customerName");
                title = rs.getString("title");
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

    public static void removeAppointment(Appointment appointment) {

    }

    //      user section

    public static void setActiveUser(User user) {
        System.out.println("setActiveUser is running");
        activeUser = user;
        System.out.println("the active user is: " + user.getUserName());
    }

    public static void removeActiveUser() {
        activeUser = null;
        System.out.println("logged out");
    }

    public static int getActiveUserId() {
        return activeUser.getUserId();
    }

    public static User getActiveUser() {
        return activeUser;
    }
}