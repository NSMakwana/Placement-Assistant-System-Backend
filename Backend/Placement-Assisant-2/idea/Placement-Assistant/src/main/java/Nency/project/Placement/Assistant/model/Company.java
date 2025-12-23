package Nency.project.Placement.Assistant.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "company")
public class Company {
    @Id
    private String id;
    private String name;
    private String batch;
    private Address address;
    private ContactPerson contactPerson;
    private List<Designation> designations;
    private boolean visibleToStudents = false;

    public boolean isVisibleToStudents() {
        return visibleToStudents;
    }

    public void setVisibleToStudents(boolean visibleToStudents) {
        this.visibleToStudents = visibleToStudents;
    }
    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }
    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public ContactPerson getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<Designation> getDesignations() {
        return designations;
    }

    public void setDesignations(List<Designation> designations) {
        this.designations = designations;
    }

        public static class Address {
        private String blockNo;
        private String buildingName;
        private String area;
        private String landmark;
        private String state;
        private String city;
        private String pincode;

            // Getters and Setters

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getBuildingName() {
                return buildingName;
            }

            public void setBuildingName(String buildingName) {
                this.buildingName = buildingName;
            }

            public String getBlockNo() {
                return blockNo;
            }

            public void setBlockNo(String blockNo) {
                this.blockNo = blockNo;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getLandmark() {
                return landmark;
            }

            public void setLandmark(String landmark) {
                this.landmark = landmark;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getPincode() {
                return pincode;
            }

            public void setPincode(String pincode) {
                this.pincode = pincode;
            }

    }

    public static class ContactPerson {
        private String name;
        private String designation;
        private String email;
        private String mobile;

        // Getters and Setters

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class Designation {
        private String designation;
        private String Package;
        private String bond;
        private String location;
        private List<String> requiredQualifications;
        private List<PlacementProcess> placementProcess;

        // Getters and Setters

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public String getPackage() {
            return Package;
        }

        public void setPackage(String aPackage) {
            Package = aPackage;
        }

        public String getBond() {
            return bond;
        }

        public void setBond(String bond) {
            this.bond = bond;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<String> getRequiredQualifications() {
            return requiredQualifications;
        }

        public void setRequiredQualifications(List<String> requiredQualifications) {
            this.requiredQualifications = requiredQualifications;
        }

        public List<PlacementProcess> getPlacementProcess() {
            return placementProcess;
        }

        public void setPlacementProcess(List<PlacementProcess> placementProcess) {
            this.placementProcess = placementProcess;
        }

        public static class PlacementProcess {
            @Id
            private String id;
            private int roundNumber;
            private String round;
            private String description;

            // Getters and Setters


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getRoundNumber() {
                return roundNumber;
            }

            public void setRoundNumber(int roundNumber) {
                this.roundNumber = roundNumber;
            }

            public String getRound() {
                return round;
            }

            public void setRound(String round) {
                this.round = round;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
