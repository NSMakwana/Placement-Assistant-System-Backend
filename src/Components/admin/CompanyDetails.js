import React from "react";
import { jsPDF } from "jspdf";
import "jspdf-autotable";
import "./CompanyDetails.css";
const CompanyDetails = ({ company }) => {
  const downloadPDF = () => {
    const doc = new jsPDF();

    // Add the title
    doc.setFontSize(18);
    doc.text(`${company.name}`, 10, 10);

    // General Details
    const generalDetails = [
      ["Batch", company.batch],
      ["Address", company.address ? `${company.address.city}, ${company.address.state}` : "N/A"],
      ["Contact Person", company.contactPerson ? company.contactPerson.name : "N/A"],
      ["Email", company.contactPerson ? company.contactPerson.email : "N/A"],
      ["Mobile", company.contactPerson ? company.contactPerson.mobile : "N/A"],
    ];

    doc.autoTable({
      head: [["Company Name", `${company.name}`]],
      body: generalDetails,
      startY: 20,
    });

    // Designation Details
    if (company.designations?.length > 0) {
      company.designations.forEach((designation, index) => {
        const designationDetails = [
          ["Designation", designation.designation],
          ["Bond", designation.bond || "N/A"],
          ["Location", designation.location || "N/A"],
          ["Package", designation.package || "N/A"],
          ["Qualifications", designation.requiredQualifications?.join(", ") || "N/A"],
        ];

        doc.autoTable({
          // head: [["Field", "Value"]],
          body: designationDetails,
          startY: doc.lastAutoTable.finalY + 10,
          theme: "striped",
        });

        // Placement Process
        if (designation.placementProcess?.length > 0) {
          doc.setFontSize(12);
          doc.text(`Placement Process for ${designation.designation}`, 15, doc.lastAutoTable.finalY + 8);
          const processDetails = designation.placementProcess.map((process) => [
            process.roundNumber,
            process.round,
            process.description || "N/A",
          ]);

          doc.autoTable({
            head: [["Round #", "Round Name", "Description"]],
            body: processDetails,
            startY: doc.lastAutoTable.finalY + 10,
            theme: "grid",
          });
        }
      });
    }

    // Save the PDF
    doc.save(`${company.name}.pdf`);
  };

  return (
    <div className="company-details-container">
      <h3>Company Details</h3>
      <table>
        <tbody>
          {/* General Details */}
          <tr>
            <th>Name</th>
            <td>{company.name}</td>
          </tr>
          <tr>
            <th>Batch</th>
            <td>{company.batch}</td>
          </tr>
          <tr>
            <th>Contact Person</th>
            <td>{company.contactPerson?.name || "N/A"}</td>
          </tr>
          <tr>
            <th>Email</th>
            <td>{company.contactPerson?.email || "N/A"}</td>
          </tr>
          <tr>
            <th>Mobile</th>
            <td>{company.contactPerson?.mobile || "N/A"}</td>
          </tr>
        </tbody>
      </table>

      {/* Designations */}
      {company.designations?.map((designation, index) => (
        <div key={index} style={{ marginTop: "20px" }}>
          <h4>Designation: {designation.designation}</h4>
          <table>
            <tbody>
              <tr>
                <th>Bond</th>
                <td>{designation.bond || "N/A"}</td>
              </tr>
              <tr>
                <th>Location</th>
                <td>{designation.location || "N/A"}</td>
              </tr>
              <tr>
                <th>Package</th>
                <td>{designation.package || "N/A"}</td>
              </tr>
              <tr>
                <th>Required Qualifications</th>
                <td>{designation.requiredQualifications?.join(", ") || "N/A"}</td>
              </tr>
            </tbody>
          </table>

          {/* Placement Process */}
          {designation.placementProcess?.length > 0 && (
            <div>
              <h5>Placement Process: {designation.designation}</h5>
              <table>
                <thead>
                  <tr>
                    <th>Round #</th>
                    <th>Round Name</th>
                    <th>Description</th>
                  </tr>
                </thead>
                <tbody>
                  {designation.placementProcess.map((process, processIndex) => (
                    <tr key={processIndex}>
                      <td>{process.roundNumber}</td>
                      <td>{process.round}</td>
                      <td>{process.description || "N/A"}</td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      ))}

      {/* Download Button */}
      <button onClick={downloadPDF}>Download PDF</button>
    </div>
  );
};

export default CompanyDetails;
