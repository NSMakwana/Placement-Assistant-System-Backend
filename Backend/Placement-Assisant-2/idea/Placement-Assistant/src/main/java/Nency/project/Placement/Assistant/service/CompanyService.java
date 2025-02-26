package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Company;
import Nency.project.Placement.Assistant.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public List<Map<String, String>> getCompanyRounds(String cname) {
        Optional<Company> company = companyRepository.findByName(cname); // Find company by name

        if (company.isPresent()) {
            return company.get().getDesignations()
                    .stream()
                    .flatMap(designation -> designation.getPlacementProcess().stream())
                    .map(process -> Map.of("round", process.getRound())) // Extract only round names
                    .toList();
        }

        return Collections.emptyList(); // Return empty list if company not found
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    // Fetch companies by program (or other fields, like designation)


    // Additional methods can be added for more complex queries, for example:
    // Fetch companies by location, designation, or other business-specific fields.
}
