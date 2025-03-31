import React, { useState, useEffect } from "react";
import StudentService from "../../Services/StudentServices"; 
import StudentTable from "../../Components/admin/StudentTable";
import Filters from "../../Components/admin/Filters";
import { jsPDF } from "jspdf"; // Import jsPDF
import "jspdf-autotable"; 
import StudentDetails from "../../Components/admin/StudentDetails";
import "./A_Dashboard.css";

const A_Dashboard = () => {  // Receive selectedMenu as a prop
  const [studentOption, setStudentOption] = useState(); // Default to "ViewDetails"
  const [students, setStudents] = useState([]);
  const [filteredStudents, setFilteredStudents] = useState([]);
  const [selectedStudent, setSelectedStudent] = useState(null); // Track the selected student
  const [selectedBatch, setSelectedBatch] = useState("");
  const [selectedProgram, setSelectedProgram] = useState("");
  
 


  // Fetching student data from the API on component mount
  useEffect(() => {
    StudentService.getStudents()
      .then((response) => {
        setStudents(response.data);
        //setFilteredStudents(response.data);
      })
      .catch((error) => console.error("Error fetching students:", error));
  }, []);

  // Handling button click to select options
  const handleOptionClick = (option) => {
    setStudentOption(option); // Set the currently selected option
    setSelectedStudent(null); // Reset selected student when changing options
  }; 

  // Handling filter logic
  const handleFilter = (key, value) => {
    let filtered = [...students]; 

    if (key === "clear") {
      filtered=[];
      setFilteredStudents(filtered);
      console.log(filtered);
      return;
    }
   

    // Apply each filter condition progressively (AND logic)
    if (key === "batch" && value) {
      setSelectedBatch(value);
    }

    if (key === "program" && value) {
      setSelectedProgram(value);
    }

    if (key === "search" && value) {
      filtered = filtered.filter((s) =>s.name.toLowerCase().includes(value.toLowerCase()) // Filter by name
      );
      setFilteredStudents(filtered);
      console.log(filtered);
      return; 
    }
    if(selectedBatch && selectedProgram)
    {
      if (selectedBatch){
        filtered = filtered.filter((s) => s.batch === selectedBatch); // Filter by batch
      }
      if (selectedProgram){
        filtered = filtered.filter((s) => s.course === selectedProgram); // Filter by program
      }
      if (key === "sscPercentage" && value) {
        const sscLimit = parseInt(value, 10); // Extract the number from the string like '<60'
        filtered = filtered.filter((s) => s.ssc.percentage <= sscLimit); // Filter by SSC percentage
      }

      if (key === "hscPercentage" && value) {
        const hscLimit = parseInt(value, 10);
        filtered = filtered.filter((s) => s.hsc.percentage <= hscLimit); // Filter by HSC percentage
      }

      if (key === "bachelorPercentage" && value) {
        const bachelorLimit = parseInt(value, 10);
        filtered = filtered.filter((s) => s.bachelor.percentage <= bachelorLimit); // Filter by Bachelor's percentage
      }

      if (key === "drops" && value) {
        filtered = filtered.filter((s) => s.drops === parseInt(value, 10)); // Filter by drops
      }
    
      setFilteredStudents(filtered);
      console.log(filtered);
      
    }else{
      filtered = [];
      setFilteredStudents(filtered);
      return;
      }
    
   
   
  };

  //delete student
  const onDelete = (eno) => {
    const updatedStudents = students.filter((student) => student.eno !== eno);
    setStudents(updatedStudents); // Update the state to remove the student
    // ssetFilteredStudents(updatedStudents); // Update the filtered list as well
  };

  //downloding filter data
  const downloadFilteredCSV = () => {
     const headers = [
      "Sr. No",
      "Name",
      "Batch",
      "Program",
      "Email",
      "Phone Number",
      "Date of Birth",
      "Gender",
      "Nationality",
      "SSC Marks",
      "SSC Total Marks",
      "SSC Percentage",
      "HSC Marks",
      "HSC Total Marks",
      "HSC Percentage",
      "Bachelor Marks",
      "Bachelor Total Marks",
      "Bachelor Percentage",
      "Master Marks",
      "Master Total Marks",
      "Master Percentage",
      "Address Block Number",
      "Address Building Name",
      "Address Area",
      "Address Landmark",
      "Address Pincode",
      "Address City",
      "Address State",
      "Drops",
      "Remarks",
    ];
  
    // Format filtered student data as CSV rows
    const rows = filteredStudents.map((student, index) => [
      index + 1,
      student.name,
      student.batch,
      student.course,
      student.email,
      student.phno,
      student.dob,
      student.gender,
      student.nationality,
      student.ssc.marks,
      student.ssc.totalMarks,
      student.ssc.percentage,
      student.hsc.marks,
      student.hsc.totalMarks,
      student.hsc.percentage,
      student.bachelor.marks,
      student.bachelor.totalMarks,
      student.bachelor.percentage,
      student.master.marks,
      student.master.totalMarks,
      student.master.percentage,
      student.address.blockNum,
      student.address.buildingName,
      student.address.area,
      student.address.landmark,
      student.address.pincode,
      student.address.city,
      student.address.state,
      student.drops,
      student.remarks,
    ]);
  
    // Combine headers and rows
    const csvContent = [
      headers.join(","), // Join headers with commas
      ...rows.map((row) => row.join(",")) // Join each row with commas
    ].join("\n"); // Join all rows with a newline
  
    // Create a Blob from the CSV content
    const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
    const url = URL.createObjectURL(blob);
  
    // Create a link to trigger the download
    const link = document.createElement("a");
    link.setAttribute("href", url);
    link.setAttribute("download", "filtered_students_details.csv"); // Default file name for download
    link.click(); // Trigger the download
  };
  
  const downloadFilteredPDF = () => {
    const doc = new jsPDF();

    // Add title
    doc.setFontSize(16);
    doc.text("Filtered Student Details", 10, 10);

    // Define table headers
    const headers = [
      "Sr. No",
      "Name",
      "Batch",
      "Program",
      "Email",
      "Phone",
      "SSC %",
      "HSC %",
      "Bachelor %",
    ];

    // Define table rows
    const rows = filteredStudents.map((student, index) => [
      index + 1,
      student.name,
      student.batch,
      student.course,
      student.email,
      student.phno,
      student.ssc.percentage,
      student.hsc.percentage,
      student.bachelor.percentage,
    ]);

    // Add table to PDF
    doc.autoTable({
      head: [headers],
      body: rows,
      startY: 20, // Start below the title
    });

    // Save the PDF
    doc.save("filtered_students_details.pdf");
  };

  

  // Handling student selection for details view
  const handleViewStudent = (student) => {
    setSelectedStudent(student); // Set the selected student
  };

  
  // Render content based on studentOption
  const renderStudentContent = () => {
    if (studentOption === "EnterDetails") {
      return <div></div>;
    }

    if (studentOption === "ViewDetails") {
      if (selectedStudent) {
        return (
          <div className="viewdetails">
            <button
              className="back-btn"
              onClick={() => setSelectedStudent(null)} // Go back to table
            >
              <img src="/Images/Back.png" height="25px" width="25px" />
            </button>
            <StudentDetails student={selectedStudent} />
          </div>
        );
      } else {
        return (
          <div className="viewdetails">
            <Filters onFilter={handleFilter} downloadCSV={downloadFilteredCSV} downloadPDF={ downloadFilteredPDF}/>
            <StudentTable
              students={filteredStudents}
              onView={handleViewStudent} // Trigger view for selected student
              onDelete={onDelete}
            />
          </div>
        );
      }
    }

    if (studentOption === "DownloadReports") {
      return <div></div>;
    }

    return null;
  };

  // Main rendering logic
  const rendersContent = () => {
  
      return (
        <>
      
          {/* Navigation Buttons for Student Options */}
            {/* Breadcrumb */}
        
          <div className="buttons">
          <div className="breadcrumb">
          Dashboard &gt; Student
        </div>
            <button onClick={() => handleOptionClick("EnterDetails")}>
              <img src="/Images/Side errow.png" height="15px" width="15px" /> Enter Details
            </button>
            <button onClick={() => handleOptionClick("DownloadReports")}>
              <img src="/Images/Side errow.png" height="15px" width="15px" /> Download Reports
              <img src="/Images/download.png" height="18px" width="27px" />
            </button>
            <br />
            <button onClick={() => handleOptionClick("ViewDetails")}>
              <img src="/Images/Side errow.png" height="15px" width="15px" /> View Details
            </button>
          </div>

        

          {/* Render student-specific content */}
          <div className="main-section">{renderStudentContent()}</div>
        </>
      );
    }
  
    
  

  return <div className="dashboard">{rendersContent()}</div>;
  };

export default A_Dashboard;
 