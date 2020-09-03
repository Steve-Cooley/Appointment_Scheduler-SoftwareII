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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentUpdateController implements Initializable {
    @FXML public TableView<Customer> tvCustomers;
    @FXML public TableColumn<TableView<Appointment>, Appointment> tcCustomerName;
    @FXML public TableColumn<TableView<Appointment>, Appointment> tcCustomerPhone;
    @FXML public Button BtnAddCust;
    @FXML public Button BtnDelCust;
    @FXML public Button BtnCancel;
    @FXML public Button BtnSave;
    @FXML public TableView TVAppointments;
    @FXML public TableView TVCustomers;
    @FXML public TextField FieldAppointment;
    @FXML public TextField FieldCustID;
    @FXML public TextField FieldUserID;
    @FXML public TextArea TextAreaDescription;
    @FXML public DatePicker datepicker;
    @FXML public Spinner SpinnerHour;
    @FXML public Spinner SpinnerMinute;
    @FXML public ComboBox<String> comboHour;
    @FXML public ComboBox<String> comboMinute;

    private static ObservableList<String > hours = FXCollections.observableArrayList() ;
    private static ObservableList<String > minutes = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        // fill screen with data
//        tvCustomers.setItems(Inventory.getCustomers());
//        tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

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
        Stage stage = (Stage) ((Node) m.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
