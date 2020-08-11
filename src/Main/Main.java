package Main;

import utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBQuery;

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
        primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 626, 417));  //This seems to set the size of my window, ignores prefheight/prefwidth
        primaryStage.setScene(new Scene(root));  //Leaving h,w params unset allows them to be set in fxml by scenbuilder
        primaryStage.show();
    }

    /*
     *   It is odd that there are basically 2 exception methods here (one handled in DBConnection),
     *   but this meets part of requirement F
     *  todo:  Move all code put in during MW's videos.  Only things that belong are start, launch, close.
     *
     */
    public static void main(String[] args) throws Exception {
        //Connection conn = DBConnection.startConnection();  //connect to database
        //String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES(?,?,?,?)";
        //String updateStatement = "UPDATE country SET country = ?, createdBy = ? WHERE country = ?";
        //String deleteStatement = "DELETE FROM country WHERE country = ?";

        //DBQuery.setPreparedStatement(conn, insertStatement); // create prepared statement
        //DBQuery.setPreparedStatement(conn, deleteStatement); // create prepared statement

        //PreparedStatement ps = DBQuery.getPreparedStatement();  // reference to PreparedStatement

        //String countryName;
        //String countryName, newCountry, createdBy;
        //String createDate = "2020-03-28 00:00:00"; // if you leave out time, it defaults to 00:00:00 (so it's optional)
        //String createdBy = "admin";
        //String lastUpdateBy = "admin";

        // Get keyboard input
        //Scanner keyboard = new Scanner(System.in);
        //System.out.print("Enter a country to update: ");
        //System.out.print("Enter a country to Delete: ");
        //countryName = keyboard.nextLine();

//        System.out.print("Enter a  new country: ");
//        newCountry = keyboard.nextLine();
//
//        System.out.print("Enter user: ");
//        createdBy = keyboard.nextLine();

        // Key value mapping
        //ps.setString(1, countryName);
//        ps.setString(2, createdBy);
//        ps.setString(3, countryName);
        //ps.setString(4, lastUpdateBy);

        //ps.execute();

        // Check rows affected
//        if(ps.getUpdateCount() > 0)
//            System.out.println(ps.getUpdateCount() + " rows affected");
//        else
//            System.out.println("No change");

        launch(args);

        //DBConnection.closeConnection();

        sqlSelect();
        sqlInsert();
        //sqlUpdate();
        //sqlDelete();
        sqlSelect();

    }

    private static void sqlSelect() throws Exception {
        System.out.println("sqlSelect is running");
        Connection conn = DBConnection.startConnection();  //connect to database
        String selectStatement = "SELECT * FROM country";

        DBQuery.setPreparedStatement(conn, selectStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.execute();  // Execute prepared statement

        ResultSet rs = ps.getResultSet();


        // Forward scroll ResultSet
        while (rs.next()) {
            int countryId = rs.getInt("countryId");
            String countryName = rs.getString("country");
            LocalDate date = rs.getDate("createDate").toLocalDate();  //"Date" is old and possibly deprecated.  Using LocalDate instead.
            LocalTime time = rs.getTime("createDate").toLocalTime();
            String createdBy = rs.getString("createdBy");
            LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
            // Malcom says "you don't have to retrieve every column in a result set."  So omitting one column here.

            //display record
            System.out.println(countryId +
                    " | " + countryName +
                    " | " + date +
                    " | " + time +
                    " | " + createdBy +
                    " | " + lastUpdate);
        }
        DBConnection.closeConnection();

    }

    public static void sqlInsert() throws Exception {
        System.out.println("sqlInsert is running");

        Connection conn = DBConnection.startConnection();  //connect to database
        String insertStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy) VALUES(?,?,?,?)";

        DBQuery.setPreparedStatement(conn, insertStatement); // create prepared statement
        PreparedStatement ps = DBQuery.getPreparedStatement();

        String country = "Canada";
        String createDate = LocalDateTime.now().toString();  //Warning this saves in local time!
        String createdBy = "Steve";
        String lastUpdateBy = "Steve";

        ps.setString(1, country);
        ps.setString(2, createDate);
        ps.setString(3, createdBy);
        ps.setString(4, lastUpdateBy);

        ps.execute();

        //check how many rows were effected:
        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change!");
        }

        DBConnection.closeConnection();
    }

    public static void sqlUpdate() throws Exception {
        System.out.println("sqlUpdate is running");

        Connection conn = DBConnection.startConnection();
        String updateStatement = "UPDATE country SET country = ?, createdBy = ? WHERE country = ?";

        DBQuery.setPreparedStatement(conn, updateStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        String countryName = "'Canada'";
        String newCountryName = "Canada";
        String createdBy = "Steve";

        ps.setString(1, newCountryName);
        ps.setString(2, createdBy);
        ps.setString(3, countryName);


        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change!");
        }

        DBConnection.closeConnection();
    }

    public static void sqlDelete() throws Exception {
        System.out.println("sqlDelete is running");

        Connection conn = DBConnection.startConnection();

        String delStatement = "DELETE FROM country WHERE country = ?";

        DBQuery.setPreparedStatement(conn, delStatement);
        PreparedStatement ps = DBQuery.getPreparedStatement();

        ps.setString(1, "Canada");

        ps.execute();

        if (ps.getUpdateCount() > 0) {
            System.out.println(ps.getUpdateCount() + " rows affected");
        } else {
            System.out.println("No change!");
        }

        DBConnection.closeConnection();

    }
}
