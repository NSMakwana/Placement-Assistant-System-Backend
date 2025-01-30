import React, { useEffect, useState } from "react";
import StudentService from "../Services/StudentServices"; 
import ResultFilters from "./ResultFilters";
import "./EnterResultDashboard.css";
import CompanyFilters from "./CompanyFilter";



const EnterResultDashboard = ({ selectedMenu }) => {
    const [Option, setOption] = useState("EnterDetails"); // Default to "EnterDetails" 
    const [filteredStudents, setFilteredStudents] = useState([]);
    const [students, setStudents] = useState([]);

    useEffect(() => {
        StudentService.getStudents()
          .then((response) => {
            setStudents(response.data);
            //setFilteredStudents(response.data);
          })
          .catch((error) => console.error("Error fetching students:", error));
      }, []);

    const handleOptionClick = (option) => {
        setOption(option); // Set the currently selected option
    }; 
    const handleFilter = (key, value) => {
        let filtered = [...students]; 
    
        if (key === "clear") {
          filtered=[];
          return;
        } 
    
        // Apply each filter condition progressively (AND logic)
        if (key === "batch" && value) {
            filtered = filtered.filter((s) => s.batch === s.batch.value); // Filter by batch
        }
    
        if (key === "program" && value) {
            filtered = filtered.filter((s) => s.program === s.program.value.toLowerCase()); // Filter by
        }
        if (key === "search" && value) {
            filtered = filtered.filter((s) => s.program === s.program.value.toLowerCase()); // Filter by
        }
        setFilteredStudents(filtered);
        console.log(filtered);
        };
       
    
    
    const renderResultContent = () => {
        if (Option === "EnterResult") {
            return (
                <div className="enterResult">
                   <CompanyFilters onFilter={handleFilter} />
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

    const renderContent = () => {
        if (selectedMenu === "Result") {
        return (
            <>                
            <div className="enter-buttons">
            <div className="breadcrumb">
            Dashboard &gt; Result
            </div>
            <button onClick={() => handleOptionClick("EnterResult")}>
            <img src="/Images/Side errow.png" height="15px" width="15px" /> Enter Result
            </button>
            <br />
            <button onClick={() => handleOptionClick("ViewResult")}>
            <img src="/Images/Side errow.png" height="15px" width="15px" /> View Result
            </button>
            </div>

            {/* Render student-specific content */}
            <div className="main-section">{renderResultContent()}</div>
            </>
        );
        }
        else{
          <div></div>
        }

    };    
    
    return <div className="ResultDashboard">{renderContent()}</div>;
};
export default EnterResultDashboard;