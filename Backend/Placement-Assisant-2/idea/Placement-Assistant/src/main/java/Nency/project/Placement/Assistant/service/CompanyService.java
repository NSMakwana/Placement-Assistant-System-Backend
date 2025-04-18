package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Company;
import Nency.project.Placement.Assistant.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

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


        return company.map(value -> value.getDesignations()
                .stream()
                .flatMap(designation -> designation.getPlacementProcess().stream())
                .map(process -> Map.of("round", process.getRound())) // Extract only round names
                .toList()).orElse(Collections.emptyList());
    }
    
     public List<String> getCompanyDesignations(String companyName) {
        Optional<Company> company = companyRepository.findByName(companyName);

        if (company.isEmpty()) {
            return Collections.emptyList(); // Return empty list if company not found
        }

        return company.get().getDesignations()
                .stream()
                .map(designation -> designation.getDesignation()) // Adjust based on your model
                .collect(Collectors.toList());
    }

    public List<Map<String, String>> getRoundsByCompanyAndDesignation(String companyName, String designation) {
        Optional<Company> company = companyRepository.findByName(companyName);
        if (company.isEmpty()) return Collections.emptyList();

        return company.get().getDesignations().stream()
                .filter(d -> d.getDesignation().equalsIgnoreCase(designation))
                .flatMap(d -> d.getPlacementProcess().stream())
                .map(p -> Map.of("round", p.getRound()))
                .toList();
    }
    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }


}
