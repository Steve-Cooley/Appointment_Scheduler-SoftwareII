package View_Controller;

import Model.Customer;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerModifyController implements Initializable {
    private static Customer customer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    //Receive the pass
    public static void setCustomer(Customer c ) {
        customer = c;
    }

}
