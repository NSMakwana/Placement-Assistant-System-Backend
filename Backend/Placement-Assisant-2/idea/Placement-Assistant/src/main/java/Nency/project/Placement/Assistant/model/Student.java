package Nency.project.Placement.Assistant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "Student")
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Student {

    @Id
    private String id;
    @Field("userID")
    private String userId;
    private int eno;
    private String name;
    private String course;
    private String email;
    @Field("phno")
    private String phno;
    private Date dob;
    private String gender;
    private String nationality;
    private String batch;
    private Date timestamp;

    private Address address;
    private SSC ssc;
    private HSC hsc;
    private Bachelor bachelor;
    private Master master;
    private String lEntry;
    private int drops;
    private String remarks;



    private String placement_status;

    public String getPlacedCompanyId() {
        return placedCompanyId;
    }

    public void setPlacedCompanyId(String placedCompanyId) {
        this.placedCompanyId = placedCompanyId;
    }

    public String getPlacedDesignation() {
        return placedDesignation;
    }

    public void setPlacedDesignation(String placedDesignation) {
        this.placedDesignation = placedDesignation;
    }

    private String placedCompanyId;
    private String placedDesignation;
    public String getPlacement_status() {
        return placement_status;
    }

    public void setPlacement_status(String placement_status) {
        this.placement_status = placement_status;
    }


    // Utility method to parse date from a string
    public void setDobFromString(String dateString) {
        if (dateString != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
                this.dob = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.err.println("Invalid date format: " + dateString);
                this.dob = null; // Handle as per your application's needs
            }
        } else {
            this.dob = null;
        }
    }
    //getters and setters


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhno() {
        return phno;
    }

    public void setPhno(String phno) {
        this.phno = phno;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public SSC getSsc() {
        return ssc;
    }

    public void setSsc(SSC ssc) {
        this.ssc = ssc;
    }

    public HSC getHsc() {
        return hsc;
    }

    public void setHsc(HSC hsc) {
        this.hsc = hsc;
    }

    public Bachelor getBachelor() {
        return bachelor;
    }

    public void setBachelor(Bachelor bachelor) {
        this.bachelor = bachelor;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public String getlEntry() {
        return lEntry;
    }

    public void setlEntry(String lEntry) {
        this.lEntry = lEntry;
    }

    public int getDrops() {
        return drops;
    }

    public void setDrops(int drops) {
        this.drops = drops;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static class Address {
        private String blockNum;
        private String buildingName;
        private String area;
        private String landmark;
        private int pincode;
        private String city;
        private String state;

        // Getters and Setters
        public String getBlockNum() {
            return blockNum;
        }

        public void setBlockNum(String blockNum) {
            this.blockNum = blockNum;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
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

        public int getPincode() {
            return pincode;
        }

        public void setPincode(int pincode) {
            this.pincode = pincode;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }

    public static class SSC {
        private int marks;
        private int totalMarks;
        private double percentage;
        private int year;
        private String board;

        // Getters and Setters
        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            this.marks = marks;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }
    }

    public static class HSC {
        private int marks;
        private int totalMarks;
        private double percentage;
        private String stream;
        private String board;
        private int year;

        // Getters and Setters
        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            this.marks = marks;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public String getStream() {
            return stream;
        }

        public void setStream(String stream) {
            this.stream = stream;
        }

        public String getBoard() {
            return board;
        }

        public void setBoard(String board) {
            this.board = board;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class Bachelor {
        private int marks;
        private int totalMarks;
        private double percentage;
        private String degree;
        private String university;
        private int year;

        // Getters and Setters
        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            this.marks = marks;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class Master {
        private int marks;
        private int totalMarks;
        private double percentage;
        private String degree;
        private String university;
        private int year;

        // Getters and Setters
        public int getMarks() {
            return marks;
        }

        public void setMarks(int marks) {
            this.marks = marks;
        }

        public int getTotalMarks() {
            return totalMarks;
        }

        public void setTotalMarks(int totalMarks) {
            this.totalMarks = totalMarks;
        }

        public double getPercentage() {
            return percentage;
        }

        public void setPercentage(double percentage) {
            this.percentage = percentage;
        }

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getUniversity() {
            return university;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }
}