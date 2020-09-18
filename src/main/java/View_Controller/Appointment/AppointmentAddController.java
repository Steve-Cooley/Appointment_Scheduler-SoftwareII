package View_Controller.Appointment;

import Model.Appointment;
import Model.Customer;
import Model.Inventory;
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
import utils.TimeMachine;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {
    @FXML private TableView<Customer> tvCustomers;
    @FXML private TableView<Appointment> tvAppointments;
    @FXML private TextField fieldAppointment;
    @FXML private TextArea textAreaDescription;
    @FXML private DatePicker datepicker;
    @FXML private TextField fieldCustName;
    @FXML private TextField fieldUserID;
    @FXML private Button btnAddCust;
    @FXML private Button btnDelCust;
    @FXML private Button btnCancel;
    @FXML private Button btnSave;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcCustomerName;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcCustomerPhone;
    @FXML private Button btnAddAppointment;
    @FXML private ComboBox<String> comboHour;
    @FXML private ComboBox<String> comboMinute;

    private Customer customer = null;
    private final Appointment appointment = null;
    private static final ObservableList<String > hours = FXCollections.observableArrayList() ;
    private static final ObservableList<String > minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fill screen with data
        tvCustomers.setItems(Inventory.getCustomers());
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //populate combo boxes
        hours.clear();
        hours.add("08");
        hours.add("09");
        hours.add("10");
        hours.add("11");
        hours.add("12");
        minutes.clear();
        minutes.add("00");
        minutes.add("15");
        minutes.add("30");
        minutes.add("45");
        comboHour.setItems(hours);
        comboMinute.setItems(minutes);
    }

    public void setBtnCancel(MouseEvent m) throws IOException {
        //System.out.println("cancel button");
        Parent parent = FXMLLoader.load(getClass().getResource("../Home/HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage stage = (Stage)((Node) m.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void setBtnSelectCustomer(MouseEvent e) {
        SelectionModel<Customer> selectionModel = tvCustomers.getSelectionModel();
        // check if the user actually has a customer selected
        if (!selectionModel.isEmpty()) {
            // select customer from tableView
            customer = selectionModel.getSelectedItem();

            //populate relevant fields
            fieldCustName.setText(customer.getName());
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer from the list");
            alert.showAndWait();
        }
    }

    public void setBtnAddAppointment(MouseEvent e) throws IOException {
        System.out.println("save button pressed");

        if (inputIsValid()) {
            System.out.println("input is valid!");
            String appointmentType = textAreaDescription.getText();
            //System.out.println(appointmentType);
            String hour = comboHour.getValue();
            //System.out.println(hour);
            String minute = comboMinute.getValue();
            LocalDate date = datepicker.getValue();
            //System.out.println("date: " + date);
            String ldtString = date + " " + hour + ":" + minute;
            int customerId = customer.getId();
            // 2020-09-040800 to 2016-03-04 11:30
            System.out.println(ldtString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(ldtString, formatter);
            //System.out.println(localDateTime);
            Timestamp start = TimeMachine.ldtToTimestamp(localDateTime);
            Timestamp end = TimeMachine.ldtToTimestamp(localDateTime); //fixme all appts are 15, minutes. Unnecessary?
            System.out.println("Timestamp: " + start);

            //check for scheduling conflict
            if(!Inventory.isTimeSlotAlreadyTaken(localDateTime)) {
                int userId = Inventory.getActiveUserId();
                AppointmentSQL.insertAppointmentsToDB(customerId, userId, appointmentType, start, end);
                setBtnCancel(e);
            } else {
                System.out.println("scheduling conflict");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Scheduling conflict");
                alert.setContentText("That time slot is already taken. \n" +
                        "please select a different time");
                alert.showAndWait();
            }
        } else {
            System.out.println("alert should be running, b/c input was not validated");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please ensure that a customer is selected,\n and that all available data is filled in");
            alert.showAndWait();
        }
    }

    private boolean inputIsValid() {
        System.out.println("input validator running");
        if(customer == null
                || textAreaDescription.getText().isEmpty()
                || datepicker.getValue() == null
                || comboHour.getValue() == null
                || comboMinute.getValue() == null
        ) {
            System.out.println("Input not valid");
            return false;
        } else {
            System.out.println("input is valid");
            return true;
        }
    }
}
