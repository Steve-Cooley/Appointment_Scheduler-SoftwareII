package View_Controller.Customer;

import Model.Customer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.CustomerSQL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerModifyController implements Initializable {
    private static Customer customer;
    public TextField fieldName;
    public TextField fieldAddress;
    public TextField fieldPhone;
    public Button btnSave;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fieldName.setText(customer.getName());
        fieldAddress.setText(customer.getAddress());
        fieldPhone.setText(customer.getPhone());
    }

    //Receive the pass
    public static void setCustomer(Customer c ) {
        customer = c;
    }

    @FXML
    public void setBtnSave(MouseEvent m) throws IOException {
        System.out.println("Save button pressed");
        String newName = fieldName.getText();
        String newAddress = fieldAddress.getText();
        String newPhone = fieldPhone.getText();

        //update customer data
        CustomerSQL.updateCustomer(customer, newName, newAddress, newPhone);

        // open up main screen
        Parent parent = FXMLLoader.load(getClass().getResource("../Home/HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();

    }

    @FXML
    public void setBtnCancel(MouseEvent m) throws IOException {
        System.out.println("Cancel Button Pressed");

        Parent parent = FXMLLoader.load(getClass().getResource("../Home/HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }
}
