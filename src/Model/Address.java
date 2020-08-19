package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Address {

    private SimpleIntegerProperty addressId;
    private SimpleStringProperty address;
    private SimpleStringProperty address2;
    private SimpleIntegerProperty cityId;
    private SimpleStringProperty postalCode;
    private SimpleStringProperty phone;
    private ZonedDateTime createDate;
    private SimpleStringProperty createdBy;
    private ZonedDateTime lastUpdate;
    private SimpleStringProperty lastUpdateBy;

    public Address(String addressString){
        // todo DO I need this constructor if I'm kind of merging address with Customer?
    }

}
