import React, { useEffect, useState } from "react";
import "./EnterResult.css";

const EnterResult = ({ selectedCompany }) => {
  const [rounds, setRounds] = useState([]);
  const [studentResults, setStudentResults] = useState({});

  // Fetch rounds when a company is selected
  useEffect(() => {
    if (!selectedCompany) {
      setRounds([]);
      return;
    }

    const fetchRounds = async () => {
      try {
        const response = await fetch(
          `https://your-app-name.onrender.com/api/companies/${selectedCompany}/rounds`
        );
        if (response.ok) {
          const data = await response.json();
          setRounds(data);
        } else {
          console.error("Failed to fetch rounds.");
        }
      } catch (error) {
        console.error("Error fetching rounds:", error);
      }
    };

    fetchRounds();
  }, [selectedCompany]);

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
                      <select
                        value={studentResults[round.round] || ""}
                        onChange={(e) =>
                          handleResultChange(round.round, e.target.value)
                        }
                      >
                        <option value="">Select</option>
                        <option value="Passed">Passed</option>
                        <option value="Failed">Failed</option>
                        <option value="Pending">Pending</option>
                      </select>
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
          <button onClick={handleSubmit}>Submit Results</button>
        </div>
      )}
    </div>
  );
};

export default EnterResult;
