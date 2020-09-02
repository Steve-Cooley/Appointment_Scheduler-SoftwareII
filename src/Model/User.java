package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class User {

    SimpleIntegerProperty userId;
    SimpleStringProperty userName;
    SimpleStringProperty password;
    SimpleIntegerProperty active;
    LocalDateTime createDate;
    SimpleStringProperty createdBy;
    LocalDateTime lastUpdate;
    SimpleStringProperty lastUpdateBy;

    // fixme: some redundancy with credentialCheck() in LoginController.  Could be good to leave it this way
    public User(String name, String password) {
        System.out.println("User constructor is running");
        System.out.println("username: " + name + " pass: " + password );

        if (name.equals("John Doe") && password.equals("super secure password")) {
            userId = new SimpleIntegerProperty(1);
            System.out.println("User ID: " + userId);
            userName = new SimpleStringProperty(name);
            System.out.println("User Name: " + userName);
        } else if (name.equals("Jane Doe") && password.equals("super secure password")) {
            userId = new SimpleIntegerProperty(2);
            System.out.println("User ID: " + userId);
            userName = new SimpleStringProperty(name);
        } else {
            System.out.println("Invalid Login");
            // todo I don't think this can possibly run
        }



    }

}


