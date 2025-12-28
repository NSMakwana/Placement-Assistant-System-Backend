package Nency.project.Placement.Assistant.payload;

public class ApplicationRequest {

        private String userId;     // from session storage
        private String companyId;
        private String designation;

        // getters & setters
        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCompanyId() {
            return companyId;
        }

        public void setCompanyId(String companyId) {
            this.companyId = companyId;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

}
