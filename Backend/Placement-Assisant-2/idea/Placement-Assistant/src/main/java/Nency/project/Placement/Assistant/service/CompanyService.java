package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Company;
import Nency.project.Placement.Assistant.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    // Fetch all companies
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    // Search companies by name (or part of the name)
    public List<Company> searchCompaniesByName(String name) {
        return companyRepository.findByNameContainingIgnoreCase(name);
    }

    // Fetch companies by batch
    public List<Company> getCompaniesByBatch(String batch) {
        return companyRepository.findByBatch(batch);
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    // Fetch companies by program (or other fields, like designation)


    // Additional methods can be added for more complex queries, for example:
    // Fetch companies by location, designation, or other business-specific fields.
}
