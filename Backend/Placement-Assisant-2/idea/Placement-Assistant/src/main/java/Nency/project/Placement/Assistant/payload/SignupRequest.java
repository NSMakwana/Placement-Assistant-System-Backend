package Nency.project.Placement.Assistant.payload;

public class SignupRequest {
    private String name;
    private String eno;
    private String email;
    private String password;

    // Default constructor
    public SignupRequest() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEno() {
        return eno;
    }
    public void setEno(String eno) {
        this.eno = eno;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
