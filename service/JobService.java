package com.example.portal.service;
// import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.example.portal.model.Job;
import com.example.portal.repository.JobRepository;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public boolean jobExists(String title, String description, String location, double salary) {
        return jobRepository.existsByTitleAndDescriptionAndLocationAndSalary(title, description, location, salary);
    }
    // public List<Job> getJobsByEmail(String email) {
    //     // return jobRepository.findByEmail(email);
    //     // return jobRepository.findAll();
    // }
}
