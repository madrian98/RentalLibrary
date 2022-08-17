package BL;

public class Person {
    private int id;
    private final String firstName;
    private final String lastName;
    private final int age;
    private final String phoneNumber;
    private final String email;
    private final Address address;


    public Person(int id, String firstName, String lastName, int age, String phoneNumber, String email, String city, String street, int houseNumber){
        this.id=id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email=email;
        this.address = new Address(city,street,houseNumber);
    }

    public Person(String firstName, String lastName, int age, String phoneNumber, String email, String city, String street, int houseNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email=email;
        this.address = new Address(city, street, houseNumber);
    }


    public Address getAddress() {
        return address;
    }
    public String getEmail() {
        return email;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getAge() {
        return age;
    }



    public int getId() {
        return id;
    }

    @Override
    public String toString(){
        return "ID: " + id + "\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "Age: " + age + "\n" +
                "Phone number: " + phoneNumber + "\n" +
                "E-mail: " + email + "\n" +
                "City: " + address.city + "\n" +
                "Street: " + address.street + "\n" +
                "House number: " + address.houseNumber + "\n" +
                "\n";
    }

}
