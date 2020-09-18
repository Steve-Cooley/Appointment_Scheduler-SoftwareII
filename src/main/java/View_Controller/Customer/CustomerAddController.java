package View_Controller.Customer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.AddressSQL;
import utils.CustomerSQL;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerAddController implements Initializable {
    public TextField fieldId;
    public TextField fieldName;
    public TextField fieldAddress;
    public TextField fieldCity;
    public TextField fieldPhone;
    public Button btnSave;
    public Button btnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //
    }


    @FXML
    public void setBtnSave(MouseEvent m) throws Exception {
        System.out.println("save button pressed");
        
        String name = fieldName.getText();
        String addr = fieldAddress.getText();
        String phone = fieldPhone.getText();
        int addrId = 0;  // initialized to avoid "possibly not be initialized" error

        ///////////////// SQL related statements below

        if (inputIsValid()) {
            System.out.println("input is valid");
            // insert address
            addrId = AddressSQL.insertAddressAndReturnID(addr, phone);

            // get address ID (select statement)

            // insert customer
            CustomerSQL.insertCustomer(name, addrId);


            //switch back to home screen
            Parent parent = FXMLLoader.load(getClass().getResource("../Home/HomeScreenController.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) m.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Input is invalid.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please ensure all fields are filled out.");
            alert.setTitle("Incomplete");
            alert.showAndWait();
        }
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

    private boolean inputIsValid() {
        return !fieldName.getText().isEmpty()
                && !fieldAddress.getText().isEmpty()
                && !fieldPhone.getText().isEmpty();
    }


}
