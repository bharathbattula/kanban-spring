package com.kanban.api.controller;

import com.kanban.api.exception.BadRequestException;
import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.Task;
import com.kanban.api.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

@RestController
@RequestMapping(BASE_API + "project/{projectId}/list/{listId}/task")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public ResponseEntity getAllTaskFromProjectId(@PathVariable final Long projectId, @PathVariable Long listId) {
		try {

			return ResponseEntity.ok(this.taskService.getTasks(projectId, listId));

		} catch (ResourceNotFoundException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", e.getMessage()));

		} catch (final Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping
	@PutMapping
	public ResponseEntity createTask(@PathVariable final Long projectId, @PathVariable Long listId, @RequestBody Task task) {
		try {

			return ResponseEntity.ok(this.taskService.saveTask(projectId, listId, task));

		} catch (BadRequestException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap("error", e.getMessage()));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));

		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@DeleteMapping("/{taskId}")
	public ResponseEntity deleteTask(@PathVariable final Long projectId, @PathVariable Long listId, @PathVariable Long taskId) {

		try {

			this.taskService.deleteTask(projectId, listId, taskId);

			return ResponseEntity.ok(Collections.singletonMap("success", "Task deleted successfully"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));

		}
	}

}
