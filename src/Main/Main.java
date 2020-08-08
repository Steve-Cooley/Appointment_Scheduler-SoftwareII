package Main;

import utils.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.DBQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        Connection conn = DBConnection.startConnection();  //connect to database

        DBQuery.setStatement(conn);  //create statement Object
        Statement statement = DBQuery.getStatement();  //get statement reference // I think that Statements are bad vs "PreparedStatements"

        String country, createDate, createdBy, lastUpdateBy;
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Enter a country: ");

        country = keyboard.nextLine();
        createDate = "2020-03-14 00:00:00";
        createdBy = "admin";
        lastUpdateBy = "admin";

        if(country.contains("'")) {
            country = country.replace("'", "\\'");
        }

        //String selectStatement = "SELECT * FROM country WHERE " + country;  // select statement //from first half of video
        String selectStatement = "INSERT INTO country(country, createDate, createdBy, lastUpdateBy)" +
                "VALUES(" +
                "'" + country + "'," +
                "'" + createDate + "'," +
                "'" + createdBy + "'," +
                "'" + createdBy + "'" +
                ")";


        try {
            statement.execute(selectStatement);  // execute statement

            if(statement.getUpdateCount() > 0)
                System.out.println(statement.getUpdateCount() + " row(s) affected!");
            else
                System.out.println("No change!");

            //ResultSet rs = statement.getResultSet();  //get resultSet

//            // Forward scroll ResultSet
//            while (rs.next()) {
//                int countryId = rs.getInt("countryId");
//                String countryName = rs.getString("country");
//                LocalDate date = rs.getDate("createDate").toLocalDate();  //"Date" is old and possibly deprecated.  Using LocalDate instead.
//                LocalTime time = rs.getTime("createDate").toLocalTime();
//                String createdBy = rs.getString("createdBy");
//                LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
//                // Malcom says "you don't have to retrieve every column in a result set."  So omitting one column here.
//
//                //display record
//                System.out.println(countryId +
//                        " | " + countryName +
//                        " | " + date +
//                        " | " + time +
//                        " | " + createdBy +
//                        " | " + lastUpdate);
//            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());

        }
        launch(args);

        DBConnection.closeConnection();
    }
}
