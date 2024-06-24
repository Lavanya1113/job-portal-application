package com.example.portal.model;

import jakarta.persistence.Entity;
// import jakarta.persistence.FetchType;
// import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // private String email;

    @NotEmpty(message = "Title is required")
    private String title;

    @NotEmpty(message = "Description is required")
    private String description;

    @NotEmpty(message = "Skills are required")
    private String skills;

    @NotNull(message = "Salary is required")
    @Min(value = 0, message = "Salary must be a positive number")
    private Double salary;

    @NotEmpty(message = "Location is required")
    private String location;

    private Long employerId;
    private String employerEmail;

    // @ManyToOne(fetch = FetchType.LAZY, targetEntity = Employer.class)
    // @JoinColumn(name = "employer_id", insertable = false, updatable = false)
    // private Employer employer;

    // Getters and setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployerId() {
        return employerId;
    }
    
    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public String getEmployerEmail(){
        return employerEmail;
    }
    public void setEmployerEmail(String employerEmail){
        this.employerEmail = employerEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setSkills(String skills) {
        this.skills=skills;
    }
    public String getSkills() {
        return skills;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    // public Employer getEmployer() {
    //     return employer;
    // }

    // public void setEmployer(Employer employer) {
    //     this.employer = employer;
    // }
  
}
