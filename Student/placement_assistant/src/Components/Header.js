import React from "react";
import "./Header.css";

const Header = ({ onLogout }) => {
  return (
    <div className="header">
      <h3 className="admin-title">Student Dashboard</h3>
      <h3 className="header-title">Placement Assistant</h3>
      <button className="logout-btn" onClick={onLogout}>
        Logout
      </button>
    </div>
  );
};

export default Header;
