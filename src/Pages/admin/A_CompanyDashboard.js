import React, { useEffect, useState } from "react";
import {CompanyService} from "../../Services/CompanyServices"; 
import ResultFilters from "../../Components/admin/ResultFilters";
import CompanyTable from "../../Components/admin/CompanyTable";
import "./A_CompanyDashboard.css";
import CompanyDeatilsForm from "../../Components/admin/CompanyDetailsForm";
import CompanyDetails from "../../Components/admin/CompanyDetails";


const CompanyDashboard = () => {
    const [Option, setOption] = useState("entercompany"); // Default to "entercompany" 
    const [filteredCompany, setFilteredCompany] = useState([]);
    const [selectedCompany, setSelectedCompany] = useState(null); // Track the selected student
    const [companies, setCompany] = useState([]);

      useEffect(() => {
          CompanyService.getCompany()
            .then((response) => {
              setCompany(response.data);
              //setFilteredStudents(response.data);
            })
            .catch((error) => console.error("Error fetching company:", error));
        }, []);
      
    const handleFilter = (key, value) => {
      let filtered = [...companies]; 
  
      if (key === "clear") {
        filtered=[];
        setFilteredCompany(filtered);
        console.log(filtered);
        return;
      }
     
  
      // Apply each filter condition progressively (AND logic)
      if (key === "batch" && value) {
        filtered = filtered.filter((s) => s.batch === value); // Filter by batch
      }
  
      if (key === "program" && value) {
        filtered = filtered.filter((s) => s.course === value); // Filter by
      }
  
      if (key === "search" && value) {
        filtered = filtered.filter((s) =>s.name.toLowerCase().includes(value.toLowerCase()) // Filter by name
        );
        
      }
      setFilteredCompany(filtered);
      console.log(filtered);
        return; }

    const handleOptionClick = (option) => {
        setOption(option); // Set the currently selected option
    }; 
   
    const renderResultContent = () => {
        if (Option === "entercompany") {
            return (
                <div className="entercompany">
                    <h3 className="cheadings">Company Details</h3>
                    <CompanyDeatilsForm />
                </div>
            );
        }
        if (selectedCompany) {
          return (
            <div className="viewcompany">
              <button
                className="back-btn"
                onClick={() => setSelectedCompany(null)} // Go back to table
              >
                <img src="/Images/Back.png" height="25px" width="25px" />
              </button>
              <CompanyDetails company={selectedCompany} />
            </div>
          );
        } else {
            return (
            <div className="viewcompany">
                 <ResultFilters onFilter={handleFilter} />
                 <CompanyTable
                  companies={filteredCompany}
                  onView={handleViewCompany} // Trigger view for selected student
                  onDelete={onDelete}
            />
            </div>
            
            );
           
        }
        
        
    };
    const handleViewCompany = (company) => {
      setSelectedCompany(company); // Set the selected student
    };
    const onDelete = (id) => {
      const updatedCompany = companies.filter((company) => company.id !== id);
      setCompany(updatedCompany); // Update the state to remove the student
      // ssetFilteredCompany(updatedCompany); // Update the filtered list as well
    };
  
    const renderContent = () => {
       
        return (
            <>                
            <div className="cmp-buttons">
            <div className="breadcrumb">
            Dashboard &gt; Company
            </div>
            <button onClick={() => handleOptionClick("entercompany")}>
            <img src="/Images/Side errow.png" height="15px" width="15px" /> Enter Company Details
            </button>
            <br />
            <button onClick={() => handleOptionClick("viewcompany")}>
            <img src="/Images/Side errow.png" height="15px" width="15px" /> View Company Details
            </button>
            </div>

            {/* Render student-specific content */}
            <div className="main-section">{renderResultContent()}</div>
            </>
        );
        }
        
    
    return <div className="CompanyDashboard">{renderContent()}</div>;
};
export default CompanyDashboard;