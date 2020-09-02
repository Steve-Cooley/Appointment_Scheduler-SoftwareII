package View_Controller;

import Model.Inventory;
import Model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public Button BtnCheater;
    public Button BtnExit;
    public Button BtnLogin;
    public TextField FieldPassword;
    public TextField FieldUsername;
    public Label LabelUsername;
    public Label LabelPassword;
    public Label LabelTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(new Locale("es"));
        ResourceBundle rb = ResourceBundle.getBundle("View_Controller.Lang", Locale.getDefault());
        //System.out.println(rb.getString("test"));
    }

    public void cheaterBtnPushed(MouseEvent mouseEvent)  throws IOException{
        System.out.println("Cheater button pushed.");
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene mainScreen = new Scene(HomeScreenParent);

        //Set stage info
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(mainScreen);
        window.show();
    }

    public void setBtnLogin(MouseEvent mouseEvent) throws IOException {
        System.out.println("login button pressed");
        // Read credentials
        String userName = FieldUsername.getText();
        String password = FieldPassword.getText();
        System.out.println("setBtnLogin - Credentials: " + userName + " pw: " + password);

        if (credentialCheck(userName, password)) {
            System.out.println("Credentials accepted!");
            User user = new User(userName, password);
            Inventory.setActiveUser(user);
            Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
            Scene mainScreen = new Scene(HomeScreenParent);

            //Set stage info
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        } else {
            System.out.println("credentials not accepted!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setContentText("Invalid Login attempt");
            alert.showAndWait();
        }
    }

    public boolean credentialCheck(String userName, String password) {
        if (userName.equals("John Doe") && password.equals("super secure password")) {
            return true;
        } else if(userName.equals("Jane Doe") && password.equals("super secure password")) {
            return true;
        } else {
            return false;
        }
    }

    // todo language stuff
    // todo alerts (with language stuff)
}
