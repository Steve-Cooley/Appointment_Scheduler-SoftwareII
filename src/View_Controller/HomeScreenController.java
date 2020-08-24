package View_Controller;

import Model.Appointment;
import Model.Customer;
import Model.Inventory;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import utils.CustomerSQL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    public Button BtnLogout;
    public Button BtnAddAppt;
    public Button BtnModAppt;
    public Button BtnDelAppt;
    public TableView<Appointment> appointmentTableView;
    public TableView<Customer> customerTableView;
    public Button BtnAddCust;
    public Button BtnModCust;
    public Button BtnDelCust;
    public TableColumn<TableView<Customer>, Customer> tcCustId; //todo generics
    public TableColumn<TableView<Customer>, Customer> tcCustName; //todo generics

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate customer table view
        Inventory.fetchCustomersFromDB();
        customerTableView.setItems(Inventory.getCustomers());
        tcCustId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcCustName.setCellValueFactory(new PropertyValueFactory<>("name"));

        // populate Calendar/Appointment table todo
    }

    public void logoutBtnPressed(MouseEvent mouseEvent) throws IOException {
        System.out.println("Logout button pressed.");
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(HomeScreenParent);

        //Set stage info
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnAddCust(MouseEvent m) throws IOException {
        System.out.println("add customer button pressed");
        Parent CustomerAddParent = FXMLLoader.load(getClass().getResource("CustomerAdd.fxml"));
        Scene scene = new Scene(CustomerAddParent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnModCust(MouseEvent m) throws IOException {
        System.out.println("mod cust button");
        //Send in cust object
        CustomerModifyController.setCustomer(customerTableView.getSelectionModel().getSelectedItem());

        Parent p = FXMLLoader.load(getClass().getResource("CustomerModify.fxml"));
        Scene scene = new Scene(p);


        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


    public void setBtnDelCust(MouseEvent m) throws IOException {
        System.out.println("del cust button");  //fixme
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        //Delete from DB
        CustomerSQL.deleteCustomer(customer.getId(), customer.getAddressId());
        // Delete from Inventory, (and from gui table)
        Inventory.delCustomer(customer);
    }

    public void setBtnAddAppt(MouseEvent m) throws IOException {
        System.out.println("add appt button");
        Parent p = FXMLLoader.load(getClass().getResource("AppointmentAdd.fxml"));
        Scene scene = new Scene(p);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnModAppt(MouseEvent m) throws IOException {
        System.out.println("appt mod pressed");
        Parent parent = FXMLLoader.load(getClass().getResource("AppointmentUpdate.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    public void setBtnDelAppt(MouseEvent m) throws IOException {
        System.out.println("del appt pressed");  //fixme
    }

}
