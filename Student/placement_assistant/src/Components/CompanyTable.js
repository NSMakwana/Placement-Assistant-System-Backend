import React from "react";
import "./CompanyTable.css";

const CompanyTable = ({ companies, onView, onEdit, onDelete }) => {
  if (!companies || companies.length === 0) {
    return (
      <div className="no-company-message">
        <img id="nodata" src="/Images/Nodata.png" height="250px" width="250px" alt="No data available" />
        <center>No company to display</center>
      </div>
    );
  } else {
    // Initialize index explicitly
    

    console.log(companies);
    
    return (
      <div className="company-table-container">
        <table className="company-table">
          <thead>
            <tr>
              <th>Sr. No</th>
              <th>Name</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {
            companies.map((companies,index) => {
              index += 1; // Increment index explicitly
              return (
                <tr key={companies.id || index}>
                  <td>{index}</td>
                  <td>{companies.name}</td>
                  <td>
                    <div className="action">
                      <button onClick={() => onView(companies)}>View</button>
                      <button onClick={() => onEdit(companies)}>Edit</button>
                      <button
                        onClick={() => {
                          const confirmDelete = window.confirm(
                            "Are you sure you want to delete this student?"
                          );
                          if (confirmDelete) {
                            onDelete(companies.id); // Pass the student's ID to the onDelete function
                          }
                        }}
                      >
                        <img src="/Images/Delete.png" height="12px" width="15px" alt="Delete" />
                      </button>
                    </div>
                  </td>
                </tr>
              );
            })}
          </tbody>
        </table>
      </div>
    );
  }
};

export default CompanyTable;
