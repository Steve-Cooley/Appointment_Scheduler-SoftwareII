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

public class AppointmentUpdateController implements Initializable {
    private static Appointment appointment;
    private static Customer customer;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcCustomerName;
    @FXML private TableColumn<TableView<Appointment>, Appointment> tcCustomerPhone;
    @FXML private Button BtnAddCust;
    @FXML private Button BtnDelCust;
    @FXML private Button BtnCancel;
    @FXML private Button BtnSave;
    @FXML private TableView<Appointment> tvAppointments;
    @FXML private TableView<Customer> tvCustomers;   /// fixme
    @FXML private TextField FieldAppointment;
    @FXML private TextField fieldCustName;
    @FXML private TextField FieldUserID;
    @FXML private TextArea TextAreaDescription;
    @FXML private DatePicker datepicker;
    @FXML private ComboBox<String> comboHour;
    @FXML private ComboBox<String> comboMinute;

    private static final ObservableList<String > hours = FXCollections.observableArrayList() ;
    private static final ObservableList<String > minutes = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // fill screen with data
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

        //populate relevant appointment fields
        System.out.println("in initialization.  Appointment ID is: " + appointment.getAppointmentID());
        TextAreaDescription.setText(appointment.getTitle());
        FieldAppointment.setText(String.valueOf(appointment.getAppointmentID()));
        fieldCustName.setText(appointment.getCustomerName());
        //get initial customer
        int custId = appointment.getCustomerId();
        customer = Inventory.getCustomerById(custId);
    }


    public void setBtnCancel(MouseEvent m) throws IOException {
        System.out.println("cancel button");
        Parent parent = FXMLLoader.load(getClass().getResource("../Home/HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage stage = (Stage) ((Node) m.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public static void setAppointment(Appointment a) {
        System.out.println("setAppointment is running");
        appointment = a;
        System.out.println("appointment set, the id number is: " + appointment.getAppointmentID());
    }

    public void addCustomerPressed(MouseEvent e) throws IOException {
//        customer = tvCustomers.getSelectionModel().getSelectedItem();
//        fieldCustName.setText(customer.getName());
        SelectionModel<Customer> selection = tvCustomers.getSelectionModel();
        if (!selection.isEmpty()) {
            customer = selection.getSelectedItem();
            fieldCustName.setText(customer.getName());
        } else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a customer from the list.");
            alert.showAndWait();
        }
    }

    public void setBtnSave(MouseEvent click) throws IOException {
        System.out.println("save button pressed");
        if (inputIsValid()) {
            System.out.println("input is valid!");
            String appointmentType = TextAreaDescription.getText();
            String hour = comboHour.getValue();
            String minute = comboMinute.getValue();
            LocalDate date = datepicker.getValue();
            String ldtString = date + " " + hour + ":" + minute;
            int customerId = customer.getId();
            int userId = appointment.getUserId();
            int apptId = appointment.getAppointmentID();
            System.out.println(ldtString);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(ldtString, formatter);
            Timestamp start = TimeMachine.ldtToTimestamp(localDateTime);
            if (!Inventory.isTimeSlotAlreadyTakenByOther(localDateTime, appointment)) { //in other terms "is time slot available"
                System.out.println("time slot is available");
                AppointmentSQL.updateAppointment(customerId, userId, appointmentType, start, start, apptId);
                setBtnCancel(click);
            } else {
                System.out.println("scheduling conflict"); // todo add popup
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Scheduling conflict");
                alert.setContentText("That time slot is already taken. \n" +
                        "please select a different time");
                alert.showAndWait();
            }
        } else {
            System.out.println("input is invalid");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setContentText("Please ensure that a customer is selected,\n and that all available data is filled in");
            alert.showAndWait();
        }
    }

    private boolean inputIsValid() {
        System.out.println("inputIsValid is running");
        return customer != null
                && !TextAreaDescription.getText().isEmpty()
                && datepicker.getValue() != null
                && comboHour.getValue() != null
                && comboMinute.getValue() != null;
    }
}
