package com.kanban.api.controller;

import com.kanban.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;
import static com.kanban.api.common.Constants.ERROR;
import static com.kanban.api.common.Constants.INTERNAL_SERVER_ERROR_MSG;

@RestController
@RequestMapping(BASE_API + "/user")
public class UserController {

	public static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@Secured({"ROLE_ADMIN"})
	@GetMapping("/search")
	public ResponseEntity filterUser(@RequestParam(value = "query", required = true) final String query) {

		try {

			return ResponseEntity.ok(this.userService.loadMatchingUsers(query));

		} catch(final Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, INTERNAL_SERVER_ERROR_MSG));
		}
	}

}
