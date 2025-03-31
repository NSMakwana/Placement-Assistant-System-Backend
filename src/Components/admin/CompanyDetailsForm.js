import React, { useState } from 'react';
import './CompanyDetailsForm.css';

const CompanyDetailsForm = () => {
  const [formData, setFormData] = useState({
    name: '',
    batch: '',
    address: {
      blockNo: '',
      buildingName: '',
      area: '',
      landmark: '',
      state: '',
      city: '',
      pincode: '',
    },
    contactPerson: {
      name: '',
      designation: '',
      email: '',
      mobile: '',
    },
    designations: [],
  });

  const handleChange = (field, value) => {
    setFormData({ ...formData, [field]: value });
  };

  const handleNestedChange = (section, field, value) => {
    setFormData({
      ...formData,
      [section]: {
        ...formData[section],
        [field]: value,
      },
    });
  };

  const handleDesignationChange = (index, field, value) => {
    const updatedDesignations = [...formData.designations];
    if (field === 'requiredQualifications') {
      updatedDesignations[index][field] = value.split(',').map((q) => q.trim()); // Convert string to array
    } else {
      updatedDesignations[index][field] = value;
    }
    setFormData({ ...formData, designations: updatedDesignations });
  };

  const handleProcessChange = (dIndex, pIndex, field, value) => {
    const updatedDesignations = [...formData.designations];
    updatedDesignations[dIndex].placementProcess[pIndex][field] = value;
    setFormData({ ...formData, designations: updatedDesignations });
  };

  const addDesignation = () => {
    setFormData({
      ...formData,
      designations: [
        ...formData.designations,
        {
          designation: '',
          Package: '',
          bond: '',
          location: '',
          RequiredQualifications: [],
          placementProcess: [],
        },
      ],
    });
  };

  const removeDesignation = (index) => {
    const updatedDesignations = formData.designations.filter(
      (_, idx) => idx !== index
    );
    setFormData({ ...formData, designations: updatedDesignations });
  };

  const addProcess = (dIndex) => {
    const updatedDesignations = [...formData.designations];
    updatedDesignations[dIndex].placementProcess.push({
      roundNumber: '',
      round: '',
      description: '',
    });
    setFormData({ ...formData, designations: updatedDesignations });
  };

  const removeProcess = (dIndex, pIndex) => {
    const updatedDesignations = [...formData.designations];
    updatedDesignations[dIndex].placementProcess = updatedDesignations[ 
      dIndex 
    ].placementProcess.filter((_, idx) => idx !== pIndex);
    setFormData({ ...formData, designations: updatedDesignations });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log('Submitting form data:', formData);

    try {
      const response = await fetch('https://placement-assistant-system.onrender.com/api/companies', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        const data = await response.json();
        console.log('Response:', data);
        alert('Company details submitted successfully!');
      } else {
        console.error('Error:', response.statusText);
        alert('Failed to submit the form.');
      }
    } catch (error) {
      console.error('Error submitting data:', error);
      alert('An error occurred while submitting the form.');
    }
  };

  return (
    <form className="company-form" onSubmit={handleSubmit}>
      {/* Company Info */}
      <table className="company-info">
        <tr>
          <td className="cname">
            <label>Company Name</label>
            <input
              type="text"
              value={formData.name}
              onChange={(e) => handleChange('name', e.target.value)}
              required
            />
          </td>
          <td>
            <label>Batch</label>
            <input
              type="text"
              value={formData.batch}
              onChange={(e) => handleChange('batch', e.target.value)}
              required
              placeholder="2023-2024"
            />
          </td>
        </tr>
      </table>

      {/* Address Fields */}
      <div className="headings">Address</div>
      {Object.keys(formData.address).map((key) => (
        <div key={key}>
          <label>{key}</label>
          <input
            type="text"
            value={formData.address[key]}
            onChange={(e) => handleNestedChange('address', key, e.target.value)}
          />
        </div>
      ))}

      {/* Contact Person Fields */}
      <div className="headings">Contact Person</div>
      {Object.keys(formData.contactPerson).map((key) => (
        <div key={key}>
          <label>{key}</label>
          <input
            type={key === 'email' ? 'email' : 'text'}
            value={formData.contactPerson[key]}
            onChange={(e) =>
              handleNestedChange('contactPerson', key, e.target.value)
            }
            required
          />
        </div>
      ))}

      {/* Designations Section */}
      <div className="headings">Designations</div>
      {formData.designations.map((designation, dIndex) => (
        <div key={dIndex} className="designation">
          {/* Designation Heading */}
          <div className="headings">
            Designation {dIndex + 1}
            {/* Remove Designation Button */}
            <button
              type="button"
              className="remove-designation-btn"
              onClick={() => removeDesignation(dIndex)}
            >
              Remove Designation
            </button>
          </div>

          {/* Designation Details */}
          {Object.keys(designation).map((key) => {
            if (key === 'placementProcess') {
              return (
                <div key={`${dIndex}-process`}>
                  {/* Round Heading and Add Round Button */}                 
                    
                         <div className='headings'>Placement Process                       
                          <button type="button" onClick={() => addProcess(dIndex)}>
                            Add Round
                          </button>                     
                 </div>

                  {/* Placement Process (Rounds) */}
                  {designation.placementProcess.map((process, pIndex) => (
                    <div key={pIndex}>
                      <div className="headings">
                        Round {pIndex + 1}
                        {/* Remove Round Button */}
                        <button
                          type="button"
                          className="remove-round-btn"
                          onClick={() => removeProcess(dIndex, pIndex)}
                        >
                          Remove Round
                        </button>
                      </div>
                      {Object.keys(process).map((pKey) => (
                        <div key={pKey}>
                          <label>{pKey}</label>
                          <input
                            type={pKey === 'description' ? 'textarea' : 'text'}
                            value={process[pKey]}
                            onChange={(e) =>
                              handleProcessChange(
                                dIndex,
                                pIndex,
                                pKey,
                                e.target.value
                              )
                            }
                          />
                        </div>
                      ))}
                    </div>
                  ))}
                </div>
              );
            }
            return (
              <div key={key}>
                <label>{key === 'requiredQualifications' ? 'Required Qualification' : key}</label>
                <input
                  type="text"
                  placeholder={
                    key === 'requiredQualifications'
                      ? 'Enter comma-separated values'
                      : ''
                  }
                  value={
                    key === 'requiredQualifications'
                      ? designation[key].join(', ')
                      : designation[key]
                  }
                  onChange={(e) =>
                    handleDesignationChange(dIndex, key, e.target.value)
                  }
                  required
                />
              </div>
            );
          })}
        </div>
      ))}

      {/* Add Designation Button */}
      <button  class="add-designation-btn" type="button" onClick={addDesignation}>
        Add Designation
      </button>

      {/* Submit Button */}
      <button type="submit">Submit</button>
    </form>
  );
};

export default CompanyDetailsForm;
