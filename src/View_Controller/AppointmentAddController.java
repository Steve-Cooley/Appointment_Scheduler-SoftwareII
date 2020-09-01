package View_Controller;

import Model.Customer;
import Model.Inventory;
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

public class AppointmentAddController implements Initializable {
    @FXML public TableView<Customer> tvCustomers;
    @FXML public TableView tvAppointments;
    @FXML public TextField fieldAppointment;
    @FXML public TextArea textAreaDescription;
    @FXML public DatePicker textFieldDate;
    @FXML public Spinner spinnerHour;
    @FXML public Spinner spinnerMinute;
    @FXML public TextField fieldCustName;
    @FXML public TextField fieldUserID;
    @FXML public Button btnAddCust;
    @FXML public Button btnDelCust;
    @FXML public Button btnCancel;
    @FXML public Button btnSave;
    public TableColumn tcCustomerName;
    public TableColumn tcCustomerPhone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fill screen with data
        tvCustomers.setItems(Inventory.getCustomers());
        tcCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcCustomerPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
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

    public void setBtnAddCust(MouseEvent e) {
        System.out.println("Add button pressed");

        // select customer from tableView
        Customer customer = tvCustomers.getSelectionModel().getSelectedItem();
        //populate relevant fields
        fieldCustName.setText(customer.getName());

    }

}
