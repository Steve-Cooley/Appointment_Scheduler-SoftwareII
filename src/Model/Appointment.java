package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class Appointment {

    SimpleIntegerProperty appointmentID;
    SimpleIntegerProperty customerID;
    SimpleIntegerProperty userId;
    SimpleStringProperty title;
    SimpleStringProperty description;
    SimpleStringProperty location;
    SimpleStringProperty  type;
    SimpleStringProperty  url;
    LocalDateTime start;
    LocalDateTime  end;
    LocalDateTime createDate;
    SimpleStringProperty createdBy;
    LocalDateTime  lastUpdate;
    SimpleStringProperty  lastUpdateBy;
}
