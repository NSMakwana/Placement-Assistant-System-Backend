import React, { useState, useEffect } from "react";
import "./CompanyFilter.css";

const CompanyFilters = ({ onFilter }) => {
  const [batch, setBatch] = useState("");
  const [companies, setCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState("");
  const [program, setProgram] = useState("");
  const [search, setSearch] = useState("");

  // Fetch companies when a batch is selected
  useEffect(() => {
    const fetchCompaniesByBatch = async () => {
      if (!batch) {
        setCompanies([]);
        onFilter("companies", []); // Send empty list when batch is cleared
        return;
      }

      try {
        const response = await fetch(
          `https://placement-assistant-system.onrender.com/api/companies/batch/${batch}`
        );
        if (response.ok) {
          const data = await response.json();
          setCompanies(data);
          onFilter("companies", data); // Send filtered companies to parent
        } else {
          console.error("Failed to fetch companies for batch.");
          setCompanies([]);
          onFilter("companies", []);
        }
      } catch (error) {
        console.error("Error fetching companies by batch:", error);
        setCompanies([]);
        onFilter("companies", []);
      }
    };

    fetchCompaniesByBatch();
  }, [batch]); // Runs when batch changes

  // Handle filter changes
  const handleFilterChange = (filterType, value) => {
    if (filterType === "batch") {
      setBatch(value);
    }
    if (filterType === "company") {
      setSelectedCompany(value);
    }
    if (filterType === "program") {
      setProgram(value);
    }
    if (filterType === "search") {
      setSearch(value);
    }

    onFilter(filterType, value); // Send filter updates to parent
  };

  // Clear all filters
  const handleClearFilters = () => {
    setBatch("");
    setSelectedCompany("");
    setProgram("");
    setSearch("");

    onFilter("clear", {
      batch: "",
      company: "",
      program: "",
      search: "",
      companies: [],
    });
  };

  return (
    <div className="CompanyFilter">
      {/* Batch Filter */}
      <div>
        <label>Batch</label>
        <select
          className="cselect"
          value={batch}
          onChange={(e) => handleFilterChange("batch", e.target.value)}
        >
          <option value="">Select</option>
          <option value="2018-2019">18-19</option>
          <option value="2019-2020">19-20</option>
          <option value="2020-2021">20-21</option>
          <option value="2021-2022">21-22</option>
          <option value="2022-2023">22-23</option>
          <option value="2023-2024">23-24</option>
        </select>
      </div>

      {/* Company Filter */}
      <div>
        <label>Company</label>
        <select
          className="cselect"
          value={selectedCompany}
          onChange={(e) => handleFilterChange("company", e.target.value)}
          disabled={!batch || companies.length === 0}
        >
          <option value="">Select</option>
          {companies.map((company, index) => (
            <option key={index} value={company}>
              {company}
            </option>
          ))}
        </select>
      </div>

      {/* Program Filter */}
      <div>
        <label>Program</label>
        <select
          className="cselect"
          value={program}
          onChange={(e) => handleFilterChange("program", e.target.value)}
        >
          <option value="">Select</option>
          <option value="MCA">MCA</option>
          <option value="B.Tech">B.Tech</option>
        </select>
      </div>

      {/* Search Filter */}
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
        <button className="cclear-btn" onClick={handleClearFilters}>
          Clear Filters
        </button>
      </div>
    </div>
  );
};

export default CompanyFilters;
