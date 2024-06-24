package com.example.portal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.session.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.portal.model.Employer;
import com.example.portal.model.JobSeeker;
import com.example.portal.model.LoginForm;
import com.example.portal.repository.EmployerRepository;
import com.example.portal.repository.JobSeekerRepository;
// import org.springframework.web.servlet.view.RedirectView;

// import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobSeekerRepository jobseekerRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
    
    // @PostMapping("/login")
    // public String login(@Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         return "login"; // Return back to the signup form if there are validation errors
    //     }
    //     String email = loginForm.getEmail();
    //     String password = loginForm.getPassword();
    //     String role = loginForm.getRole();

    //     if (role.equals("employer")) {
    //         Employer employer = employerRepository.findByEmail(email);
    //         if (employer != null && employer.getPassword().equals(password)) {
    //             model.addAttribute("message", "Welcome  "+ employer.getName() +"\n Have a great day...!");
    //             return "employer";
    //         }
    //     } else if (role.equals("jobseeker")) {
    //         JobSeeker jobseeker = jobseekerRepository.findByEmail(email);
    //         if (jobseeker != null && jobseeker.getPassword().equals(password)) {
    //             // Authentication successful, redirect to jobseeker dashboard
    //             model.addAttribute("message", "Welcome  "+ jobseeker.getName() +"\n Have a great day...!");
    //             return "jobseeker";
    //         }
    //     }
    //     model.addAttribute("error", "Invalid user details");
    //     // Authentication failed, redirect back to login with error message
    //     return "login";
    // }
    
    @PostMapping("/login")
    public String login(HttpSession session, @Valid @ModelAttribute("loginForm") LoginForm loginForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login";
        }

        String email = loginForm.getEmail();
        String password = loginForm.getPassword();
        String role = loginForm.getRole();

        if (role.equals("employer")) {
            Employer employer = employerRepository.findByEmail(email);
            if (employer != null && employer.getPassword().equals(password)) {
                model.addAttribute("message", "Welcome  " + employer.getName() + "\n Have a great day...!");
                session.setAttribute("employeremail", employer.getEmail());
                // session.setAttribute("employerid", employer.getId());
                return "employer"; // Redirect to the employer dashboard
            }
        }else if (role.equals("jobseeker")) {
                    JobSeeker jobseeker = jobseekerRepository.findByEmail(email);
                    if (jobseeker != null && jobseeker.getPassword().equals(password)) {
                        // Authentication successful, redirect to jobseeker dashboard
                        model.addAttribute("message", "Welcome  "+ jobseeker.getName() +"\n Have a great day...!");
                        session.setAttribute("jobseekeremail", jobseeker.getEmail());
                        return "jobseeker";
                    }
            }
    
        model.addAttribute("error", "Invalid user details");
        return "login";
    }
}