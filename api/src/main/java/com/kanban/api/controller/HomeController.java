package com.kanban.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class HomeController {

	@GetMapping("/hello")
	public ResponseEntity<?> greeting() {
		return ResponseEntity.ok(Collections.singletonMap("success", "Controller Working"));
	}
}
