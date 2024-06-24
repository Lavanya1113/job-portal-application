package com.example.portal.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
// import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

// import java.time.LocalDateTime;

import jakarta.persistence.Entity;

@Entity
@Table(name = "jobapplications")
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    // @ManyToOne
    // @JoinColumn(name = "job_id", nullable = false)
    // private Job job;
    // private LocalDateTime applicationDate;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "gender is required")
    private String gender;
    @NotEmpty(message = "Name is required")
    private String name;

    // private String resumeFilePath;
    @NotEmpty(message = "resume is required")
    private String resumeFilePath; // Field to store the path to the uploaded resume



    
    // Default constructor
    public JobApplication() {}

    // Getters and Setters

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getGender(){
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getResumeFilePath() {
        return resumeFilePath;
    }

    public void setResumeFilePath(String resumeFilePath) {
        this.resumeFilePath = resumeFilePath;
    }
    
}
