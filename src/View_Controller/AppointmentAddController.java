package View_Controller;

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
import utils.TimeMachine;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {
    @FXML public TableView<Customer> tvCustomers;
    @FXML public TableView tvAppointments;
    @FXML public TextField fieldAppointment;
    @FXML public TextArea textAreaDescription;
    @FXML public DatePicker datepicker;
    @FXML public Spinner spinnerHour;
    @FXML public Spinner spinnerMinute;
    @FXML public TextField fieldCustName;
    @FXML public TextField fieldUserID;
    @FXML public Button btnAddCust;
    @FXML public Button btnDelCust;
    @FXML public Button btnCancel;
    @FXML public Button btnSave;
    @FXML public TableColumn<TableView<Appointment>, Appointment> tcCustomerName;
    @FXML public TableColumn<TableView<Appointment>, Appointment> tcCustomerPhone;
    @FXML public Button btnAddAppointment;
    @FXML public ComboBox<String> comboHour;
    @FXML public ComboBox<String> comboMinute;

    private static ObservableList<String > hours = FXCollections.observableArrayList() ;
    private static ObservableList<String > minutes = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fill screen with data
        tvCustomers.setItems(Inventory.getCustomers());
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        //populate combo boxes
        hours.add("08");
        hours.add("09");
        hours.add("10");
        hours.add("11");
        hours.add("12");
        minutes.add("00");
        minutes.add("15");
        minutes.add("30");
        minutes.add("45");
        comboHour.setItems(hours);
        comboMinute.setItems(minutes);
    }

    public void setBtnCancel(MouseEvent m) throws IOException {
        System.out.println("cancel button");
        Parent parent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage stage = (Stage)((Node) m.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }

    public void setBtnSelectCustomer(MouseEvent e) {
        // select customer from tableView
        Customer customer = tvCustomers.getSelectionModel().getSelectedItem();
        //populate relevant fields
        fieldCustName.setText(customer.getName());
    }

    public void setBtnAddAppointment(MouseEvent e) {
        String appointmentType = textAreaDescription.getText();
        System.out.println(appointmentType);
        String hour = comboHour.getValue();
        System.out.println(hour);
        String minute = comboMinute.getValue();
        LocalDate date = datepicker.getValue();
        System.out.println("date: " + date);
        String ldt =  date + " " + hour + ":" + minute;
        // 2020-09-040800 to 2016-03-04 11:30
        System.out.println(ldt);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(ldt, formatter);
        System.out.println(localDateTime);
        Timestamp ts = TimeMachine.ldtToTimestamp(localDateTime);
        System.out.println("Timestamp: " + ts);
    }

}
