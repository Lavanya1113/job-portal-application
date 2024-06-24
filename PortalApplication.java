package com.example.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
// import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@SpringBootApplication
// @EnableSpringHttpSession
@EntityScan("com.example.portal.model")
public class PortalApplication {
	public static void main(String[] args) {
		SpringApplication.run(PortalApplication.class, args);
	}
}
 