package Nency.project.Placement.Assistant.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String name;
    private String eno;
    private String email;
    private String password;
    private boolean hasSubmittedAgreement = false;
    private String role;
    public User(){
        
    }
    public User(String email, String role, String name, String eno, boolean hasSubmittedAgreement) {
        this.id=id;
        this.email = email;
        this.role = role;
        this.name = name;
        this.eno = eno;
        this.hasSubmittedAgreement = hasSubmittedAgreement;
    }
    public boolean isHasSubmittedAgreement() {
        return hasSubmittedAgreement;
    }

    public void setHasSubmittedAgreement(boolean hasSubmittedAgreement) {
        this.hasSubmittedAgreement = hasSubmittedAgreement;
    }
    // Getters and setters

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public Boolean getHasSubmittedAgreement() {
        return hasSubmittedAgreement;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}


