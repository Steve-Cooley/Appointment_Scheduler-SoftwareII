package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;

public class City {

    private SimpleIntegerProperty cityId;
    private SimpleStringProperty city;
    private SimpleIntegerProperty countryId;
    private LocalDateTime createDate;
    private SimpleStringProperty createdBy;
    private LocalDateTime lastUpdate;
    private SimpleStringProperty lastUpdateBy;
}
