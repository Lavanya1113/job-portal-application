package com.example.portal.model;

// import java.util.List;

// import org.hibernate.mapping.List;
// import java.util.List;
// import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.OneToMany;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;



@Entity
@Table(name = "employer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    // private String company;
    

    // @OneToMany(mappedBy = "employer")
    // private List<Job> jobs;

    // @OneToMany(targetEntity =  Job.class, cascade = CascadeType.ALL)
    // @JoinColumn(name="employer_job",referencedColumnName = "email")
    // private List<Job> jobs;

    public Employer(){

    }

    // Getters and setters
    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
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
    public String getPassword(){
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    // public String getCompany(){
    //     return company;
    // }
    // public void setCompany(String company) {
    //     this.company = company;
    // }
    // public List<Job> getJobs() {
    //     return jobs;
    // }

    // public void setJobs(List<Job> jobs) {
    //     this.jobs = jobs;
    // }
}
