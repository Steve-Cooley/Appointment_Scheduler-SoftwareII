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
import utils.DBConnection;
import utils.DBQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    public void setBtnSave(MouseEvent m) throws IOException {
        System.out.println("save button pressed");
        
        String name = fieldName.getText();
        String addr = fieldAddress.getText();
        String phone = fieldPhone.getText();
        int addrId = 0;  // initialized to avoid "possibly not be initialized" error

        ///////////////// SQL related statements below
        Connection conn = DBConnection.startConnection();

        // insert address
        try {
            String insertAddr = "Insert INTO address(address, phone, address2, cityId, postalCode, createDate," +
                    " createdBy, lastUpdate, lastUpdateBy) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            DBQuery.setPreparedStatement(conn, insertAddr);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            //Normal fields
            ps.setString(1, addr);
            ps.setString(2, phone);
            //Filler fields
            ps.setString(3, "0"); //address 2
            ps.setInt(4, 1);  //cityId
            ps.setString(5, "0"); //postalCode
            ps.setString(6, LocalDateTime.now().toString()); //createDate
            ps.setString(7, "0");  //createdBy
            ps.setString(8, LocalDateTime.now().toString()); //lastUpdate
            ps.setString(9, "0"); //lastUpdateBy

            System.out.println("Insert String is: \n" + ps.toString());

            ps.execute();
        } catch(SQLException e) {
            System.out.println("SQLException, save button, insert addr");
        }

        // get address ID (select statement)
        try {
            String selectS = "SELECT addressId FROM address WHERE address = ?";
            DBQuery.setPreparedStatement(conn, selectS);
            PreparedStatement ps = DBQuery.getPreparedStatement();

            ps.setString(1, addr);

            ps.execute();

            ResultSet rs = ps.getResultSet();
            if(rs.next()){
                addrId = rs.getInt("addressId");
                System.out.println("The address ID is: " + addrId);
            }
        } catch (SQLException throwables) {
            System.out.println("SQLException, save button, get addressId ");
            throwables.printStackTrace();
        }

        // insert user
        try {
            String insertString = "INSERT INTO customer(customerName, addressId," +
                    "active, createDate, createdBy, lastUpdate, lastUpdateBy) values(?, ?, ?, ?, ?, ?, ?)";
            DBQuery.setPreparedStatement(conn, insertString);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, name);
            ps.setInt(2, addrId);
            //filler fields
            ps.setInt(3, 0);   //todo not sure what active is supposed to do, might need to fix
            ps.setString(4, LocalDateTime.now().toString());
            ps.setString(5, "Temp User");  //createdBy //fixme  probably need to implement this once I have users
            ps.setString(6, LocalDateTime.now().toString()); //lastUpdate
            ps.setString(7, "TempUser2");  //lastUpdateBy //todo

            ps.execute();

        } catch (SQLException e) {
            System.out.println("SQLException, save button, insert user");
            e.printStackTrace();
        }
        System.out.println("end of save button");

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
