
// App.js
import React from 'react';
import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from './Pages/auth/loginpage';
import SignupPage from './Pages/auth/Signup';
import StudentDashboardLayout from './layouts/StudentDashboardLayout';
import AdminDashboardLayout from './layouts/AdminDashboardLayout';
import Dashboard from './Pages/student/Dashboard';
import AgreementForm from './Components/student/AgreementForm';
import StudentDetailForm from './Components/student/StudentDetailForm';
import CompanyDashboard from './Pages/student/CompanyDashboard';
import AnalysisDashboard from './Pages/student/AnalysisDashboard';
import EnterResultDashboard from './Pages/admin/EnterResultDashboard';
import A_Dashboard from './Pages/admin/A_Dashboard';
import A_CompanyDashboard from './Pages/admin/A_CompanyDashboard';
import A_AnalysisDashboard from './Pages/admin/A_AnalysisDashboard';
// import PrivateRoute from './routes/PrivateRoute';

function App() {
  return (
    <Routes>
      {/* Public Routes */}
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignupPage />} />
      
      {/* Protected Routes for Student */}
      <Route path="/student/*" element={<StudentDashboardLayout />}>  
        <Route index element={<Dashboard />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="agreement" element={<AgreementForm />} />
        <Route path="studentdetail" element={<StudentDetailForm />} />
        <Route path="company" element={<CompanyDashboard />} />
        <Route path="analysis" element={<AnalysisDashboard />} />
</Route>
        

      {/* Protected Routes for Admin (if needed) */}
      <Route path="/admin/*" element={<AdminDashboardLayout />}>
        <Route index element={<A_Dashboard />} />
        <Route path="admin_dashboard" element={<A_Dashboard />} />
        <Route path="companydetails" element={<A_CompanyDashboard />} />
        <Route path="result" element={<EnterResultDashboard />} />
        <Route path="analysis" element={<A_AnalysisDashboard />} />
       
         
        </Route>
      

      {/* Default Redirect */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

export default App;
