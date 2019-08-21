package com.kanban.api.controller;

import com.kanban.api.model.Project;
import com.kanban.api.model.UserSession;
import com.kanban.api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;

@RestController
@RequestMapping(BASE_API + "/project")
public class ProjectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping
	public ResponseEntity create(@RequestBody final Project project) {

		try {
			if (this.projectService.duplicateProjectName(project.getName())) {
				LOGGER.warn("Project already present with the same name, {}", project.getName());
				return ResponseEntity.badRequest()
						.body(Collections.singletonMap("error", "Project already exist with the same name"));
			}

			return ResponseEntity.ok(this.projectService.createProject(project));

		} catch (final Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Failed to create the project"));
		}
	}

	@Secured({"ROLE_ADMIN"})
	@GetMapping
	public ResponseEntity getProjects(@RequestBody final UserSession userSession) {
		try {

			return ResponseEntity.ok(this.projectService.getAllProjects(userSession));
		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Failed to get the project details"));
		}
	}
}
