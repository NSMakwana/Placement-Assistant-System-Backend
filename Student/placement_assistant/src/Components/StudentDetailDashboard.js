import React, { useState } from "react";
import "./StudentDetailDashboard.css";
const StudentDetailDashboard = () => {
   
      const [formData, setFormData] = useState({
        eno: "",
        name: "",
        course: "",
        email: "",
        phno: "",
        dob: "",
        gender: "",
        batch: "",
        nationality: "",
        sscMarks: "",
        sscTotalMarks: "",
        sscPercentage: "",
        hscMarks: "",
        hscTotalMarks: "",
        hscPercentage: "",
        bachelorMarks: "",
        bachelorTotalMarks: "",
        bachelorPercentage: "",
        masterMarks: "",
        masterTotalMarks: "",
        masterPercentage: "",
        blockNum: "",
        buildingName: "",
        area: "",
        landmark: "",
        pincode: "",
        city: "",
        state: "",
        drops: "",
        remarks: "",
      });
    
      const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
      };
    
      const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Form Data Submitted:", formData);
        // Add logic to send form data to the backend or perform any actions
      };
    
      return (
        <div className="student-form-container">
          <h2>Student Details Form</h2>
          <form className="student-form" onSubmit={handleSubmit}>
            <h3>Personal Details</h3>
            <div className="form-row">
              <div className="form-group">
                <label>Enrollment Number</label>
                <input type="text" name="eno" value={formData.eno} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Name</label>
                <input type="text" name="name" value={formData.name} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Course</label>
                <input type="text" name="course" value={formData.course} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Email</label>
                <input type="email" name="email" value={formData.email} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Phone Number</label>
                <input type="text" name="phno" value={formData.phno} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Date of Birth</label>
                <input type="date" name="dob" value={formData.dob} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Gender</label>
                <select name="gender" value={formData.gender} onChange={handleChange} required>
                  <option value="">Select</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Other">Other</option>
                </select>
              </div>
              <div className="form-group">
                <label>Batch</label>
                <input type="text" name="batch" value={formData.batch} onChange={handleChange} required />
              </div>
              <div className="form-group">
                <label>Nationality</label>
                <input type="text" name="nationality" value={formData.nationality} onChange={handleChange} />
              </div>
            </div>
    
            <h3>Address Details</h3>
            <div className="form-row">
              <div className="form-group">
                <label>Block Number</label>
                <input type="text" name="blockNum" value={formData.blockNum} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>Building Name</label>
                <input type="text" name="buildingName" value={formData.buildingName} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>Area</label>
                <input type="text" name="area" value={formData.area} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>Landmark</label>
                <input type="text" name="landmark" value={formData.landmark} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>Pincode</label>
                <input type="text" name="pincode" value={formData.pincode} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>City</label>
                <input type="text" name="city" value={formData.city} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>State</label>
                <input type="text" name="state" value={formData.state} onChange={handleChange} />
              </div>
            </div>
    
            <h3>Academic Details</h3>
            <div className="form-row">
              <div className="form-group">
                <label>SSC Marks</label>
                <input type="text" name="sscMarks" value={formData.sscMarks} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>SSC Total Marks</label>
                <input type="text" name="sscTotalMarks" value={formData.sscTotalMarks} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>SSC Percentage</label>
                <input type="text" name="sscPercentage" value={formData.sscPercentage} onChange={handleChange} />
              </div>
              

              <div className="form-group">
                <label>HSC Marks</label>
                <input type="text" name="sscMarks" value={formData.sscMarks} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>HSC Total Marks</label>
                <input type="text" name="sscTotalMarks" value={formData.sscTotalMarks} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>HSC Percentage</label>
                <input type="text" name="sscPercentage" value={formData.sscPercentage} onChange={handleChange} />
              </div>
              
              {/* Add similar fields for HSC, Bachelor, Master, etc. */}
            </div>
    
            <h3>Other Details</h3>
            <div className="form-row">
              <div className="form-group">
                <label>Drops</label>
                <input type="text" name="drops" value={formData.drops} onChange={handleChange} />
              </div>
              <div className="form-group">
                <label>Remarks</label>
                <textarea name="remarks" value={formData.remarks} onChange={handleChange} />
              </div>
            </div>
    
            <button type="submit" className="submit-btn">Submit</button>
          </form>
        </div>
      );
    };
    
  
export default StudentDetailDashboard;