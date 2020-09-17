package View_Controller;

import Model.Inventory;
import Model.User;
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
import utils.TimeMachine;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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
    private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(new Locale("es"));  // So I don't have to reboot to test Spanish text.
        rb = ResourceBundle.getBundle("View_Controller.Lang", Locale.getDefault());
        //System.out.println(rb.getString("test"));
        LabelUsername.setText(rb.getString("username"));
        LabelPassword.setText(rb.getString("password"));
        LabelTitle.setText(rb.getString("title"));
        BtnLogin.setText(rb.getString("loginButton"));
        BtnExit.setText(rb.getString("exit"));
    }

    public void cheaterBtnPushed(MouseEvent mouseEvent)  throws IOException{
        System.out.println("Cheater button pushed.");
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("Home/HomeScreenController.fxml"));
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
            Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("Home/HomeScreenController.fxml"));
            Scene mainScreen = new Scene(HomeScreenParent);

            // log user login
            logUserActivity();

            //Set stage info
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();

            window.setScene(mainScreen);
            window.show();
        } else {
            System.out.println("credentials not accepted!");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("alertTitle"));
            alert.setContentText(rb.getString("alertContent"));
            alert.showAndWait();
        }
    }

    private boolean credentialCheck(String userName, String password) {
        if (userName.equals("test") && password.equals("test")) {
            return true;
        } else if(userName.equals("Jane Doe") && password.equals("super secure password")) {
            return true;
        } else {
            return false;
        }
    }

    private void logUserActivity() throws IOException {
        LocalDateTime nowLDT = LocalDateTime.now();
        Timestamp nowUTC = TimeMachine.ldtToTimestamp(nowLDT);
        User user = Inventory.getActiveUser();
        String userName = user.getUserName();
        File file = new File("UserLog.txt");
        if (!file.exists()) {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append("This is a log file with timestamps in ***UTC***.\n");
            fileWriter.append("User " + userName + " logged in at: " + nowUTC + " UTC\n");
            fileWriter.flush();
            fileWriter.close();
        } else {
            FileWriter fileWriter = new FileWriter(file, true);  // the second param causes fw to append
            fileWriter.append("User " + userName + " logged in at: " + nowUTC + " UTC\n");
            fileWriter.close();
        }

    }
}
