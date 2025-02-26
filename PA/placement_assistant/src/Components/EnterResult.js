import React, { useState, useEffect } from "react";
// import "./EnterResult.css";

const EnterResult = ({ companies }) => {
  const [selectedCompany, setSelectedCompany] = useState("");
  const [rounds, setRounds] = useState([]);

  // Fetch rounds for selected company
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

  return (
    <div className="enter-result-container">
      {/* Company Selection */}
      <div className="filter-group">
        <label>Company</label>
        <select
          value={selectedCompany}
          onChange={(e) => setSelectedCompany(e.target.value)}
          disabled={companies.length === 0}
        >
          <option value="">Select</option>
          {companies.map((company, index) => (
            <option key={index} value={company.name}>
              {company.name}
            </option>
          ))}
        </select>
      </div>

      {/* Table of Rounds */}
      {selectedCompany && (
        <div className="rounds-table">
          <table>
            <thead>
              <tr>
                <th>Sr. No</th>
                <th>Round Name</th>
                <th>Action</th>
              </tr>
            </thead>
            <tbody>
              {rounds.length > 0 ? (
                rounds.map((round, index) => (
                  <tr key={index}>
                    <td>{index + 1}</td>
                    <td>{round.round}</td>
                    <td>
                      <button onClick={() => console.log("Enter result for", round)}>
                        Enter Result
                      </button>
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
