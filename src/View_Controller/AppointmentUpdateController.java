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

public class AppointmentUpdateController implements Initializable {
    @FXML public Button BtnAddCust;
    @FXML public Button BtnDelCust;
    @FXML public Button BtnCancel;
    @FXML public Button BtnSave;
    @FXML public TableView TVAppointments;
    @FXML public TableView TVCustomers;
    @FXML public TextField FieldAppointment;
    @FXML public TextField FieldCustID;
    @FXML public TextField FieldUserID;
    @FXML public TextArea TextAreaDescription;
    @FXML public DatePicker TextFieldDate;
    @FXML public Spinner SpinnerHour;
    @FXML public Spinner SpinnerMinute;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //At the very least, I should fill the screen with data here.
    }


    public void setBtnCancel(MouseEvent m) throws IOException {
        System.out.println("cancel button");
        Parent parent = FXMLLoader.load(getClass().getResource("HomeScreenController.fxml"));
        Scene scene = new Scene(parent);

        //Set stage info
        Stage stage = (Stage) ((Node) m.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
