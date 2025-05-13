package entities;

public class User {
    private String email;
    private String firstName;
    private String lastName;
    private String password;


    public User(String fName, String lName, String email, String password) {
        this.email = email;
        this.firstName = fName;
        this.lastName = lName;
        this.password = password;
    }

    public void setMail(String email) { this.email= email; }
    public String getMail(){ return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public void setfirstName(String firstName) { this.firstName = firstName; }
    public void setlastName(String lastName) { this.lastName = lastName; }
}
