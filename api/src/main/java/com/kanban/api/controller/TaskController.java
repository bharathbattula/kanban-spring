package com.kanban.api.controller;

import com.kanban.api.common.Utility;
import com.kanban.api.config.authentication.UserPrincipal;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;
import static com.kanban.api.common.Constants.ERROR;
import static com.kanban.api.common.Constants.SUCCESS;

@RestController
@RequestMapping(BASE_API + "/project/{projectId}/list/{listId}/task")
public class TaskController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

	@Autowired
	private TaskService taskService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public ResponseEntity getAllTaskFromProjectId(@PathVariable final Long projectId, @PathVariable final Long listId) {
		try {

			return ResponseEntity.ok(this.taskService.getTasks(projectId, listId));

		} catch (final ResourceNotFoundException e) {

			LOGGER.error("error extracting tasks", e);

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap(ERROR, e.getMessage()));

		} catch (final Exception e) {
			LOGGER.error("error extracting tasks", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, "Something went wrong"));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
	public ResponseEntity createTask(@PathVariable final Long projectId,
									 @PathVariable final Long listId,
									 @RequestBody final Task task,
									 final Authentication authentication) {
		try {

			final UserPrincipal userPrincipal = Utility.getUserPrincipalFromAuthentication(authentication);

			return ResponseEntity.ok(this.taskService.saveTask(projectId, listId, task, userPrincipal));

		} catch (final BadRequestException e) {
			LOGGER.error("error updating task", e);

			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap(ERROR, e.getMessage()));

		} catch (final Exception e) {
			LOGGER.error("error updating task", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, "Something went wrong"));

		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@DeleteMapping("/{taskId}")
	public ResponseEntity deleteTask(@PathVariable final Long projectId, @PathVariable final Long listId, @PathVariable final Long taskId) {

		try {

			this.taskService.deleteTask(projectId, listId, taskId);

			return ResponseEntity.ok(Collections.singletonMap(SUCCESS, "Task deleted successfully"));

		} catch (final Exception e) {
			LOGGER.error("error deleting task", e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, "Something went wrong"));

		}
	}

}
