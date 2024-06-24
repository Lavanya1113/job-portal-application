package com.example.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.portal.model.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer,Long> {
    Employer findByEmail(String email);
    // Employer findById(Long id);
}
