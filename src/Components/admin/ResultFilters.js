import React, { useState } from "react";
import "./ResultFilters.css";

const ResultFilters = ({ onFilter}) => {
  const [batch, setBatch] = useState("");
  const [program, setProgram] = useState("");
  const [search, setSearch] = useState("");
  

  // Function to handle filtering
  const handleFilterChange = (filterType, value) => {
    if (filterType === "batch") setBatch(value);
    if (filterType === "program") setProgram(value);
    if (filterType === "search") setSearch(value);
   
    onFilter(filterType, value); // Notify parent about the filter change
  };

  // Function to clear all filters
  const handleClearFilters = () => {
    const clearedFilters = {
      batch: "",
      program: "",
      search: "",
    };

    setBatch("");
    setProgram("");
    setSearch("");
    
    // Pass cleared filters as a single object
    onFilter("clear", clearedFilters);
  };

  return (
    <div className="ResultFilters">
      <div>
        <label>Batch</label>
        <select
          className="select"
          value={batch}
          onChange={(e) => handleFilterChange("batch", e.target.value)}
        >
          <option value="">Select </option>
          <option value="2018-2019">18-19</option>
          <option value="2019-2020">19-20</option>
          <option value="2020-2021">20-21</option>
          <option value="2021-2022">21-22</option>
          <option value="2022-2023">22-23</option>
          <option value="2023-2024">23-24</option>
        </select>
      </div>

      <div>
        <label>Program</label>
        <select
          className="select"
          value={program}
          onChange={(e) => handleFilterChange("program", e.target.value)}
        >  <option value="">Select </option>
          <option value="MCA">MCA</option>
          <option value="B.Tech">B.Tech</option>
        </select>
      </div>

      <div>
        <img src="/Images/Search.png" height="22px" width="22px" alt="search" />
        <input
          type="text"
          placeholder="Search..."
          value={search}
          onChange={(e) => handleFilterChange("search", e.target.value)}
        />
      </div>
     
      {/* Clear Filters Button */}
      <div>
        <button className="clear-btn" onClick={handleClearFilters}>
          Clear Filters
        </button>
      </div>
    </div>
  );
};

export default ResultFilters;
