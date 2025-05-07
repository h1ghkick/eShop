package entities;

public class User {
    private int userNr;
    private String firstName;
    private String lastName;

    public User(String fName, String lName, int userNr) {
        userNr = userNr;
        this.firstName = fName;
        this.lastName = lName;
    }


    public int getUserNr(){ return userNr; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public void setfirstName(String fName) { this.firstName = fName; }
    public void setlastName(String lName) { this.lastName = lName; }
}
