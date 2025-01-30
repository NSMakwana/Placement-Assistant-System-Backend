import React from "react";
import "./StudentDetails.css"; // Ensure to style the table properly
import { jsPDF } from "jspdf";
import "jspdf-autotable";
import html2canvas from "html2canvas";
const StudentDetails = ({ student }) => {
  const downloadPDF = async () => {
    const doc = new jsPDF();

    // Title
    doc.setFontSize(18);
    doc.text(`${student.name}`, 10, 10);

    // Add the photo
    const photoElement = document.querySelector(".passport-photo");
    if (photoElement) {
      const photoCanvas = await html2canvas(photoElement);
      const photoData = photoCanvas.toDataURL("image/png");
      doc.addImage(photoData, "PNG", 150, 10, 40, 40); // Adjust the dimensions as needed
    }

    // Details in Table Format
    // const headers = [["Field", "Value"]];
    const rows = [
      ["Enrollment Number", student.eno],
      ["Name", student.name],
      ["Course", student.course],
      ["Email", student.email],
      ["Phone", student.phno],
      ["Date of Birth", new Date(student.dob).toLocaleDateString()],
      ["Gender", student.gender],
      ["Batch", student.batch],
      ["Block Number", student.address.blockNum],
      ["Building Name", student.address.buildingName],
      ["Area", student.address.area],
      ["Landmark", student.address.landmark],
      ["Pincode", student.address.pincode],
      ["City", student.address.city],
      ["State", student.address.state],
      ["SSC Percentage", `${student.ssc.percentage}%`],
      ["HSC Percentage", `${student.hsc.percentage}%`],
      ["Bachelor's Percentage", `${student.bachelor.percentage}%`],
      ["Master's Percentage", `${student.master.percentage}%`],
      ["Drops", student.drops],
      ["Remarks", student.remarks],
    ];

    doc.autoTable({
      // head: headers,
      body: rows,
      startY: 60, // Start below the photo
    });

    // Save the PDF
    doc.save(`${student.name}.pdf`);
  };

  return (
    <div className="student-details-container">
      <h3>Student Details</h3>
      <div className="details-layout">
        {/* Details Table */}
        <div className="details-table-container">
      <table className="details-table">
        <tbody>
          {/* Personal Details */}
          <tr>
            <th>Enrollment Number</th>
            <td>{student.eno}</td>
          </tr>
          <tr>
            <th>Name</th>
            <td>{student.name}</td>
          </tr>
          <tr>
            <th>Course</th>
            <td>{student.course}</td>
          </tr>
          <tr>
            <th>Email</th>
            <td>{student.email}</td>
          </tr>
          <tr>
            <th>Phone</th>
            <td>{student.phno}</td>
          </tr>
          <tr>
            <th>Date of Birth</th>
            <td>{new Date(student.dob).toLocaleDateString()}</td>
          </tr>
          <tr>
            <th>Gender</th>
            <td>{student.gender}</td>
          </tr>
          <tr>
            <th>Batch</th>
            <td>{student.batch}</td>
          </tr>

          {/* Address Details */}
          <tr>
            <th>Block Number</th>
            <td>{student.address.blockNum}</td>
          </tr>
          <tr>
            <th>Building Name</th>
            <td>{student.address.buildingName}</td>
          </tr>
          <tr>
            <th>Area</th>
            <td>{student.address.area}</td>
          </tr>
          <tr>
            <th>Landmark</th>
            <td>{student.address.landmark}</td>
          </tr>
          <tr>
            <th>Pincode</th>
            <td>{student.address.pincode}</td>
          </tr>
          <tr>
            <th>City</th>
            <td>{student.address.city}</td>
          </tr>
          <tr>
            <th>State</th>
            <td>{student.address.state}</td>
          </tr>

          {/* Educational Details */}
          <tr>
            <th>SSC Marks</th>
            <td>{student.ssc.marks} / {student.ssc.totalMarks}</td>
          </tr>
          <tr>
            <th>SSC Percentage</th>
            <td>{student.ssc.percentage}%</td>
          </tr>
          <tr>
            <th>SSC Year</th>
            <td>{student.ssc.year}</td>
          </tr>
          <tr>
            <th>SSC Board</th>
            <td>{student.ssc.board}</td>
          </tr>

          <tr>
            <th>HSC Marks</th>
            <td>{student.hsc.marks} / {student.hsc.totalMarks}</td>
          </tr>
          <tr>
            <th>HSC Percentage</th>
            <td>{student.hsc.percentage}%</td>
          </tr>
          <tr>
            <th>HSC Stream</th>
            <td>{student.hsc.stream}</td>
          </tr>
          <tr>
            <th>HSC Board</th>
            <td>{student.hsc.board}</td>
          </tr>
          <tr>
            <th>HSC Year</th>
            <td>{student.hsc.year}</td>
          </tr>

          <tr>
            <th>Bachelor's Degree</th>
            <td>{student.bachelor.degree} ({student.bachelor.university})</td>
          </tr>
          <tr>
            <th>Bachelor's Marks</th>
            <td>{student.bachelor.marks} / {student.bachelor.totalMarks}</td>
          </tr>
          <tr>
            <th>Bachelor's Percentage</th>
            <td>{student.bachelor.percentage}%</td>
          </tr>
          <tr>
            <th>Bachelor's Year</th>
            <td>{student.bachelor.year}</td>
          </tr>

          <tr>
            <th>Master's Degree</th>
            <td>{student.master.degree} ({student.master.university})</td>
          </tr>
          <tr>
            <th>Master's Marks</th>
            <td>{student.master.marks} / {student.master.totalMarks}</td>
          </tr>
          <tr>
            <th>Master's Percentage</th>
            <td>{student.master.percentage}%</td>
          </tr>
          <tr>
            <th>Master's Year</th>
            <td>{student.master.year}</td>
          </tr>

          {/* Other Details */}
          <tr>
            <th>Lateral Entry</th>
            <td>{student.lEntry ? "Yes" : "No"}</td>
          </tr>
          <tr>
            <th>Number of Drops</th>
            <td>{student.drops}</td>
          </tr>
          <tr>
            <th>Remarks</th>
            <td>{student.remarks}</td>
          </tr>
        </tbody>
      </table>
      </div>

      {/* Student Photo */}
      <div className="student-photo">
        <img
          src={student.photoUrl || "/Images/User_photo_default.png"}
          alt={`${student.name}'s Passport`}
          className="passport-photo"
        />
      </div>
      <button className="download" onClick={downloadPDF}>
        <img src="/Images/Download.png" height="22px" width="25px" alt="search" />
          Download(.pdf)
        </button>
    
      </div>
      </div>
      
  );
};

export default StudentDetails;
