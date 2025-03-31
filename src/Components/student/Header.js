import React from "react";
import { useNavigate } from "react-router-dom";

import "./Header.css";

const Header = ({ onLogout }) => {
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
      <h3 className="admin-title">Student Dashboard</h3>
      <h3 className="header-title">Placement Assistant</h3>
      <button className="logout-btn" onClick={handleLogout}>
        Logout
      </button>
    </div>
  );
};

export default Header;
