package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Country {

    private SimpleIntegerProperty countryId;
    private SimpleStringProperty country;  // this is the actual name of the country, confusing.
    private LocalDateTime createDate;
    private String createdBy;
    private ZonedDateTime lastUpdate;
    private String lastUpdateBy;
}
