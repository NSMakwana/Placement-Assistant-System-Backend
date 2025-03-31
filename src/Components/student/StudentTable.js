import React from "react";
import "./StudentTable.css";
const StudentTable = ({ students, onView, onEdit,onDelete }) => {
  return (
    <div className="student-table-container">
      <table  className="student-table">
        <thead>
          <tr>
            <th>Sr. No</th>
            <th>Name</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {students.map((student, index) => (
            <tr key={student.eno}>
              <td>{index + 1}</td>
              <td>{student.name}</td>
              <td>
                <div className="action">
                <button  onClick={() => onView(student)}>View</button>
                <button  onClick={() => onEdit(student)}>Edit</button>
                <button   onClick={() => {
                      const confirmDelete = window.confirm(
                        "Are you sure you want to delete this student?"
                      );
                      if (confirmDelete) {
                        onDelete(student.eno); // Pass the student's ID to the onDelete function
                      }
                    }}>
                  <img src="/Images/Delete.png" height="12px" width="15px" /></button>
                  </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default StudentTable;
