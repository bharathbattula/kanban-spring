package com.kanban.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;

@Controller
@RequestMapping(BASE_API)
public class HomeController {


	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/hello")
	public ResponseEntity<?> greeting() {
		return ResponseEntity.ok(Collections.singletonMap("success", "Controller Working"));
	}
}
