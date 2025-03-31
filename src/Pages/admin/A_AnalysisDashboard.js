import React, { useEffect, useState } from "react";
import "./A_AnalysisDashboard.css";
import AnalysisFilter from "../../Components/admin/AnalysisFilter";
import { Pie } from "react-chartjs-2";
import {
  Chart as ChartJS,
  ArcElement,
  Tooltip,
  Legend,
} from "chart.js";
import ChartDataLabels from "chartjs-plugin-datalabels";
import StudentService from "../../Services/StudentServices";

ChartJS.register(ArcElement, Tooltip, Legend, ChartDataLabels);

const A_AnalysisDashboard = () => {
  const [placedCount, setPlacedCount] = useState(0);
  const [nonPlacedCount, setNonPlacedCount] = useState(0);
  const [pending, setPendingCount] = useState(0);
  const [loading, setLoading] = useState(true);
  const [filteredStudents, setFilteredStudents] = useState([]);
  const [students, setStudents] = useState([]);

  useEffect(() => {
    StudentService.getStudents()
      .then((response) => {
        const students = response.data;
        setStudents(students);
        setFilteredStudents(students); // Initialize filtered students
        console.log("Students Data:", students);

        // Calculate initial counts
        const placed = students.filter((student) => student.placement_status === "placed").length;
        const nonPlaced = students.filter((student) => student.placement_status === "not placed").length;
        const pending = students.filter((student) => student.placement_status === "pending").length;

        setPlacedCount(placed);
        setNonPlacedCount(nonPlaced);
        setPendingCount(pending);
      })
      .catch((error) => console.error("Error fetching student data:", error))
      .finally(() => setLoading(false));
  }, []);

  const handleFilter = (key, value) => {
    let filtered = [...students]; // Start with all students

    // Reset filters if "clear" is selected
    if (key === "clear") {
      setFilteredStudents(students);
      const placed = students.filter((s) => s.placement_status === "placed").length;
      const nonPlaced = students.filter((s) => s.placement_status === "not placed").length;
      const pending = students.filter((s) => s.placement_status === "pending").length;

      setPlacedCount(placed);
      setNonPlacedCount(nonPlaced);
      setPendingCount(pending);
      return;
    }

    // Apply batch filter
    if (key === "batch" && value) {
      filtered = filtered.filter((s) => s.batch === value);
    }

    // Apply program filter
    if (key === "program" && value) {
      filtered = filtered.filter((s) => s.course.toLowerCase() === value.toLowerCase());
    }

    setFilteredStudents(filtered);

    // Update counts based on filtered students
    const placed = filtered.filter((s) => s.placement_status === "placed").length;
    const nonPlaced = filtered.filter((s) => s.placement_status === "not placed").length;
    const pending = filtered.filter((s) => s.placement_status === "pending").length;

    setPlacedCount(placed);
    setNonPlacedCount(nonPlaced);
    setPendingCount(pending);
  };

  const pieChartData = {
    labels: ["Placed Students", "Non-Placed Students", "Pending"],
    datasets: [
      {
        data: [placedCount, nonPlacedCount, pending],
        backgroundColor: ["#4CAF50", "#FF5252", "purple"],
        hoverBackgroundColor: ["#66BB6A", "#FF867F", "#A020F0"],
        borderColor: ["#ffffff", "#ffffff", "#ffffff"],
        borderWidth: 2,
      },
    ],
  };

  const pieChartOptions = {
    responsive: true,
    maintainAspectRatio: true,
    plugins: {
      legend: { position: "top" },
      tooltip: { enabled: true },
      datalabels: {
        color: "#ffffff",
        font: {
          size: 14,
          weight: "bold",
        },
        formatter: (value) => `${value}`, // Display the value
      },
    },
  };

  if (loading) {
    return <div className="loading">Loading data...</div>;
  }

  return (
    <div className="analysis-dashboard">
      <div className="analysis-filters">
        <AnalysisFilter onFilter={handleFilter} />
      </div>
      <Pie data={pieChartData} options={pieChartOptions} />
    </div>
  );
};

export default A_AnalysisDashboard;
