// import Sidebar from "./Components/Sidebar";
// import Header from "./Components/Header";
// import Dashboard from "./Components/Dashboard";
// import React, { useState } from "react";
// import "./App.css"; // Ensure you include global styles

// function App() {
//   const [selectedMenu, setSelectedMenu] = useState("Student"); // Default to "Student"

//   const renderContent = () => {
//     switch (selectedMenu) {
//       case "Student":
//         return <Dashboard />;
//       case "AdminProfile":
//         return <div>Admin Profile Content</div>;
//       case "Company":
//         return <div>Company Content</div>;
//       case "Analysis":
//         return <div>Analysis Content</div>;
//       default:
//         return <div>Select a menu item to get started.</div>;
//     }
//   };

//   return (
//     <div className="app">
//       <Sidebar onMenuSelect={setSelectedMenu} />
//       <div className="main-content">
//         <Header />
//         <div className="content">{renderContent()}</div>
//       </div>
//     </div>
//   );
// }

// export default App;
import Sidebar from "./Components/Sidebar";
import Header from "./Components/Header";
import Dashboard from "./Components/Dashboard";
import AnalysisDashboard from "./Components/AnalysisDashboard"; // Import the new AnalysisDashboard component
import EnterResultDashboard  from "./Components/EnterResultDashboard"; // Import the new AnalysisDashboard component
import CompanyDashboard from "./Components/CompanyDashboard"; // Import the new CompanyDashboard component
import React, { useState } from "react";
import "./App.css"; // Ensure you include global styles

function App() {
  const [selectedMenu, setSelectedMenu] = useState("Student"); // Default to "Student"

  const renderContent = () => {
    switch (selectedMenu) {
      case "Student":
        return <Dashboard selectedMenu={selectedMenu} />;  // Pass selectedMenu to Dashboard
      case "AdminProfile":
        return <div>Admin Profile Content</div>;
      case "Company":
        return <CompanyDashboard selectedMenu={selectedMenu} />;
      case "Result":
        return <EnterResultDashboard selectedMenu={selectedMenu} />;
      case "Analysis":
        // return <Dashboard selectedMenu={selectedMenu} />;  // Pass selectedMenu to Dashboard (for Analysis)
        return <AnalysisDashboard />;
      default:
        return <div></div>;
    }
  };

  return (
    <div className="app">
      <Sidebar onMenuSelect={setSelectedMenu} /> {/* Pass the menu selection handler */}
      <div className="main-content">
        <Header />
        <div className="content">{renderContent()}</div> {/* Display the correct content */}
      </div>
    </div>
  );
}

export default App;
