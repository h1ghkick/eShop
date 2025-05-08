package entities;

public class User {
    private String mail;
    private String firstName;
    private String lastName;
    private boolean status = false;


    public User(String fName, String lName, String email, boolean status) {
        this.mail = email;
        this.firstName = fName;
        this.lastName = lName;
        this.status = status;
    }


    public String getMail(){ return mail; }
    public boolean getStatus(){ return status;}
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public void setfirstName(String firstName) { this.firstName = firstName; }
    public void setlastName(String lastName) { this.lastName = lastName; }
}
