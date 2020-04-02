package com.kanban.api.controller;

import com.kanban.api.common.Utility;
import com.kanban.api.config.authentication.UserPrincipal;
import com.kanban.api.model.Project;
import com.kanban.api.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;
import static com.kanban.api.common.Constants.ERROR;

@RestController
@RequestMapping(BASE_API + "/project")
public class ProjectController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);

	@Autowired
	ProjectService projectService;

	//@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping
	public ResponseEntity create(@RequestBody final Project project, final Authentication authentication) {

		try {
			if (this.projectService.duplicateProjectName(project.getName())) {
				LOGGER.warn("Project already present with the same name, {}", project.getName());
				return ResponseEntity.badRequest()
						.body(Collections.singletonMap(ERROR, "Project already exist with the same name"));
			}

			final UserPrincipal userPrincipal = Utility.getUserPrincipalFromAuthentication(authentication);

			return ResponseEntity.ok(this.projectService.createProject(project, userPrincipal));

		} catch (final Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, e.getMessage()));
		}
	}

//	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public ResponseEntity getProjects(final Authentication authentication) {
		try {

			final Object details = authentication.getPrincipal();

			if (details instanceof UserPrincipal)
				return ResponseEntity.ok(this.projectService.getAllProjects(((UserPrincipal) details).getId()));

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap(ERROR, "Something went wrong"));
		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, "Failed to get the project details"));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PutMapping
	public ResponseEntity updateProject(@RequestBody final Project project, final Authentication authentication) {

		try {

			return ResponseEntity
					.ok(this.projectService.updateProject(project, (UserPrincipal) authentication.getPrincipal()));

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, e.getMessage()));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@DeleteMapping({"/{projectId}"})
	public ResponseEntity deleteProject(@PathVariable("projectId") final Long projectId) {
		try {

			this.projectService.deleteProject(projectId);

			return ResponseEntity.ok(Collections.singletonMap("success", "deleted"));

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, e.getMessage()));
		}
	}

}
