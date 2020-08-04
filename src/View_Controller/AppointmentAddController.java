package View_Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AppointmentAddController {
    public TableView TVCustomers;
    public TableView TVAppointments;
    public TextField FieldAppointment;
    public TextArea TextAreaDescription;
    public DatePicker TextFieldDate;
    public Spinner SpinnerHour;
    public Spinner SpinnerMinute;
    public TextField FieldCustID;
    public TextField FieldUserID;
    public Button BtnAddCust;
    public Button BtnDelCust;
    public Button BtnCancel;
    public Button BtnSave;

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
