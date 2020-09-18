package Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
    private final SimpleIntegerProperty id;
    private final SimpleStringProperty name;
    private final SimpleIntegerProperty addressId;
    private final SimpleStringProperty address;
    private final SimpleStringProperty phone;

    /**]
     *  Constructor that populates all fields.
     *  todo may not be necessary.  If it is, then it might need to pull from/ push to Address (address)
     * @param id
     * @param name
     * @param addressId
     * @param address
     * @param phone
     */
    public Customer(int id, String name, int addressId, String address, String phone) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.addressId = new SimpleIntegerProperty(addressId);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);
    }


    public String getPhone() {
        return phone.get();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAddressId() {
        return addressId.get();
    }

    public SimpleIntegerProperty addressIdProperty() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId.set(addressId);
    }

    public String getAddress() {
        System.out.println("getting address");
        return address.get();
    }

    public SimpleStringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }





}
