import axios from 'axios';

const API_URL = 'https://placement-assistant-system.onrender.com/api/students';

class StudentService {
    getStudents() {
        return axios.get(API_URL); // Fetch all students
    }

    getStudent(id) {
        return axios.get(`${API_URL}/${id}`); // Fetch student by ID
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
