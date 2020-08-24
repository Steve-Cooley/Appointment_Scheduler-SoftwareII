package View_Controller;

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
import utils.AddressSQL;
import utils.CustomerSQL;
import utils.DBConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
        Connection conn = DBConnection.startConnection();

        // insert address
        addrId = AddressSQL.insertAddressAndReturnID(addr, phone, conn);

        // get address ID (select statement)  fixme if insertAddress returned an id, this might not be necessary
        //addrId = DBQuery.getAddressID(addr, addrId, conn);

        // insert customer
        CustomerSQL.insertCustomer(name, addrId, conn);

        DBConnection.closeConnection();

        //switch back to home screen
        Parent parent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void setBtnCancel(MouseEvent m) throws IOException {
        System.out.println("Cancel Button Pressed");

        Parent parent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage window = (Stage)((Node) m.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }


}
