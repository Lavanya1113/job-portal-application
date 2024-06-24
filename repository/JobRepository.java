package com.example.portal.repository;

// import com.example.portal.model.Employer;
// import com.example.portal.model.Employer;
import com.example.portal.model.Job;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    boolean existsByTitleAndDescriptionAndLocationAndSalary(String title, String description, String location,
            double salary);
    List<Job> findByTitleContainingOrLocationContaining(String title, String location);
    List<Job> findByEmployerId(Long employerId);

    // List<Job> findByEmployer(Employer employer);

    // List<Job> findByEmail(String email);

    // You can add custom query methods here if needed
}