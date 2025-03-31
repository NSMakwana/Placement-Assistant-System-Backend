import React, { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import "./Sidebar.css";

const Sidebar = () => {
  const [hasSubmitted, setHasSubmitted] = useState(false);

  useEffect(() => {
    const checkSubmissionStatus = async () => {
      try {
        const storedUser = localStorage.getItem("user");
        const email = storedUser ? JSON.parse(storedUser).email : "";
        const response = await fetch(`http://localhost:8080/api/user/hasSubmitted?email=${email}`);
        if (response.ok) {
          const data = await response.json();
          setHasSubmitted(data); // data should be a boolean value
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

  // A helper to conditionally disable links if agreement not submitted
  const handleNavClick = (e) => {
    if (!hasSubmitted) {
      // Prevent navigation if agreement not submitted
      e.preventDefault();
      // Optionally, show a message or highlight the Agreement Form tab
      alert("Please submit the agreement form before accessing other sections.");
    }
  };

  return (
    <div className="sidebar">
      {/* Agreement Form should always be enabled */}
      <NavLink
        to="agreement"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item"
        }
      >
        <img src="/Images/Threeline.png" alt="Agreement Icon" className="menu-icon" />
        <span className="menu-text">Agreement Form</span>
      </NavLink>

      {/* Other sections: only allow navigation if hasSubmitted is true */}
      <NavLink
        to="studentprofile"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item disabled"
        }
        onClick={(e) => !hasSubmitted && handleNavClick(e)}
      >
        <img src="/Images/Threeline.png" alt="Profile Icon" className="menu-icon" />
        <span className="menu-text">Student Profile</span>
      </NavLink>

      <NavLink
        to="studentdetail"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item disabled"
        }
        onClick={(e) => !hasSubmitted && handleNavClick(e)}
      >
        <img src="/Images/Threeline.png" alt="Detail Icon" className="menu-icon" />
        <span className="menu-text">Student Detail Form</span>
      </NavLink>

      <NavLink
        to="company"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item disabled"
        }
        onClick={(e) => !hasSubmitted && handleNavClick(e)}
      >
        <img src="/Images/Threeline.png" alt="Company Icon" className="menu-icon" />
        <span className="menu-text">View Companies</span>
      </NavLink>

      <NavLink
        to="analysis"
        className={({ isActive }) =>
          isActive ? "menu-item active" : "menu-item disabled"
        }
        onClick={(e) => !hasSubmitted && handleNavClick(e)}
      >
        <img src="/Images/Threeline.png" alt="Analysis Icon" className="menu-icon" />
        <span className="menu-text">Analysis</span>
      </NavLink>
    </div>
  );
};

export default Sidebar;
