import React, { useState, useEffect } from "react";
import Sidebar from "./Components/Sidebar";
import Header from "./Components/Header";
import Dashboard from "./Components/Dashboard";
import AnalysisDashboard from "./Components/AnalysisDashboard";
import StudentDetailsDashboard from "./Components/StudentDetailDashboard";
import LoginPage from "./Components/loginpage";
import SignupPage from "./Components/Signup";
import AgreementForm from "./Components/AgreementForm";
import StudentDetailForm from "./Components/StudentDetailForm";
import CompanyDashboard from "./Components/CompanyDashboard"; 

import "./App.css";

function App() {
  const [isLoggedIn, setIsLoggedIn] = useState(() => {
    return localStorage.getItem("isLoggedIn") === "true";
  }); // Track login status
  const [selectedMenu, setSelectedMenu] = useState(""); // Default menu
  const [currentPage, setCurrentPage] = useState("login"); // Track the current page for login/signup

  useEffect(() => {
    // Save login state to localStorage whenever it changes
    localStorage.setItem("isLoggedIn", isLoggedIn);
  }, [isLoggedIn]);

  // Handle successful login
  const handleLogin = () => {
    setIsLoggedIn(true);
  };
  const handleLogout = () => {
    setIsLoggedIn(false);
    setCurrentPage("login"); // Redirect to login page after logout
  };
  // Render content for the sidebar menus
  const renderMenuContent = () => {
    switch (selectedMenu) {
      case "Dashboard":
        return <Dashboard />;
      case "AgreementForm":
          return <AgreementForm />;
      case "StudentDetail":
        return <StudentDetailForm />;
        case "Company":
          return <CompanyDashboard selectedMenu={selectedMenu} />;
      case "Analysis":
        return <AnalysisDashboard />;
      default:
        return <div></div>;
    }
  };

  // Render the appropriate page based on login state and current page
  const renderPage = () => {
    if (!isLoggedIn) {
      switch (currentPage) {
        case "login":
          return <LoginPage onLogin={handleLogin} navigateToSignup={() => setCurrentPage("signup")} />;
        case "signup":
          return <SignupPage navigateToLogin={() => setCurrentPage("login")} />;
        default:
          return null;
      }
    } else {
      // Show the main dashboard with sidebar and header
      return (
        <>
          <Sidebar onMenuSelect={setSelectedMenu} />
          <div className="main-content">
            <Header  onLogout={handleLogout}/>
            <div className="content">{renderMenuContent()}</div>
          </div>
        </>
      );
    }
  };

  return <div className="app">{renderPage()}</div>;
}

export default App;
