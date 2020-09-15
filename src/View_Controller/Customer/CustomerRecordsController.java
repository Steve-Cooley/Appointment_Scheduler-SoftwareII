package View_Controller.Customer;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerRecordsController implements Initializable {
    public TableView TVCust;
    public TableColumn TCID;
    public TableColumn TCName;
    public TableColumn TCAddress;
    public TableColumn TCPhone;
    public Button BtnDelCust;
    public Button BtnEditCust;
    public Button BtnAddCust;
    public Button BtnCancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
