import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Signup.css";

function SignupPage({ navigateToLogin }) {
  const [formData, setFormData] = useState({ name: "", eno: "", email: "", password: "" });
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate(); 

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage(""); // Reset error message

    try {
      const response = await fetch("http://localhost:8080/api/user/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        alert("Signup successful! Redirecting to login...");
        navigate("/login"); // Redirect to login page
        // navigateToLogin(); // Redirect to login page
      } else {
        const error = await response.text();
        setErrorMessage(error);
        alert(`Signup failed: ${error}`);
      }
    } catch (error) {
      console.error("Error during signup:", error);
      setErrorMessage("Something went wrong. Please try again.");
    }
  };

  return (
    <div className="SignupPage_Container">
      <div className="Login_image">
      <h3><div id="t">Placement Assistant</div><br />
          <br /><t/>
          Department of Computer Science,
          <br />
          Gujarat University.
      </h3>
      </div>
      <div className="SignupPage">
        <h3>Sign Up</h3>
        {errorMessage && <p className="error-message">{errorMessage}</p>} {/* Display error messages */}
        <form onSubmit={handleSubmit}>
          <div className="eno">
            <label htmlFor="eno" className="form-label">Enrollment No.</label>
            <input
              type="text"
              className="form-control"
              id="eno"
              name="eno"
              value={formData.eno}
              onChange={handleChange}
              required
            />
          </div>
          <div className="name">
            <label htmlFor="name" className="form-label">Name</label>
            <input
              type="text"
              className="form-control"
              id="name"
              name="name"
              placeholder="Name as per Attendance Sheet"
              value={formData.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="email">
            <label htmlFor="email" className="form-label">Email Address</label>
            <input
              type="email"
              className="form-control"
              id="email"
              name="email"
              placeholder="Enter your email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>
          <div className="pwd">
            <label htmlFor="password" className="form-label">Password</label>
            <input
              type="password"
              className="form-control"
              id="password"
              name="password"
              placeholder="Enter your password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>
          
          <div className="LSbuttons">
            <button type="submit" className="signup">
              Sign Up
            </button>
            <button type="button" className="login" onClick={navigateToLogin}>
              Login
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default SignupPage;
