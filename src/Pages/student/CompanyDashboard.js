import React, { useEffect, useState } from "react";
import {CompanyService} from "../../Services/CompanyServices";
import CompanyTable from "../../Components/admin/CompanyTable";
import CompanyDetails from "../../Components/admin/CompanyDetails";
import "./CompanyDashboard.css";
import StudentServices from "../../Services/StudentServices";
import axios from "axios";
const CompanyDashboard = ({}) => {
      
      const [filteredCompany, setFilteredCompany] = useState([]);
       const [Option, setOption] = useState("Viewcompany");
      const [batch, setBatch] = useState(null); // Track the selected batch
      const [selectedCompany, setSelectedCompany] = useState(null); // Track the selected student
      const [companies, setCompany] = useState([]);

      const storedUser = localStorage.getItem('user'); 
      const email = storedUser ? JSON.parse(storedUser).email : "";

      // useEffect(() => {
      //   StudentServices.getBatchOfStudents(email)
      //     .then((response) => {
      //       setBatch(response.data);
      //     })
      //     .catch((error) => console.error("Error fetching batch of students:", error));
      // }, []);

      useEffect(() => {
          CompanyService.getCompanyByemail(email)
            .then((response) => {
              setCompany(response.data);             
            })
            .catch((error) => console.error("Error fetching company:", error));
        }, []);
      
  
     

  
    const renderResultContent = () => {
       
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
                 
                 <CompanyTable       
                  companies={companies}      
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
  
    const handleOptionClick = (option) => {
      setOption(option); // Set the currently selected option
  }; 
    const renderContent = () => {
       
        return (
            <>                
            <div className="cmp-buttons">
            <div className="breadcrumb">
            Dashboard &gt; Company
            </div>
            
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