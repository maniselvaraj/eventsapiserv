package com.yumu.eventsapiserv.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HealthCheck {
	
	@GetMapping("/health")
	String  checkHealth() {
		return "healthy!";
	}
	
}
