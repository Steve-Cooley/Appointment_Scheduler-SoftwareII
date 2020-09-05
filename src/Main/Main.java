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

    /*
     *   It is odd that there are basically 2 exception methods here (one handled in DBConnection),
     *   but this meets part of requirement F
     *
     */
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

    //    public static void sqlInsert() throws Exception {
//        System.out.println("sqlInsert is running");
//
//        Connection conn = DBConnection.startConnection();  //connect to database
//        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES(?,?,?,?)";
//
//        DBQuery.setPreparedStatement(conn, insertStatement); // create prepared statement
//        PreparedStatement ps = DBQuery.getPreparedStatement();
//
//        String country = "Canada";
//        String createDate = LocalDateTime.now().toString();  //Warning this saves in local time!
//        String createdBy = "Steve";
//        String lastUpdateBy = "Steve";
//
//        ps.setString(1, country);
//        ps.setString(2, createDate);
//        ps.setString(3, createdBy);
//        ps.setString(4, lastUpdateBy);
//
//        ps.execute();
//
//        //check how many rows were effected:
//        if (ps.getUpdateCount() > 0) {
//            System.out.println(ps.getUpdateCount() + " rows affected");
//        } else {
//            System.out.println("No change!");
//        }
//
//        DBConnection.closeConnection();
//    }

}
