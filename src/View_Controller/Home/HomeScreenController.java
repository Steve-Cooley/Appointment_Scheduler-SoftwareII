package View_Controller.Home;

import Model.Appointment;
import Model.Customer;
import Model.Inventory;
import Model.User;
import View_Controller.Appointment.AppointmentUpdateController;
import View_Controller.Customer.CustomerModifyController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.AppointmentSQL;
import utils.CustomerSQL;
import utils.TimeMachine;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    @FXML private Button btnNumApptInMonth;
    @FXML private Button btnSchedule;
    @FXML private Button btnNumCust;
    @FXML private Button BtnLogout;
    @FXML private Button BtnAddAppt;
    @FXML private Button BtnModAppt;
    @FXML private Button BtnDelAppt;
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableView<Customer> customerTableView;
    @FXML private Button BtnAddCust;
    @FXML private Button BtnModCust;
    @FXML private Button BtnDelCust;
    @FXML private TableColumn<TableView<Customer>, Customer> tcCustId;
    @FXML private TableColumn<TableView<Customer>, Customer> tcCustName;
    @FXML private TableColumn<TableView<Customer>, Customer> tcCustAddress;
    @FXML private TableColumn<TableView<Customer>, Customer> tcCustPhone;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcStart;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcEnd;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcCustomer;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcTypeDescription;
    @FXML private RadioButton radioBtnWeek;
    @FXML private RadioButton radioBtnMonth;
    @FXML private ToggleGroup monthWeekToggleGroup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate customer table view
        Inventory.fetchCustomersFromDB();
        customerTableView.setItems(Inventory.getCustomers());
        tcCustId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustName.setCellValueFactory(new PropertyValueFactory<>("name")); //
        tcCustAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        tcCustPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // populate Calendar/Appointment table todo
        Inventory.fetchAppointmentsFromDB();
        appointmentTableView.setItems(Inventory.getAppointments());
        tcCustomer.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        tcStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        tcTypeDescription.setCellValueFactory(new PropertyValueFactory<>("title"));

        //group radio buttons
        monthWeekToggleGroup = new ToggleGroup();
        radioBtnMonth.setToggleGroup(monthWeekToggleGroup);
        radioBtnWeek.setToggleGroup((monthWeekToggleGroup));

        checkForImminentAppointment();

    }

    public void logoutBtnPressed(MouseEvent mouseEvent) throws IOException {
        System.out.println("Logout button pressed.");
        //The line below effectively logs the user out.
        Inventory.removeActiveUser();
        //return to login screen
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("../LoginScreen.fxml"));
        Scene scene = new Scene(HomeScreenParent);

        //Set stage info
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnAddCust(MouseEvent m) throws IOException {
        System.out.println("add customer button pressed");
        Parent CustomerAddParent = FXMLLoader.load(getClass().getResource("../Customer/CustomerAdd.fxml"));
        Scene scene = new Scene(CustomerAddParent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void setBtnModCust(MouseEvent m) throws IOException {
        System.out.println("mod cust button");
        // test if customer was selected
        SelectionModel<Customer> customerSelectionModel = customerTableView.getSelectionModel();
        if (customerSelectionModel.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing was selected");
            alert.setContentText("Please select a customer to update");
            alert.showAndWait();
        }else {
            //Send in cust object
            CustomerModifyController.setCustomer(customerSelectionModel.getSelectedItem());

            Parent p = FXMLLoader.load(getClass().getResource("../Customer/CustomerModify.fxml"));
            Scene scene = new Scene(p);

            //Set stage info
            Stage window = (Stage) ((Node) m.getSource()).getScene().getWindow();

            window.setScene(scene);
            window.show();
        }
    }

    public void setBtnDelCust() {
        System.out.println("del cust button");  //fixme
        //I need to have the SelectionModel as a separate variable so that I can see if it's empty
        SelectionModel<Customer> selection = customerTableView.getSelectionModel();
        ObservableList<Customer> customers = Inventory.getCustomers();
        ObservableList<Appointment> apptsToDelete = FXCollections.observableArrayList();
        // If no selection made, give error. Otherwise delete customer and all associated appointments.
        if (selection.isEmpty()) {
            System.out.println("nothing was selected");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No customer was selected");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        } else {
            Customer customerFromTv = selection.getSelectedItem();
            int customerIdFromCustomer = customerFromTv.getId();
            //Delete any appointments associated with customer
            ObservableList<Appointment> appointments = Inventory.getAppointments();
            for(Appointment appointment: appointments) {
                //System.out.println(appointment.getCustomerId());
                int custIdFromAppointment = appointment.getCustomerId();
                if(custIdFromAppointment == customerIdFromCustomer) {
                    AppointmentSQL.deleteFromDbByCustID(custIdFromAppointment);
                    //Inventory.delAppointmentFromList(appointment);  concurrent modification error
                    apptsToDelete.add(appointment);
                }
            }
            //delete appointments from Inventory
            for (Appointment apptToDel: apptsToDelete) {
                Inventory.delAppointmentFromList(apptToDel);
            }
            //Delete from DB
            CustomerSQL.deleteCustomer(customerFromTv.getId(), customerFromTv.getAddressId());
            // Delete from Inventory, (and from gui table)
            Inventory.delCustomer(customerFromTv);
        }
    }

    public void setBtnAddAppt(MouseEvent m) throws IOException {
        System.out.println("add appt button");
        Parent p = FXMLLoader.load(getClass().getResource("../Appointment/AppointmentAdd.fxml"));
        Scene scene = new Scene(p);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnModAppt(MouseEvent m) throws IOException {
        System.out.println("appt mod pressed");

        //send in appointment object
        SelectionModel<Appointment> selectionModel = appointmentTableView.getSelectionModel();
        if (selectionModel.isEmpty()) {
            System.out.println("selection model is empty"); // todo add popup
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Nothing selected!");
            alert.setContentText("Please select an appointment to modify.");
            alert.showAndWait();
        } else {
            System.out.println("selection model is not empty");
            Appointment appointment = selectionModel.getSelectedItem();
            System.out.println("appointment ID is : " + appointment.getAppointmentID());
            AppointmentUpdateController.setAppointment(appointment);

            Parent parent = FXMLLoader.load(getClass().getResource("../Appointment/AppointmentUpdate.fxml"));
            Scene scene = new Scene(parent);

            //Set stage info
            Stage window = (Stage) ((Node) m.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    public void setBtnDelAppt(MouseEvent m) throws IOException {
        System.out.println("del appt pressed");  //fixme
        Appointment appointment = appointmentTableView.getSelectionModel().getSelectedItem();
        if(appointment == null) {
            System.out.println("nothing was selected");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Appointment was selected");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        } else {
            int apptId = appointment.getAppointmentID();
            AppointmentSQL.deleteFromDbByAppointmentID(apptId);
            Inventory.delAppointmentFromList(appointment);
        }
    }

    public void radioBtnChange() {
        LocalDate now = LocalDate.now();
        System.out.println("That was then, this is " + now);
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        System.out.println("radioButton changed!");
        if (monthWeekToggleGroup.getSelectedToggle().equals(radioBtnMonth)) {
            System.out.println("month selected");
            // This lambda isn't meant to count toward the requirement, but I stuck it here so that I can easily
            // see the difference between the lambda version and the classic 'for each' statement
            Inventory.getAppointments().forEach(appointment -> {
                LocalDate date = appointment.getDate();
                if (date.isBefore(now.plusDays(30)) && date.isAfter(now.minusDays(1))) {
                    filteredAppts.add(appointment);
                }
            });
        } else {
            System.out.println("week selected");
            for (Appointment appointment : Inventory.getAppointments()) {
                LocalDate date = appointment.getDate();
                if (date.isBefore(now.plusDays(7)) && date.isAfter(now.minusDays(1))) {
                    filteredAppts.add(appointment);
                }
            }
        }
        appointmentTableView.setItems(filteredAppts);
    }

    public void setBtnNumApptTypesInMonth() throws IOException {
        System.out.println("Report button pushed: number of appointments in a month.");
        int numBusinessAppts = 0;
        int numPersonalAppts = 0;
        int numOtherAppts = 0;
        String business;
        String personal;
        String other;
        ObservableList<Appointment> allAppointments = Inventory.getAppointments();
        ObservableList<Appointment> filteredAppointments = FXCollections.observableArrayList();
        LocalDate now = LocalDate.now();
        //add appointments that take place in the next 30 days to observable list
        for (Appointment appointment: allAppointments) {
            LocalDate date = appointment.getDate();
            if (date.isAfter(now.minusDays(1)) && date.isBefore(now.plusDays(30))) {
                filteredAppointments.add(appointment);
            }
        }
        //System.out.println("Number of appointments in filtered list: " + filteredAppointments.size());
        // iterate through that list, looking for keywords then incrementing appropriately
        for (Appointment appointment: filteredAppointments) {
            String type = appointment.getTitle();
            //System.out.println("Type: " + type);
            if (type.contains("BUSINESS")) {
                numBusinessAppts++;
            }
            if (type.contains("PERSONAL")) {
                numPersonalAppts++;
            }
            if (!type.contains("BUSINESS") && !type.contains("PERSONAL")) {
                numOtherAppts++;
            }
        }
        business = "Number of business appointments: " + numBusinessAppts;
        System.out.println(business);
        personal = "Number of personal appointments: " + numPersonalAppts;
        System.out.println(personal);
        other = "Number of other appointments: " + numOtherAppts;
        System.out.println(other);
        FileWriter outPutFile = new FileWriter("Types_of_appointments.txt");
        outPutFile.write(business + "\n");
        outPutFile.write(personal + "\n");
        outPutFile.write(other);
        outPutFile.close();
    }

    public void setBtnSchedule() throws IOException {
        System.out.println("Report button pushed: schedule for consultant.");
        int userId = Inventory.getActiveUserId();
        ObservableList<Appointment> allAppointments = Inventory.getAppointments();
        FileWriter outputFile = new FileWriter("User_schedule.txt");
        // sort appointments by time
        allAppointments.sort(Comparator.comparing(Appointment::getStart));
        outputFile.write("List of all appointments for active user in the format date, time, customer name: \n");
        System.out.println("active user id is: " + userId);
        for (Appointment appointment: allAppointments) {
            String s = appointment.getDate() + " " +
                    appointment.getStartTime() + " " +
                    appointment.getCustomerName();
            //System.out.println(s);
            outputFile.write(s + "\n");
        }
        outputFile.close();
    }

    public void setBtnNumCust() throws IOException {
        System.out.println("Report button pushed: total number of customers.");
        FileWriter outputFile = new FileWriter("Number_of_customers.txt");
        int numberOfCustomers = Inventory.getCustomers().size();
        System.out.println("The number of customers is: " + numberOfCustomers);
        outputFile.write("The total number of all customers for all users is: " + numberOfCustomers);
        outputFile.close();
    }

    public void checkForImminentAppointment() {
        System.out.println("Check for immediate appointment is running!");
        ObservableList<Appointment> allAppointments = Inventory.getAppointments();
        int userId = Inventory.getActiveUserId();
        LocalDateTime now = LocalDateTime.now();
        for (Appointment appointment: allAppointments) {
            LocalDateTime apptStart = appointment.getStart();
            if (appointment.getUserId() == userId
                    && apptStart.isAfter(now)
                    && apptStart.isBefore(now.plusMinutes(15))) {
                System.out.println("You have an appointment within 15 minutes!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Alert");
                alert.setContentText("You have an appointment in a few minutes " +
                        "with \n" + appointment.getCustomerName());
                alert.showAndWait();
            }
        }
    }
}
