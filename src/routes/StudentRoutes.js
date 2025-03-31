import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import Dashboard from '../Components/Dashboard';
import AgreementForm from '../Components/AgreementForm';
import StudentDetailForm from '../Components/StudentDetailForm';
import CompanyDashboard from '../Components/CompanyDashboard';
import AnalysisDashboard from '../Components/AnalysisDashboard';

const StudentRoutes = () => {
  return (
    <Routes>
      <Route path="dashboard" element={<Dashboard />} />
      <Route path="agreement" element={<AgreementForm />} />
      <Route path="studentdetail" element={<StudentDetailForm />} />
      <Route path="company" element={<CompanyDashboard />} />
      <Route path="analysis" element={<AnalysisDashboard />} />
      {/* Default student route */}
      <Route path="" element={<Navigate to="dashboard" replace />} />
    </Routes>
  );
};

export default StudentRoutes;
