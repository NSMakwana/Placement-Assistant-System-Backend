import React, { useState } from "react";
import { Form, Button, Row, Col } from "react-bootstrap";
import axios from "axios";
import "./StudentDetailForm.css";
const StudentDetailForm = () => {
    const [studentDetails, setStudentDetails] = useState({
        enrollmentNumber: "",
        fullName: "",
        course: "",
        email: "",
        phone: "",
        dob: "",
        gender: "",
        nationality: "",
        timestamp: "",
        address: {
            buildingName: "",
            city: "",
            pincode: "",
            state: "",
        },
        ssc: {
            marks: "",
            totalMarks: "",
            percentage: "",
            board: "",
        },
        hsc: {
            marks: "",
            totalMarks: "",
            percentage: "",
            stream: "",
            board: "",
            year: "",
        },
        bachelor: {
            marks: "",
            percentage: "",
            year: "",
        },
        masters: {
            totalMarks: "",
            percentage: "",
            degree: "",
            university: "",
            year: "",
        },
        entry: "",
        drops: "",
        remarks: "",
    });

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name.includes(".")) {
            const [section, field] = name.split(".");
            setStudentDetails((prevDetails) => ({
                ...prevDetails,
                [section]: {
                    ...prevDetails[section],
                    [field]: value,
                },
            }));

            // Trigger reverse lookup for pincode
            if (section === "address" && field === "city" && value.length > 2) {
                fetchPincodeByCity(value);
            }
        } else {
            setStudentDetails((prevDetails) => ({ ...prevDetails, [name]: value }));
        }
    };

    const fetchPincodeByCity = async (city) => {
        try {
            // Call the Postal Pincode API for city
            const response = await axios.get(`https://api.postalpincode.in/postoffice/${city}`);
            const data = response.data;

            if (data[0].Status === "Success") {
                // Extract the first pincode and set it
                const postOffice = data[0].PostOffice[0];
                setStudentDetails((prevDetails) => ({
                    ...prevDetails,
                    address: {
                        ...prevDetails.address,
                        pincode: postOffice.Pincode, // Set the first pincode found
                        state: postOffice.State,
                    },
                }));
            } else {
                console.error("City not found or no data available.");
            }
        } catch (error) {
            console.error("Error fetching pincode:", error);
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Student Details Submitted: ", studentDetails);
    };

    return (
        <div  className="student-form-container">
        <Form onSubmit={handleSubmit} className="p-4 border rounded bg-light">
            <h3><center>Student Details Form</center></h3>
            <h4 className="mt-4">Basic Details</h4>

            {/* Basic Information */}
            <Row>
                <Col md={6}>
                    <Form.Group controlId="enrollmentNumber" className="mb-3">
                        <Form.Label>Enrollment Number</Form.Label>
                        <Form.Control
                            type="number"
                            name="enrollmentNumber"
                            value={studentDetails.enrollmentNumber}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="fullName" className="mb-3">
                        <Form.Label>Full Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="fullName"
                            value={studentDetails.fullName}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="course" className="mb-3">
                        <Form.Label>Course</Form.Label>
                        <Form.Control
                            type="text"
                            name="course"
                            value={studentDetails.course}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="email" className="mb-3">
                        <Form.Label>Email</Form.Label>
                        <Form.Control
                            type="email"
                            name="email"
                            value={studentDetails.email}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="phone" className="mb-3">
                        <Form.Label>Phone Number</Form.Label>
                        <Form.Control
                            type="number"
                            name="phone"
                            value={studentDetails.phone}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="dob" className="mb-3">
                        <Form.Label>Date of Birth</Form.Label>
                        <Form.Control
                            type="date"
                            name="dob"
                            value={studentDetails.dob}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="gender" className="mb-3">
                        <Form.Label>Gender</Form.Label>
                        <Form.Select
                            name="gender"
                            value={studentDetails.gender}
                            onChange={handleChange}
                        >
                            <option value="">Select Gender</option>
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                            <option value="Other">Other</option>
                        </Form.Select>
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="nationality" className="mb-3">
                        <Form.Label>Nationality</Form.Label>
                        <Form.Control
                            type="text"
                            name="nationality"
                            value={studentDetails.nationality}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="timestamp" className="mb-3">
                        <Form.Label>Timestamp</Form.Label>
                        <Form.Control
                            type="datetime-local"
                            name="timestamp"
                            value={studentDetails.timestamp}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group controlId="batch" className="mb-3">
                        <Form.Label>Batch</Form.Label>
                        <Form.Control
                            type="text"
                            name="batch"
                            value={studentDetails.batch}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            {/* Address */}
            <h4 className="mt-4">Address</h4>
            <Row>
                <Col md={6}>
                    <Form.Group controlId="address.buildingName" className="mb-3">
                        <Form.Label>Building Name</Form.Label>
                        <Form.Control
                            type="text"
                            name="address.buildingName"
                            value={studentDetails.address.buildingName}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="address.city" className="mb-3">
                        <Form.Label>City</Form.Label>
                        <Form.Control
                            type="text"
                            name="address.city"
                            value={studentDetails.address.city}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="address.pincode" className="mb-3">
                        <Form.Label>Pincode</Form.Label>
                        <Form.Control
                            type="text"
                            name="address.pincode"
                            value={studentDetails.address.pincode}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="address.state" className="mb-4">
                        <Form.Label>State</Form.Label>
                        <Form.Control
                            type="text"
                            name="address.state"
                            value={studentDetails.address.state}
                            readOnly
                        />
                    </Form.Group>
                </Col>
            </Row>

            {/* SSC Information */}
            <h4 className="mt-4">SSC Information</h4>
            <Row>
                <Col md={6}>
                    <Form.Group controlId="ssc.marks" className="mb-3">
                        <Form.Label>SSC Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="ssc.marks"
                            value={studentDetails.ssc.marks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="ssc.totalMarks" className="mb-3">
                        <Form.Label>SSC Total Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="ssc.totalMarks"
                            value={studentDetails.ssc.totalMarks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="ssc.percentage" className="mb-3">
                        <Form.Label>SSC Percentage</Form.Label>
                        <Form.Control
                            type="number"
                            step="0.01"
                            name="ssc.percentage"
                            value={studentDetails.ssc.percentage}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="ssc.board" className="mb-3">
                        <Form.Label>SSC Board</Form.Label>
                        <Form.Control
                            type="text"
                            name="ssc.board"
                            value={studentDetails.ssc.board}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            {/* HSC Information */}
            <h4 className="mt-4">HSC Information</h4>
            <Row>
                <Col md={6}>
                    <Form.Group controlId="hsc.marks" className="mb-3">
                        <Form.Label>HSC Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="hsc.marks"
                            value={studentDetails.hsc.marks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="hsc.totalMarks" className="mb-3">
                        <Form.Label>HSC Total Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="hsc.totalMarks"
                            value={studentDetails.hsc.totalMarks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="hsc.percentage" className="mb-3">
                        <Form.Label>HSC Percentage</Form.Label>
                        <Form.Control
                            type="number"
                            step="0.01"
                            name="hsc.percentage"
                            value={studentDetails.hsc.percentage}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="hsc.stream" className="mb-3">
                        <Form.Label>HSC Stream</Form.Label>
                        <Form.Control
                            type="text"
                            name="hsc.stream"
                            value={studentDetails.hsc.stream}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="hsc.board" className="mb-3">
                        <Form.Label>HSC Board</Form.Label>
                        <Form.Control
                            type="text"
                            name="hsc.board"
                            value={studentDetails.hsc.board}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="hsc.year" className="mb-3">
                        <Form.Label>HSC Year</Form.Label>
                        <Form.Control
                            type="number"
                            name="hsc.year"
                            value={studentDetails.hsc.year}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <h4 className="mt-4">Bachelor Information</h4>
            <Row>
                <Col md={6}>
                    <Form.Group controlId="bachelor.marks" className="mb-3">
                        <Form.Label>Bachelor Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="bachelor.marks"
                            value={studentDetails.bachelor.marks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="bachelor.totalMarks" className="mb-3">
                        <Form.Label>Bachelor Total Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="bachelor.totalMarks"
                            value={studentDetails.bachelor.totalMarks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="bachelor.percentage" className="mb-3">
                        <Form.Label>Bachelor Percentage</Form.Label>
                        <Form.Control
                            type="number"
                            step="0.01"
                            name="bachelor.percentage"
                            value={studentDetails.bachelor.percentage}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="bachelor.degree" className="mb-3">
                        <Form.Label>Bachelor Degree</Form.Label>
                        <Form.Control
                            type="text"
                            name="bachelor.degree"
                            value={studentDetails.bachelor.degree}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="bachelor.university" className="mb-3">
                        <Form.Label>Bachelor University</Form.Label>
                        <Form.Control
                            type="text"
                            name="bachelor.university"
                            value={studentDetails.bachelor.university}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="bachelor.year" className="mb-3">
                        <Form.Label>Bachelor Year</Form.Label>
                        <Form.Control
                            type="number"
                            name="bachelor.year"
                            value={studentDetails.bachelor.year}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <h4 className="mt-4">Masters Information</h4>
            <Row>
                <Col md={6}>
                    <Form.Group controlId="masters.marks" className="mb-3">
                        <Form.Label>Masters Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="masters.marks"
                            value={studentDetails.masters.marks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="masters.totalMarks" className="mb-3">
                        <Form.Label>Masters Total Marks</Form.Label>
                        <Form.Control
                            type="number"
                            name="masters.totalMarks"
                            value={studentDetails.masters.totalMarks}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="masters.percentage" className="mb-3">
                        <Form.Label>Masters Percentage</Form.Label>
                        <Form.Control
                            type="number"
                            step="0.01"
                            name="masters.percentage"
                            value={studentDetails.masters.percentage}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="masters.degree" className="mb-3">
                        <Form.Label>Masters Degree</Form.Label>
                        <Form.Control
                            type="text"
                            name="masters.degree"
                            value={studentDetails.masters.degree}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <Row>
                <Col md={6}>
                    <Form.Group controlId="masters.university" className="mb-3">
                        <Form.Label>Masters University</Form.Label>
                        <Form.Control
                            type="text"
                            name="masters.university"
                            value={studentDetails.masters.university}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
                <Col md={6}>
                    <Form.Group controlId="masters.year" className="mb-3">
                        <Form.Label>Masters Year</Form.Label>
                        <Form.Control
                            type="number"
                            name="masters.year"
                            value={studentDetails.masters.year}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
            </Row>

            <h4 className="mt-4">Other Details</h4>
            <row>
            <Col md={6}>    
            <Form.Group controlId="Entry" className="mb-3">
        <Form.Label>Lateral Entry</Form.Label>
        <Form.Control
            type="textarea"
           // rows={3}
            name="Entry"
            value={studentDetails.Entry}
            onChange={handleChange}
        />
        </Form.Group>
        </Col>

        <Col md={6}>
                    <Form.Group controlId="drops" className="mb-3">
                        <Form.Label>Drops</Form.Label>
                        <Form.Control
                            type="number"
                            name="drops"
                            value={studentDetails.drops}
                            onChange={handleChange}
                        />
                    </Form.Group>
                </Col>
        

        </row>
             {/* Remarks */}
        <Form.Group controlId="remarks" className="mb-3">
        <Form.Label>Remarks</Form.Label>
        <Form.Control
            as="textarea"
            rows={3}
            name="remarks"
            value={studentDetails.remarks}
            onChange={handleChange}
        />
        </Form.Group>

            <Button type="submit" className="mt-3">
                Submit
            </Button>
        </Form>
       </div>
    );
};

export default StudentDetailForm;
