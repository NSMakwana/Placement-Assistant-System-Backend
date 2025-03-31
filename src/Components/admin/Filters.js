import React, { useState } from "react";
import "./filters.css";

const Filters = ({ onFilter, downloadCSV, downloadPDF }) => {
  const [batch, setBatch] = useState("");
  const [program, setProgram] = useState("");
  const [search, setSearch] = useState("");
  const [sscPercentage, setSSCPercentage] = useState("");
  const [hscPercentage, setHSCPercentage] = useState("");
  const [bachelorPercentage, setBachelorPercentage] = useState("");
  const [drops, setDrops] = useState("");

  // Function to handle filtering
  const handleFilterChange = (filterType, value) => {
    if (filterType === "batch") setBatch(value);
    if (filterType === "program") setProgram(value);
    if (filterType === "search") setSearch(value);
    if (filterType === "sscPercentage") setSSCPercentage(value);
    if (filterType === "hscPercentage") setHSCPercentage(value);
    if (filterType === "bachelorPercentage") setBachelorPercentage(value);
    if (filterType === "drops") setDrops(value);

    onFilter(filterType, value); // Notify parent about the filter change
  };

  // Function to clear all filters
  const handleClearFilters = () => {
    const clearedFilters = {
      batch: "",
      program: "",
      search: "",
      sscPercentage: "",
      hscPercentage: "",
      bachelorPercentage: "",
      drops: "",
    };

    setBatch("");
    setProgram("");
    setSearch("");
    setSSCPercentage("");
    setHSCPercentage("");
    setBachelorPercentage("");
    setDrops("");

    // Pass cleared filters as a single object
    onFilter("clear", clearedFilters);
  };

  return (
    <div className="filters">
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
        > <option value="">Select </option>
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
      <div>
        <button className="download-btn" onClick={downloadCSV}>
          <img src="/Images/Download.png" height="22px" width="25px" alt="search" />
          Download(.CSV)
        </button>
      </div>
      <div>
        <button className="download-btn" onClick={downloadPDF}>
          <img src="/Images/Download.png" height="22px" width="25px" alt="search" />
          Download(.pdf)
        </button>
      </div>
      <div className="ssc-filter">
        <label>S.S.C.</label>
        <select
          className="select"
          value={sscPercentage}
          onChange={(e) => handleFilterChange("sscPercentage", e.target.value)}
        >
          <option value="">Select </option>
          <option value="50">&lt;50%</option>
          <option value="60">&lt;60%</option>
          <option value="70">&lt;70%</option>
          <option value="80">&lt;80%</option>
        </select>
      </div>

      <div className="hsc-filter">
        <label>H.S.C.</label>
        <select
          className="select"
          value={hscPercentage}
          onChange={(e) => handleFilterChange("hscPercentage", e.target.value)}
        >
          <option value="">Select </option>
          <option value="50">&lt;50%</option>
          <option value="60">&lt;60%</option>
          <option value="70">&lt;70%</option>
          <option value="80">&lt;80%</option>
        </select>
      </div>

      <div>
        <label>Bachelor</label>
        <select
          className="select"
          value={bachelorPercentage}
          onChange={(e) => handleFilterChange("bachelorPercentage", e.target.value)}
        >  <option value="">Select </option>
          <option value="50">&lt;50%</option>
          <option value="60">&lt;60%</option>
          <option value="70">&lt;70%</option>
          <option value="80">&lt;80%</option>
        </select>
      </div>

      <div>
        <label>Drops</label>
        <select
          className="select"
          value={drops}
          onChange={(e) => handleFilterChange("drops", e.target.value)}
        >
          <option value="">Select </option>
          <option value="0">0</option>
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4+</option>
        </select>
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

export default Filters;
