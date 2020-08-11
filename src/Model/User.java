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
}
