package Main;

import Model.Inventory;
import utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import utils.DBQuery;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../View_Controller/LoginScreen.fxml"));
        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 626, 417));  //This seems to set the size of my window, ignores prefheight/prefwidth
        primaryStage.setScene(new Scene(root));  //Leaving h,w params unset allows them to be set in fxml by scenbuilder
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {

        //  The 4 following call basic sql actions found here in this class for testing purposes
        //sqlInsert();
        //sqlUpdate();
        //sqlDelete();
        //sqlSelect();

        //   Test DBQuery methods
        //DBQuery.getAddressString(4);

        //  Test methods in Inventory
        //Inventory.addCustomer();

        //testing fetchAppointmentsFromDb with emphasis on timezone stuff.
        //Inventory.fetchAppointmentsFromDB();

        launch(args);  // Launch GUI.
    }

}
