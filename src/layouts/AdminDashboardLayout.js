
import React, {useState}from 'react';
import { Outlet } from 'react-router-dom';
import Sidebar from '../Components/admin/Sidebar';
import Header from '../Components/admin/Header';


const AdminDashboardLayout = () => {
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

export default AdminDashboardLayout;
