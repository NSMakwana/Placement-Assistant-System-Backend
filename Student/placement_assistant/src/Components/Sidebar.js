import React, { useEffect, useState } from "react";

import "./Sidebar.css";

function Sidebar({ onMenuSelect }) {
  const [hasSubmitted, setHasSubmitted] = useState(false);
  useEffect(() => {
    // Fetch the user's submission status (this can be done via an API call)
    const checkSubmissionStatus = async () => {
      try {
        const email = localStorage.getItem("userEmail"); // Or however you get the logged-in user's email
        const response = await fetch(`http://localhost:8080/api/user/hasSubmitted?email=${email}`);
        const data = await response.json();
        setHasSubmitted(data); // Assuming the response is a boolean (true/false)
      } catch (error) {
        console.error("Error fetching submission status:", error);
        setHasSubmitted(false); // Default to false if there's an error
      }
    };

    checkSubmissionStatus();
  }, []);

  return (

    <div className="sidebar">
      <div className="menu-item" onClick={() => onMenuSelect("StudentProfile")}>
        <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
        <span className="menu-text">Student-Profile</span>
      </div>
      <div className="menu-item" onClick={() => onMenuSelect("AgreementForm")}>
          <img src="/Images/Threeline.png" alt="Agreement Icon" className="menu-icon" />
          <span className="menu-text">Agreement Form</span>
        </div>
     
          <div className="menu-item" onClick={() => onMenuSelect("StudentDetail")}>
            <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
            <span className="menu-text">Student Detail Form</span>
          </div>
          <div className="menu-item" onClick={() => onMenuSelect("Company")}>
            <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
            <span className="menu-text">View Companies</span>
          </div>
          <div className="menu-item" onClick={() => onMenuSelect("Result")}>
            <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
            <span className="menu-text">View Result</span>
          </div>
          <div className="menu-item" onClick={() => onMenuSelect("QBank")}>
            <img src="/Images/Threeline.png" alt="Dashboard Icon" className="menu-icon" />
            <span className="menu-text">Question Bank</span>
          </div>       
       
    
    </div>
  );
}

export default Sidebar;