package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class User {

    private SimpleIntegerProperty userId;
    private SimpleStringProperty userName;
    private SimpleStringProperty password;
    private SimpleIntegerProperty active;
    private LocalDateTime createDate;
    private SimpleStringProperty createdBy;
    private LocalDateTime lastUpdate;
    private SimpleStringProperty lastUpdateBy;

    public User(String name, String password) {
        System.out.println("User constructor is running");
        System.out.println("username: " + name + " pass: " + password );

        if (name.equals("test") && password.equals("test")) {
            userId = new SimpleIntegerProperty(1);
            System.out.println("User ID: " + userId);
            userName = new SimpleStringProperty(name);
            System.out.println("User Name: " + userName);
        } else if (name.equals("Jane Doe") && password.equals("super secure password")) {
            userId = new SimpleIntegerProperty(2);
            System.out.println("User ID: " + userId);
            userName = new SimpleStringProperty(name);
        } else {
            System.out.println("Invalid Login"); // should never run
        }
    }

    public int getUserId() {
        return userId.get();
    }

    public String getUserName() {
        return userName.get();
    }

}


