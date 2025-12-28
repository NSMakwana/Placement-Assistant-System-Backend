package Nency.project.Placement.Assistant.service;

import Nency.project.Placement.Assistant.model.Company;
import Nency.project.Placement.Assistant.model.Round;
import Nency.project.Placement.Assistant.repository.CompanyRepository;
import Nency.project.Placement.Assistant.repository.RoundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoundService {

    private final RoundRepository roundRepo;
    private final CompanyRepository companyRepo;

    public RoundService(RoundRepository roundRepo, CompanyRepository companyRepo) {
        this.roundRepo = roundRepo;
        this.companyRepo = companyRepo;
    }

    public List<Round> listRounds(String companyId, String designation, String batch) {
        return roundRepo.findByCompanyIdAndDesignationAndBatchOrderBySequence(companyId, designation, batch);
    }

    public Round createRound(Round round) {
        // validate company exists
        Optional<Company> comp = companyRepo.findById(round.getCompanyId());
        if (!comp.isPresent()) {
            throw new IllegalArgumentException("Invalid companyId");
        }

        // validate designation exists inside company
        boolean designationExists = comp.get().getDesignations()
                .stream()
                .anyMatch(d -> d.getDesignation().equalsIgnoreCase(round.getDesignation()));
        if (!designationExists) {
            throw new IllegalArgumentException("Invalid designation for this company");
        }

        // If setting this as final, unset previous final for this (company,designation,batch)
        if (round.isFinalRound()) {
            List<Round> existing = roundRepo.findByCompanyIdAndDesignationAndBatch(round.getCompanyId(), round.getDesignation(), round.getBatch());
            for (Round r : existing) {
                if (r.isFinalRound()) {
                    r.setFinalRound(false);
                    roundRepo.save(r);
                }
            }
        }

        // optional: compute sequence as +1 of max existing sequence
        List<Round> existing = roundRepo.findByCompanyIdAndDesignationAndBatch(round.getCompanyId(), round.getDesignation(), round.getBatch());
        int maxSeq = existing.stream().mapToInt(Round::getSequence).max().orElse(0);
        if (round.getSequence() == 0) round.setSequence(maxSeq + 1);

        return roundRepo.save(round);
    }


}
