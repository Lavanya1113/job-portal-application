package com.example.portal.controller;

import com.example.portal.model.Employer;
import com.example.portal.model.Job;
import com.example.portal.model.JobApplication;
import com.example.portal.model.JobSeeker;
import com.example.portal.repository.EmployerRepository;
import com.example.portal.repository.JobApplicationRepository;
import com.example.portal.repository.JobRepository;
import com.example.portal.repository.JobSeekerRepository;
import com.example.portal.service.JobService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
// import jakarta.persistence.criteria.Path;
import jakarta.validation.Valid;
import java.util.Optional;
// import com.example.portal.service.JobService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.io.File;
// import java.io.IOException;
// import java.nio.file.Files;
import java.nio.file.Paths;
// import java.security.Principal;
import java.nio.file.Path;
// import java.io.IOException;
// import java.nio.file.Paths;
import java.util.List;

// import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class JobController {

    @Autowired
    private JobService jobService;
    @Autowired
    private JobRepository jobRepository;
   @Autowired
   private EmployerRepository employerRepository;
    @Autowired
    private JobSeekerRepository jobseekerRepository;
   
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    private static String UPLOAD_DIR = "uploads/";
    @PostConstruct
    public void init() {
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    // listing all jobs 
    @GetMapping("/listAllJobs")
    public String getAllJobs(Model model) {
        List<Job> jobs = jobRepository.findAll();
        model.addAttribute("jobs", jobs);
        // Logic to fetch all jobs from the database and return a view
        return "allemployerjobs";
    }
    @GetMapping("/employerProfile")
    public String showEmployerProfile(HttpSession session, Model model) {
        String employeremail = (String) session.getAttribute("employeremail");
        if (employeremail == null) {
            return "redirect:/login"; // Redirect to login if the session has expired
        }
        Employer employer = employerRepository.findByEmail(employeremail);
        model.addAttribute("employer", employer);
        return "employerProfile";
    }
    
    @GetMapping("/jobseekerProfile")
    public String showJobSeekerProfile(HttpSession session, Model model) {
        String jobseekeremail = (String) session.getAttribute("jobseekeremail");
        // if (jobseekeremail == null) {
        //     return "redirect:/login"; // Redirect to login if the session has expired
        // }
        JobSeeker jobseeker = jobseekerRepository.findByEmail(jobseekeremail);
        model.addAttribute("jobseeker", jobseeker);
        return "jobseekerProfile";
    }

    @GetMapping("/listJobs")
    public String listJobsForEmployer(HttpSession session, Model model) {
        String employerEmail = (String) session.getAttribute("employeremail"); // Ensure the attribute name is correct
        if (employerEmail == null) {
            return "redirect:/login"; // Redirect to login if the session has expired
        }
        Employer employer = employerRepository.findByEmail(employerEmail);
        if (employer == null) {
            return "redirect:/login"; // Handle case where employer is not found
        }
        // List<Job> jobs = jobRepository.findAll();
        List<Job> jobs = jobRepository.findByEmployerId(employer.getId());
        model.addAttribute("jobs", jobs);
        return "jobs";// employer jobs
    }
    
    // @GetMapping("/addJob")
    // public String showAddJobForm(HttpSession session, Model model) {
    //     Job job = new Job();
    //     model.addAttribute("job", job);
   //     return "add-job";
    // }
    @GetMapping("/addJob")
    public String showAddJobForm(HttpSession session, Model model) {
        Job job = new Job();
        String employerEmail = (String) session.getAttribute("employeremail"); // Ensure the attribute name is correct
        if (employerEmail == null) {
            return "redirect:/employerdashboard"; // Redirect to login if the session has expired
        }
        Employer employer = employerRepository.findByEmail(employerEmail);
        if (employer == null) {
            return "redirect:/employerdashboard"; // Handle case where employer is not found
        }
        job.setEmployerEmail(employerEmail);
        job.setEmployerId(employer.getId()); // Set the employerId
        model.addAttribute("job", job);
        model.addAttribute("employer", employer);
            return "add-job";
        }



    @PostMapping("/addJob")
    public String addJob(@ModelAttribute("job") @Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-job";
        }

        if (jobService.jobExists(job.getTitle(), job.getDescription(), job.getLocation(), job.getSalary())) {
            model.addAttribute("message", "Job already exists with the same details.");
            return "add-job";
        }

        // job.setEmployer(employer);
        jobRepository.save(job);
        model.addAttribute("message", "Job added successfully!");
        return "add-job";
    }

    @GetMapping("/employerdashboard")
    public String employerboard(){
        return "employer";
    }

    @GetMapping("/jobseekerdashboard")
    public String jobseekerboard(){
        return "jobseeker";
    }

    @GetMapping("/editJob/{id}")
    public String editJobForm(@PathVariable("id") long id, Model model) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            model.addAttribute("job", job.get());
            return "edit-job";
        } else {
            // Handle the case where the job does not exist
            return "redirect:/listJobs";
        }
    }

    @PostMapping("/editJob/{id}")
    public String editJob(@PathVariable("id") long id, @ModelAttribute("job") @Valid Job job, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "edit-job";
        }
        job.setId(id);
        jobRepository.save(job);
        model.addAttribute("message", "Job updated successfully!");
        return "redirect:/listJobs";
    }

    @PostMapping("/deleteJob/{id}")
    public String deleteJob(@PathVariable("id") long id, Model model) {
        jobRepository.deleteById(id);
        model.addAttribute("message", "Job deleted successfully!");
        return "redirect:/listJobs";
    }

    @GetMapping("/searchJobs")
    public String searchJobs(@RequestParam("query") String query, Model model) {
        List<Job> jobs = jobRepository.findByTitleContainingOrLocationContaining(query, query);
        model.addAttribute("jobs", jobs);
        return "search-results";
    }
   
    @GetMapping("/listJobsForJobseeker")
    public String listJobsForJobseeker(Model model) {
        List<Job> jobs = jobRepository.findAll();
        model.addAttribute("jobs", jobs);
        return "jobs-list";
    }

    @GetMapping("/applyJob/{id}")
    public String showApplicationForm(@PathVariable("id") long id, Model model) {
        JobApplication jobApplication = new JobApplication();
        jobApplication.setJobId(id);
        model.addAttribute("jobApplication", jobApplication);
        return "job-application";
    }

    @PostMapping("/applyJob")
    public String applyForJob(@ModelAttribute("jobApplication") @Valid JobApplication jobApplication,BindingResult result,@RequestParam("resume") MultipartFile resume, Model model,RedirectAttributes redirectAttributes) {
        
        // if (result.hasErrors()) {
        //     return "job-application";
        // }
        
         // Save the uploaded resume file
         String resumeFileName = resume.getOriginalFilename();
         Path resumePath = Paths.get(UPLOAD_DIR, resumeFileName);
        //  try {
        //      Files.createDirectories(resumePath.getParent()); // Create directories if not exist
        //      resume.transferTo(resumePath.toFile());
        //  } catch (IOException e) {
        //      model.addAttribute("message", "Failed to upload resume.");
        //      return "job-application";
        //  }
        // Set the resume path in the job application
        jobApplication.setResumeFilePath(resumePath.toString());

        // Save job application details to the database
        jobApplicationRepository.save(jobApplication);
        // jobApplicationRepository.save(jobApplication);
        model.addAttribute("message", "We will contact you through email.");
        return "jobs-list";
    }
}

