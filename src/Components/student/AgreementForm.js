import React, { useState, useEffect } from "react";
import './AgreementForm.css';

function AgreementForm() {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    phone: "",
    course: "",
    semester: "",
    confirmOption: "",
    file: null,
  });

  const [fileError, setFileError] = useState("");
  const [hasSubmitted, setHasSubmitted] = useState(false); // This tracks if the user has already submitted the form

  const courses = ["MCA", "M.Sc(AI&ML)", "M.Sc(DS)", "M.Tech(NT)", "M.Tech(WT)"];
  const confirm = ["Yes", "No"];
  const agree = ["I agree to all the terms and conditions mentioned"];

  // On load, check if the user has already submitted the form
  useEffect(() => {
    const checkSubmissionStatus = async () => {
      try {
        const storedUser = localStorage.getItem('user'); // Replace with dynamic user email, e.g., from auth state
        const email = storedUser ? JSON.parse(storedUser).email : "";
        const response = await fetch(`http://localhost:8080/api/user/hasSubmitted?email=${email}`);
        if (response.ok) {
          const data = await response.json();
          setHasSubmitted(data); // If true, the form is already submitted
        } else {
          setHasSubmitted(false);
        }
      } catch (error) {
        console.error("Error checking submission status:", error);
        setHasSubmitted(false);
      }
    };

    checkSubmissionStatus();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleFileChange = (e) => {
    const file = e.target.files[0];
    if (file && file.size > 10 * 1024 * 1024) {
      setFileError("File size must not exceed 10MB.");
      setFormData({ ...formData, file: null });
    } else {
      setFileError("");
      setFormData({ ...formData, file });
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (hasSubmitted) {
      alert("You have already submitted the form.");
      return;
    }
    const formDataObject = new FormData();

    formDataObject.append("name", formData.name);
    formDataObject.append("email", formData.email);
    formDataObject.append("phone", formData.phone);
    formDataObject.append("course", formData.course);
    formDataObject.append("semester", formData.semester);
    formDataObject.append("confirm", formData.confirmOption);
    formDataObject.append("agree", formData.agree);
    if (formData.file) {
      formDataObject.append("file", formData.file);
    }

    try {
      const response = await fetch("http://localhost:8080/api/agreements", {
        method: "POST",
        body: formDataObject,
      });

      if (response.ok) {
        setHasSubmitted(true); // Disable the form and indicate successful submission
        alert("Form submitted successfully!");

        // Update submission status in backend
        const email = formData.email; // Use logged-in user's email
        const updateResponse = await fetch(`http://localhost:8080/api/user/updateHasSubmitted?email=${email}`, {
          method: "POST",
        });

        if (updateResponse.ok) {
          console.log("User submission status updated successfully.");
        } else {
          alert("Failed to update submission status.");
        }
      } else {
        const error = await response.text();
        alert("Error submitting form: " + error);
      }
    } catch (error) {
      console.error("Error submitting form:", error);
      alert("Something went wrong. Please try again.");
    }
  };

  if (hasSubmitted) {
    return (
      <div className="AgreementForm_Container">
        <h2>Thank you for submitting the agreement! You cannot submit the form again.</h2>
      </div>
    );
  } else {
    return (
      <div className="AgreementForm_Container">
        <h2>Student Agreement Form</h2>
        <div className="form-container">
          <form onSubmit={handleSubmit}>
            {/* Name Field */}
            <div className="form-group">
              <label htmlFor="name" className="form-label">
                Name
              </label>
              <input
                type="text"
                className="form-control"
                id="name"
                name="name"
                value={formData.name}
                onChange={handleChange}
                required
                disabled={hasSubmitted} // Disable input if form is already submitted
              />
            </div>

            {/* Email Field */}
            <div className="form-group">
              <label htmlFor="email" className="form-label">
                Email
              </label>
              <input
                type="email"
                className="form-control"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                required
                disabled={hasSubmitted} // Disable input if form is already submitted
              />
            </div>

            {/* Phone Number Field */}
            <div className="form-group">
              <label htmlFor="phone" className="form-label">
                Phone Number
              </label>
              <input
                type="tel"
                className="form-control"
                id="phone"
                name="phone"
                value={formData.phone}
                onChange={handleChange}
                required
                disabled={hasSubmitted} // Disable input if form is already submitted
              />
            </div>

            {/* Course Dropdown */}
            <div className="form-group">
              <label htmlFor="course" className="form-label">
                Course
              </label>
              <select
                className="form-select"
                id="course"
                name="course"
                value={formData.course}
                onChange={handleChange}
                required
                disabled={hasSubmitted} // Disable dropdown if form is already submitted
              >
                <option value="">Select a course</option>
                {courses.map((course, index) => (
                  <option key={index} value={course}>
                    {course}
                  </option>
                ))}
              </select>
            </div>

            {/* Semester Field */}
            <div className="form-group">
              <label className="form-label">Semester</label>
              <input
                className="form-control"
                type="text"
                name="semester"
                value={formData.semester}
                onChange={handleChange}
                required
                disabled={hasSubmitted} // Disable input if form is already submitted
              />
            </div>

            {/* Confirm Field */}
            <div className="form-group">
              <label className="form-label">
                Would you like to sit for the placement process?
              </label>
              <div>
                {confirm.map((confirm, index) => (
                  <div className="form-check form-check-inline" key={index}>
                    <input
                      className="form-check-input"
                      type="radio"
                      name="confirm"
                      id={`confirm-${index}`}
                      value={confirm}
                      onChange={handleChange}
                      required
                      disabled={hasSubmitted} // Disable input if form is already submitted
                    />
                    <label className="form-check-label" htmlFor={`confirm-${index}`}>
                      {confirm}
                    </label>
                  </div>
                ))}
              </div>
              Read the following document carefully, print it, sign it, then scan the signed copy and upload it in the next section.
            </div>

            {/* Agreement Document */}
            <div className="image-section mt-3">
              <label className="form-label">Agreement Document</label>
              <br />
              <a href="/Images/consent.jpeg" target="_blank" rel="noopener noreferrer">
                <img src="/Images/consent.jpeg" alt="Agreement Document" height="100px" width="100px" />
              </a>
            </div>

            {/* File Upload Field */}
            <div className="form-group">
              <label htmlFor="file" className="form-label">
                Upload File (Max: 10MB)
              </label>
              <input
                type="file"
                className="form-control"
                id="file"
                name="file"
                onChange={handleFileChange}
                required
                disabled={hasSubmitted} // Disable input if form is already submitted
              />
              {fileError && <small className="text-danger">{fileError}</small>}
            </div>

            {/* Submit Button */}
            <button type="submit" className="btn btn-primary" disabled={hasSubmitted}>
              Submit
            </button>
          </form>
        </div>
      </div>
    );
  }
}

export default AgreementForm;
