import React, { useState } from "react";
//import "bootstrap/dist/css/bootstrap.min.css";
import LoginHeader from "./LoginHeader";
import "./loginpage.css"; // Optional for additional styles

function LoginPage({onLogin, navigateToSignup}) {
  const [formData, setFormData] = useState({ name:"" ,eno:"",email: "", password: "" });

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
        localStorage.setItem("userEmail", formData.email);
        alert("Login successful! Redirecting to dashboard...");
        onLogin(); // Redirect to dashboard
      } else {
        const error = await response.text();
        alert(error);
      }
    } catch (error) {
      console.error("Error during login:", error);
      alert("Something went wrong! Please try again.");
    }
  };

  return (
  
    <div className="LoginPage_Container">
     
      <div className="Login_image">
      <h3><div id="t">Placement Assistant</div><br />
          <br /><t/>
          Department of Computer Science,
          <br />
          Gujarat University.
      </h3>
      {/* <img src="/Images/login_Illustration.png"  /> */}
      </div>
      <div className="LoginPage">
        <h3>Login</h3>
        <form onSubmit={handleSubmit}>
        
         
          {/* Email Field */}
          <div className="email">
            <label htmlFor="email" className="form-label">Email Address</label>
            <br />
            <br />
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
         

          {/* Password Field */}
          <div className="pwd">
            <label htmlFor="password" className="form-label">Password</label>
            <br />
            <br />
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
          <br />
          <br />
          {/* Login Button */}
          <div id="q">New User?</div>
          <div className="LSbuttons">
          <button type="submit" className="login" >
            Login
          </button>
          <button type="submit" className="signup" onClick={navigateToSignup}>
           Sign up
          </button>
          </div>
        </form> 
      </div>
    </div>
   
  );
}

export default LoginPage;
