import React, { useEffect, useState } from "react";
import "./AnalysisDashboard.css";
import StudentService from "../../Services/StudentServices";
import { Pie } from "react-chartjs-2"; // Import Pie chart from Chart.js
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";
 // Import your service to fetch student data

// Register Chart.js components
ChartJS.register(ArcElement, Tooltip, Legend);

const AnalysisDashboard = () => {
  const [placedCount, setPlacedCount] = useState(0);
  const [nonPlacedCount, setNonPlacedCount] = useState(0);
  const [pending, setPendingCount] = useState(0);
  const [loading, setLoading] = useState(true); // New state for loading

  // Fetch student data and calculate placed/non-placed counts
  useEffect(() => {
    StudentService.getStudents()
      .then((response) => {
        const students = response.data;
        console.log("Students Data:", students);
        const placed = students.filter((student) => student.placement_status === "placed").length;
        const nonPlaced = students.filter((student) => student.placement_status === "not placed").length;
        const pending = students.filter((student) => student.placement_status === "pending").length;

        setPlacedCount(placed);
        setNonPlacedCount(nonPlaced);
        setPendingCount(pending);
      })
      .catch((error) => console.error("Error fetching student data:", error))
      .finally(() => setLoading(false)); // Set loading to false after data is fetched
  }, []);

  // Data for the Pie Chart
  const pieChartData = {
    labels: ["Placed Students", "Non-Placed Students", "Pending"],
    datasets: [
      {
        data: [placedCount, nonPlacedCount, pending],
        backgroundColor: ["#4CAF50", "#FF5252", "purple"], // Colors for each segment
        hoverBackgroundColor: ["#66BB6A", "#FF867F", "#A020F0"], // Hover colors
        borderColor: ["#ffffff", "#ffffff", "#ffffff"], // Border color
        borderWidth: 2,
      },
    ],
  };

  // Options for the Pie Chart
  const pieChartOptions = {
    responsive: true,
    maintainAspectRatio: true, 
    plugins: {
      legend: { position: "top" },
      tooltip: { enabled: true },
    },
  };

  // Loading State
  if (loading) {
    return <div className="loading">Loading data...</div>; // Show loading message
  }

  // Render Pie Chart
  return (
    <div className="analysis-dashboard">
      {/* <h2>Placement Analysis</h2> */}
      <Pie data={pieChartData} options={pieChartOptions} />
    </div>
  );
};

export default AnalysisDashboard;
