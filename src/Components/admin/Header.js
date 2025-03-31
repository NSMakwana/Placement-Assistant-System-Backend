import React from "react";
import "./Header.css";
import { useNavigate } from "react-router-dom";

const Header = ({onLogout}) => {
   const navigate = useNavigate();
  
    const handleLogout = () => {
      // Clear authentication data
      localStorage.removeItem("user");
      localStorage.setItem("IsLoggedIn",false);
      // Optionally clear any other auth state
      if (onLogout) onLogout();
      // Redirect to login page
      navigate("/login");
    };
  return (

<div className="header">
  <h3 class ="admin-title">Admin Dashboard</h3>
  <h3 class="header-title">Placement Assistant</h3>
  <button className="logout-btn"onClick={handleLogout}>Logout</button>
</div>

   
  );
};

export default Header;
