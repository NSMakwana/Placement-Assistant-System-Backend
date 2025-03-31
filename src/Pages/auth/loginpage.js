import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import LoginHeader from "./LoginHeader";
import "./loginpage.css";

function LoginPage() {
  const [formData, setFormData] = useState({ name:"", eno:"", email: "", password: "" });
  const navigate = useNavigate(); 
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };
 
  const handleSubmit= async (e) => {
    e.preventDefault();
    try {
      const response = await fetch("http://localhost:8080/api/user/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });
  
      if (response.ok) {
        const data = await response.json();
        localStorage.setItem("user", JSON.stringify(data.user));
        localStorage.setItem("IsLoggedIn",true);
        alert("Login successful! Redirecting to dashboard...");
        const StoredUser=JSON.parse(localStorage.getItem("user"));
        if(StoredUser.role==="admin")
          navigate("/admin/admin_dashboard"); // Redirect to dashboard
        if(StoredUser.role==="student")
          navigate("/student/dashboard"); // Redirect to dashboard
      } else {
        const error = await response.text();
        alert(error);
      }
    } catch (error) {
      console.error("Error during login:", error);
      alert("Something went wrong! Please try again.");
    }
  };

  // Use local navigate for signup navigation
  const handleSignupNavigation = () => {
    navigate("/signup");
  };

  return (
    <div className="LoginPage_Container">
      <div className="Login_image">
        <h3>
          <div id="t">Placement Assistant</div><br />
          <br />
          Department of Computer Science,
          <br />
          Gujarat University.
        </h3>
      </div>
      <div className="LoginPage">
        <h3>Login</h3>
        <form onSubmit={handleSubmit}>
          <div className="email">
            <label htmlFor="email" className="form-label">Email Address</label>
            <br /><br />
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
            <br /><br />
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
          <br /><br />
          <div id="q">New User?</div>
          <div className="LSbuttons">
            <button type="submit" className="login">Login</button>
            <button type="button" className="signup" onClick={handleSignupNavigation}>
              Sign up
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default LoginPage;
