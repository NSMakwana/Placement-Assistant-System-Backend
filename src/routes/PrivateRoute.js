import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ allowedRoles, children }) => {
  const user = JSON.parse(localStorage.getItem("user"));
  if (user && allowedRoles.includes(user.role)) {
    return children;
  }
  return <Navigate to="/login" replace />;
};

export default PrivateRoute;
