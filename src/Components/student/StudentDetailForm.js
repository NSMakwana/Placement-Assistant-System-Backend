import React, { useState } from "react";
import axios from "axios";
import "./StudentDetailForm.css";

const StudentDetailForm = () => {
  const [studentDetails, setStudentDetails] = useState({
    enrollmentNumber: "",
    fullName: "",
    course: "",
    email: "",
    phone: "",
    dob: "",
    gender: "",
    nationality: "",
    timestamp: "",
    batch: "",
    address: {
      buildingName: "",
      city: "",
      pincode: "",
      state: "",
    },
    ssc: {
      marks: "",
      totalMarks: "",
      percentage: "",
      board: "",
    },
    hsc: {
      marks: "",
      totalMarks: "",
      percentage: "",
      stream: "",
      board: "",
      year: "",
    },
    bachelor: {
      marks: "",
      totalMarks: "",
      percentage: "",
      degree: "",
      university: "",
      year: "",
    },
    masters: {
      totalMarks: "",
      percentage: "",
      degree: "",
      university: "",
      year: "",
    },
    Entry: "",
    drops: "",
    remarks: "",
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    // Check for nested properties using dot notation in name attribute
    if (name.includes(".")) {
      const [section, field] = name.split(".");
      setStudentDetails((prev) => ({
        ...prev,
        [section]: {
          ...prev[section],
          [field]: value,
        },
      }));
      if (section === "address" && field === "city" && value.length > 2) {
        fetchPincodeByCity(value);
      }
    } else {
      setStudentDetails((prev) => ({ ...prev, [name]: value }));
    }
  };

  const fetchPincodeByCity = async (city) => {
    try {
      const response = await axios.get(
        `https://api.postalpincode.in/postoffice/${city}`
      );
      const data = response.data;
      if (data[0].Status === "Success") {
        const postOffice = data[0].PostOffice[0];
        setStudentDetails((prev) => ({
          ...prev,
          address: {
            ...prev.address,
            pincode: postOffice.Pincode,
            state: postOffice.State,
          },
        }));
      } else {
        console.error("City not found or no data available.");
      }
    } catch (error) {
      console.error("Error fetching pincode:", error);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log("Student Details Submitted: ", studentDetails);
    // Here, you can send the studentDetails to your backend
  };

  return (
    <div className="student-form-container">
      <form onSubmit={handleSubmit} className="student-detail-form">
        <h3 style={{ textAlign: "center" }}>Student Details Form</h3>
        <h4>Basic Details</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="enrollmentNumber">Enrollment Number</label>
            <input
              type="number"
              id="enrollmentNumber"
              name="enrollmentNumber"
              value={studentDetails.enrollmentNumber}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="fullName">Full Name</label>
            <input
              type="text"
              id="fullName"
              name="fullName"
              value={studentDetails.fullName}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="course">Course</label>
            <input
              type="text"
              id="course"
              name="course"
              value={studentDetails.course}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={studentDetails.email}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="phone">Phone Number</label>
            <input
              type="number"
              id="phone"
              name="phone"
              value={studentDetails.phone}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="dob">Date of Birth</label>
            <input
              type="date"
              id="dob"
              name="dob"
              value={studentDetails.dob}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="gender">Gender</label>
            <select
              id="gender"
              name="gender"
              value={studentDetails.gender}
              onChange={handleChange}
            >
              <option value="">Select Gender</option>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
              <option value="Other">Other</option>
            </select>
          </div>
          <div className="form-group col-6">
            <label htmlFor="nationality">Nationality</label>
            <input
              type="text"
              id="nationality"
              name="nationality"
              value={studentDetails.nationality}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="timestamp">Timestamp</label>
            <input
              type="datetime-local"
              id="timestamp"
              name="timestamp"
              value={studentDetails.timestamp}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="batch">Batch</label>
            <input
              type="text"
              id="batch"
              name="batch"
              value={studentDetails.batch}
              onChange={handleChange}
            />
          </div>
        </div>

        <h4>Address</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="address.buildingName">Building Name</label>
            <input
              type="text"
              id="address.buildingName"
              name="address.buildingName"
              value={studentDetails.address.buildingName}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="address.city">City</label>
            <input
              type="text"
              id="address.city"
              name="address.city"
              value={studentDetails.address.city}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="address.pincode">Pincode</label>
            <input
              type="text"
              id="address.pincode"
              name="address.pincode"
              value={studentDetails.address.pincode}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="address.state">State</label>
            <input
              type="text"
              id="address.state"
              name="address.state"
              value={studentDetails.address.state}
              readOnly
            />
          </div>
        </div>

        <h4>SSC Information</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="ssc.marks">SSC Marks</label>
            <input
              type="number"
              id="ssc.marks"
              name="ssc.marks"
              value={studentDetails.ssc.marks}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="ssc.totalMarks">SSC Total Marks</label>
            <input
              type="number"
              id="ssc.totalMarks"
              name="ssc.totalMarks"
              value={studentDetails.ssc.totalMarks}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="ssc.percentage">SSC Percentage</label>
            <input
              type="number"
              step="0.01"
              id="ssc.percentage"
              name="ssc.percentage"
              value={studentDetails.ssc.percentage}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="ssc.board">SSC Board</label>
            <input
              type="text"
              id="ssc.board"
              name="ssc.board"
              value={studentDetails.ssc.board}
              onChange={handleChange}
            />
          </div>
        </div>

        <h4>HSC Information</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="hsc.marks">HSC Marks</label>
            <input
              type="number"
              id="hsc.marks"
              name="hsc.marks"
              value={studentDetails.hsc.marks}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="hsc.totalMarks">HSC Total Marks</label>
            <input
              type="number"
              id="hsc.totalMarks"
              name="hsc.totalMarks"
              value={studentDetails.hsc.totalMarks}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="hsc.percentage">HSC Percentage</label>
            <input
              type="number"
              step="0.01"
              id="hsc.percentage"
              name="hsc.percentage"
              value={studentDetails.hsc.percentage}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="hsc.stream">HSC Stream</label>
            <input
              type="text"
              id="hsc.stream"
              name="hsc.stream"
              value={studentDetails.hsc.stream}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="hsc.board">HSC Board</label>
            <input
              type="text"
              id="hsc.board"
              name="hsc.board"
              value={studentDetails.hsc.board}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="hsc.year">HSC Year</label>
            <input
              type="number"
              id="hsc.year"
              name="hsc.year"
              value={studentDetails.hsc.year}
              onChange={handleChange}
            />
          </div>
        </div>

        <h4>Bachelor Information</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="bachelor.marks">Bachelor Marks</label>
            <input
              type="number"
              id="bachelor.marks"
              name="bachelor.marks"
              value={studentDetails.bachelor.marks}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="bachelor.totalMarks">Bachelor Total Marks</label>
            <input
              type="number"
              id="bachelor.totalMarks"
              name="bachelor.totalMarks"
              value={studentDetails.bachelor.totalMarks}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="bachelor.percentage">Bachelor Percentage</label>
            <input
              type="number"
              step="0.01"
              id="bachelor.percentage"
              name="bachelor.percentage"
              value={studentDetails.bachelor.percentage}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="bachelor.degree">Bachelor Degree</label>
            <input
              type="text"
              id="bachelor.degree"
              name="bachelor.degree"
              value={studentDetails.bachelor.degree}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="bachelor.university">Bachelor University</label>
            <input
              type="text"
              id="bachelor.university"
              name="bachelor.university"
              value={studentDetails.bachelor.university}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="bachelor.year">Bachelor Year</label>
            <input
              type="number"
              id="bachelor.year"
              name="bachelor.year"
              value={studentDetails.bachelor.year}
              onChange={handleChange}
            />
          </div>
        </div>

        <h4>Masters Information</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="masters.marks">Masters Marks</label>
            <input
              type="number"
              id="masters.marks"
              name="masters.marks"
              value={studentDetails.masters.marks}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="masters.totalMarks">Masters Total Marks</label>
            <input
              type="number"
              id="masters.totalMarks"
              name="masters.totalMarks"
              value={studentDetails.masters.totalMarks}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="masters.percentage">Masters Percentage</label>
            <input
              type="number"
              step="0.01"
              id="masters.percentage"
              name="masters.percentage"
              value={studentDetails.masters.percentage}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="masters.degree">Masters Degree</label>
            <input
              type="text"
              id="masters.degree"
              name="masters.degree"
              value={studentDetails.masters.degree}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="masters.university">Masters University</label>
            <input
              type="text"
              id="masters.university"
              name="masters.university"
              value={studentDetails.masters.university}
              onChange={handleChange}
            />
          </div>
          <div className="form-group col-6">
            <label htmlFor="masters.year">Masters Year</label>
            <input
              type="number"
              id="masters.year"
              name="masters.year"
              value={studentDetails.masters.year}
              onChange={handleChange}
            />
          </div>
        </div>

        <h4>Other Details</h4>
        <div className="form-row">
          <div className="form-group col-6">
            <label htmlFor="Entry">Lateral Entry</label>
            <textarea
              id="Entry"
              name="Entry"
              value={studentDetails.Entry}
              onChange={handleChange}
            ></textarea>
          </div>
          <div className="form-group col-6">
            <label htmlFor="drops">Drops</label>
            <input
              type="number"
              id="drops"
              name="drops"
              value={studentDetails.drops}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="form-group">
          <label htmlFor="remarks">Remarks</label>
          <textarea
            id="remarks"
            name="remarks"
            rows="3"
            value={studentDetails.remarks}
            onChange={handleChange}
          ></textarea>
        </div>
        <button type="submit" className="submit-button">
          Submit
        </button>
      </form>
    </div>
  );
};

export default StudentDetailForm;
