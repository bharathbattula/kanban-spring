package com.kanban.api.controller;

import com.kanban.api.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.kanban.api.common.Constants.BASE_API;

@RestController
@RequestMapping(BASE_API + "/task")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping("/project/{projectId}")
	public ResponseEntity getAllTaskFromProjectId(@PathVariable("projectId") final Long projectId) {
		this.taskService.getTaskFromProjectId(projectId);
	}
}
