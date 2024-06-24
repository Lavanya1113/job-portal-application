package com.example.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.portal.model.JobSeeker;


@Repository
public interface JobSeekerRepository extends JpaRepository<JobSeeker, Long> {
    JobSeeker findByEmail(String email);
}