package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
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

    }

//    @FXML
//    public void cheaterBtnPushed(ActionEvent event) throws IOException {
//        //HomeScreenController.setPrt()
//        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
//        Scene mainScreen = new Scene(HomeScreenParent);
//    }

    public void cheaterBtnPushed(MouseEvent mouseEvent)  throws IOException{
        System.out.println("Cheater button pushed.");
        Parent HomeScreenParent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene mainScreen = new Scene(HomeScreenParent);

        //Set stage info
        Stage window = (Stage)((Node) mouseEvent.getSource()).getScene().getWindow();

        window.setScene(mainScreen);
        window.show();
    }
}
