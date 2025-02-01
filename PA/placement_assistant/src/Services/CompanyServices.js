import axios from 'axios';

const API_URL = 'https://placement-assistant-system.onrender.com/api/companies';


class CompanyServiceClass {
    getCompany() {
        return axios.get(API_URL); // Fetch allCompanys
    }

   

    deleteCompany(id) {
        return axios.delete(`${API_URL}/${id}`); // DeleteCompany by ID
    }
}

export const CompanyService = new CompanyServiceClass();
