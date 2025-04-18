package Nency.project.Placement.Assistant.payload;

public class UserResponse {

        private String email;
        private String role;
        private String name;
        private String eno;
        private boolean hasSubmittedAgreement;

        public UserResponse(String email, String role, String name, String eno, boolean hasSubmittedAgreement) {
            this.email = email;
            this.role = role;
            this.name = name;
            this.eno = eno;
            this.hasSubmittedAgreement = hasSubmittedAgreement;
        }

        // Getters and Setters

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getRole() {
            return role;
        }
        public void setRole(String role) {
            this.role = role;
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
        public boolean isHasSubmittedAgreement() {
            return hasSubmittedAgreement;
        }
        public void setHasSubmittedAgreement(boolean hasSubmittedAgreement) {
            this.hasSubmittedAgreement = hasSubmittedAgreement;
        }
    }


