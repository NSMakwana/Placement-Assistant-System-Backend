// src/layouts/StudentDashboardLayout.js
import React, {useState}from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../Components/student/Sidebar';
import Header from '../Components/student/Header';


const StudentDashboardLayout = () => {
  const [selectedMenu, setSelectedMenu] = useState('');

  return (
    <div className="dashboard-layout">
      <Sidebar />
      <div className="main-content">
        <Header />
        <div className="content">
          <Outlet context={{ selectedMenu, setSelectedMenu }}/>
        </div>
      </div>
    </div>
  );
};

export default StudentDashboardLayout;
