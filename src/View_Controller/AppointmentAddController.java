package View_Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {
    @FXML public TableView TVCustomers;
    @FXML public TableView TVAppointments;
    @FXML public TextField FieldAppointment;
    @FXML public TextArea TextAreaDescription;
    @FXML public DatePicker TextFieldDate;
    @FXML public Spinner SpinnerHour;
    @FXML public Spinner SpinnerMinute;
    @FXML public TextField FieldCustID;
    @FXML public TextField FieldUserID;
    @FXML public Button BtnAddCust;
    @FXML public Button BtnDelCust;
    @FXML public Button BtnCancel;
    @FXML public Button BtnSave;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //fill screen with data
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



}
