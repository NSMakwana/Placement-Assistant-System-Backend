import React, { useEffect, useState } from "react";
// import "./EnterResult.css";

const EnterResult = ({ selectedCompany,selectedDesignation }) => {
  const [rounds, setRounds] = useState([]);
  const [studentResults, setStudentResults] = useState({});

  // Fetch rounds when a company is selected
  useEffect(() => {
    if (!selectedCompany || !selectedDesignation) {
      setRounds([]);
      return;
    }

    const fetchRounds = async () => {
      try {
        const response = await fetch(

            `https://placement-assistant-system.onrender.com/api/companies/${selectedCompany}/designations/${selectedDesignation}/rounds`
        );
        if (response.ok) {
          const data = await response.json();
          console.log("Fetched Rounds:", data); 
          setRounds(data);
        } else {
          console.error("Failed to fetch rounds.");
        }
      } catch (error) {
        console.error("Error fetching rounds:", error);
      }
    };

    fetchRounds();
  }, [selectedCompany,selectedDesignation]); // Runs when selectedCompany or Selected Designation changes

  // Handle result entry change
  const handleResultChange = (round, value) => {
    setStudentResults({
      ...studentResults,
      [round]: value,
    });
  };

  // Submit results
  const handleSubmit = async () => {
    try {
      const response = await fetch(
        "https://your-app-name.onrender.com/api/results/enter",
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(studentResults),
        }
      );
      if (response.ok) {
        alert("Results entered successfully!");
      } else {
        alert("Failed to submit results.");
      }
    } catch (error) {
      console.error("Error submitting results:", error);
    }
  };

  return (
    <div className="enter-result-container">
      {/* Table of Rounds */}
      {selectedCompany && (
        <div className="rounds-table">
          <h3>Rounds for {selectedCompany}</h3>
          <table>
            <thead>
              <tr>
                <th>Sr. No</th>
                <th>Round Name</th>
                <th>Result</th>
              </tr>
            </thead>
            <tbody>
              {rounds.length > 0 ? (
                rounds.map((round, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{round.round}</td>
                    <td>
                    <button onClick={handleSubmit}>Enter Results</button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="3">No rounds available</td>
                </tr>
              )}
            </tbody>
          </table>
          
        </div>
      )}
    </div>
  );
};

export default EnterResult;
