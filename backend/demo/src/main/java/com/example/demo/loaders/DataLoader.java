package com.example.demo.loaders;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Map;
import com.example.demo.repositories.CandidateRepository;
import com.example.demo.repositories.IssueRepository;
import com.example.demo.entities.Candidate;
import com.example.demo.entities.Issue;
import org.springframework.beans.factory.annotation.Autowired;

// Annotation to tell Spring this is a component to run at startup
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private IssueRepository issueRepository;

    // Constructor injection of the repositories
    public DataLoader(CandidateRepository candidateRepository, IssueRepository issueRepository) {
        this.candidateRepository = candidateRepository;
        this.issueRepository = issueRepository;
    }

    // This method runs automatically at application startup
    @Override
    public void run(String... args) throws Exception {
        // Clear any existing data to prevent duplicates (optional)
        // candidateRepository.deleteAll();
        // issueRepository.deleteAll();
        // // Seed the 'candidates' collection with some initial data
        // candidateRepository.createCandidate(new Candidate("1", "alice Smith", "D", Map.of("abortion", 85.0, "second amendment", 0.0)));

        // candidateRepository.createCandidate(new Candidate("2", "bob Johnson", "R", Map.of("second amendment", 90.0, "abortion", 40.0)));

        // // Seed the 'issues' collection with some initial data
        // issueRepository.createIssue(new Issue("1", "Abortion"));
        // issueRepository.createIssue(new Issue("2", "Second Amendment"));

        System.out.println("Database seeded with initial data.");
    }
}
