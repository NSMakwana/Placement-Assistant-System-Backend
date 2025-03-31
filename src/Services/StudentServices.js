import axios from 'axios';

const API_URL = 'http://localhost:8080/api/students';

class StudentService {
    getStudents() {
        return axios.get(API_URL); // Fetch all students
    }

    getStudent(id) {
        return axios.get(`${API_URL}/${id}`); // Fetch student by ID
    }

    getBatchOfStudents(email) {
        return axios.get(`${API_URL}/batchByEmail/${email}`); // Fetch all students by batch
    }
    addStudent(student) {
        return axios.post(API_URL, student); // Add a new student
    }

    updateStudent(id, student) {
        return axios.put(`${API_URL}/${id}`, student); // Update student
    }

    deleteStudent(id) {
        return axios.delete(`${API_URL}/${id}`); // Delete student by ID
    }
}

export default new StudentService();
