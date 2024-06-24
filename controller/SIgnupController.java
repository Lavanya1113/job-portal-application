package com.example.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.example.portal.model.Employer;
import com.example.portal.model.JobSeeker;
import com.example.portal.model.SignupForm;
import com.example.portal.repository.EmployerRepository;
import com.example.portal.repository.JobSeekerRepository;
// import ch.qos.logback.core.model.Model;

import jakarta.validation.Valid;

@Controller
public class SIgnupController {
    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;
    
    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("signupForm", new SignupForm());
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("signupForm") SignupForm signupForm,BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup"; // Return back to the signup form if there are validation errors
        }
        String email = signupForm.getEmail();
        String role = signupForm.getRole();
    
        // Check if the email already exists for employers
        if ("recruiter".equalsIgnoreCase(role) && employerRepository.findByEmail(email) != null) {
            model.addAttribute("emailError", "Email is already in use by another employer.");
            return "signup";
        }
        
        // Check if the email already exists for job seekers
        if ("jobseeker".equalsIgnoreCase(role) && jobSeekerRepository.findByEmail(email) != null) {
            model.addAttribute("emailError", "Email is already in use by another job seeker.");
            return "signup";
        }
        
        if ("recruiter".equalsIgnoreCase(signupForm.getRole())) {
            Employer employer = new Employer();
            employer.setName(signupForm.getName());
            employer.setEmail(signupForm.getEmail());
            employer.setPassword(signupForm.getPassword());
            employerRepository.save(employer); 
            model.addAttribute("message", "Employer registered successfully!");
        } else if ("jobseeker".equalsIgnoreCase(signupForm.getRole())) {
            JobSeeker jobSeeker = new JobSeeker();
            jobSeeker.setName(signupForm.getName());
            jobSeeker.setEmail(signupForm.getEmail());
            jobSeeker.setPassword(signupForm.getPassword());
            jobSeekerRepository.save(jobSeeker);
            model.addAttribute("message", "Jobseeker registered successfully!");
        } else {
            model.addAttribute("message", "Invalid role specified!");
        }
        return "redirect:/login";
    }  
    // Add this method to handle the /result endpoint
    @GetMapping("/result")
    public String result(Model model) {
        return "result";
    }
}
