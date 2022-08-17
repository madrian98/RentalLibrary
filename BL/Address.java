package BL;

public class Address {

    String city;
    String street;
    int houseNumber;

    public Address(String city, String street, int houseNumber){
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
    }
    public String getCity() {
        return city;
    }
    public int getHouseNumber() {
        return houseNumber;
    }
    public String getStreet() {
        return street;
    }


    @Override
    public String toString() {
        return "Address{" +
                "\n City='" + city + '\'' +
                "\n Street='" + street + '\'' +
                "\n HouseNumber=" + houseNumber +
                '}';
    }
}
