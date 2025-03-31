import React from "react";
import { NavLink } from "react-router-dom";
import "./Sidebar.css";

function Sidebar() {
  return (
    <div className="sidebar">
      <NavLink
        to="adminprofile"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Admin Profile Icon" className="menu-icon" />
        <span className="menu-text">Admin-Profile</span>
      </NavLink>

      <NavLink
        to="admin_dashboard"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Student Icon" className="menu-icon" />
        <span className="menu-text">Student</span>
      </NavLink>

      <NavLink
        to="companydetails"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Company Icon" className="menu-icon" />
        <span className="menu-text">Company</span>
      </NavLink>

      <NavLink
        to="result"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Result Icon" className="menu-icon" />
        <span className="menu-text">Result</span>
      </NavLink>

      <NavLink
        to="analysis"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Analysis Icon" className="menu-icon" />
        <span className="menu-text">Analysis</span>
      </NavLink>
    </div>
  );
}

export default Sidebar;
