package com.kanban.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class HomeController {


	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/hello")
	public ResponseEntity<?> greeting() {
		return ResponseEntity.ok(Collections.singletonMap("success", "Controller Working"));
	}
}
