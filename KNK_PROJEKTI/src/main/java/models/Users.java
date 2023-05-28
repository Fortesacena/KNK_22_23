package models;

public class Users {

    private final int student_id;
    private String username;
    private String salt;
    private String saltedPassword;
    private String name;
    private String lastname;
    private String role;

    public Users(
            int student_id,
            String username,
            String salt,
            String saltedPassword,
            String name,
            String lastname,
            String role
    ) {
        this.student_id = student_id;
        this.username = username;
        this.salt = salt;
        this.saltedPassword = saltedPassword;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
    }

    public int getStudent_id() {
        return student_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSaltedPassword() {
        return saltedPassword;
    }

    public void setSaltedPassword(String saltedPassword) {
        this.saltedPassword = saltedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getRole() {
        return role;
    }
}
