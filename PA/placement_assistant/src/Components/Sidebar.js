import React from "react";
import "./Sidebar.css";

function Sidebar({ onMenuSelect }) {
  return (
    <div className="sidebar">
      <div className="menu-item" onClick={() => onMenuSelect("AdminProfile")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Admin-Profile</span>
      </div>
      <div className="menu-item" onClick={() => onMenuSelect("Student")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Student</span>
      </div>
      <div className="menu-item" onClick={() => onMenuSelect("Company")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Company</span>
      </div>
      <div className="menu-item" onClick={() => onMenuSelect("Result")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Result</span>
      </div>
      <div className="menu-item" onClick={() => onMenuSelect("Analysis")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Analysis</span>
      </div>
    </div>
  );
}

export default Sidebar;
