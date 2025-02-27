import React, { useEffect, useState } from "react";
import CompanyFilters from "./CompanyFilter";
import ResultFilters from "./ResultFilters";
import EnterResult from "./EnterResult"; // Import EnterResult component
import "./EnterResultDashboard.css";

const EnterResultDashboard = ({ selectedMenu }) => {
  const [Option, setOption] = useState("EnterDetails"); // Default to "EnterDetails"
  const [companies, setCompanies] = useState([]);
  const [filteredCompanies, setFilteredCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState("");
  const [selectedDesignation, setSelectedDesignation] = useState("");
  
  useEffect(() => {
    // Fetch all companies initially
    fetch("https://your-app-name.onrender.com/api/companies")
      .then((response) => response.json())
      .then((data) => setCompanies(data))
      .catch((error) => console.error("Error fetching companies:", error));
  }, []);

  // Handle filter updates
  const handleFilter = (key, value) => {
    let filtered = [...companies];

    if (key === "clear") {
      setSelectedCompany("");
      setSelectedDesignation("");
      setFilteredCompanies([]); // Clear filters
      return;
    }

    if (key === "company") {
        setSelectedCompany(value);
    }
    
    if (key === "companies") {
        setFilteredCompanies(value);
    }

    if (key === "designation") {
      setSelectedDesignation(value); // Set selected designation
    }
    setFilteredCompanies(filtered);
  };

  // Handle option selection
  const handleOptionClick = (option) => {
    setOption(option);
  };

  // Render result content
  const renderResultContent = () => {
    if (Option === "EnterResult") {
      return (
        <div className="enterResult">
          <CompanyFilters onFilter={handleFilter} />
          <EnterResult companies={filteredCompanies} 
            selectedCompany={selectedCompany} //  Pass selectedCompany
            selectedDesignation={selectedDesignation} 
           /> 
        </div>
      );
    }
    if (Option === "ViewResult") {
      return (
        <div className="viewResult">
          <ResultFilters onFilter={handleFilter} />
        </div>
      );
    }
    return null;
  };

  // Main UI rendering logic
  const renderContent = () => {
    if (selectedMenu === "Result") {
      return (
        <>
          <div className="enter-buttons">
            <div className="breadcrumb">Dashboard &gt; Result</div>
            <button onClick={() => handleOptionClick("EnterResult")}>
              <img src="/Images/Side errow.png" height="15px" width="15px" /> Enter Result
            </button>
            <br />
            <button onClick={() => handleOptionClick("ViewResult")}>
              <img src="/Images/Side errow.png" height="15px" width="15px" /> View Result
            </button>
          </div>
          <div className="main-section">{renderResultContent()}</div>
        </>
      );
    } else {
      return <div></div>;
    }
  };

  return <div className="ResultDashboard">{renderContent()}</div>;
};

export default EnterResultDashboard;
