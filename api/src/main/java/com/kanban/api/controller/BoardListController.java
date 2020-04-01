package com.kanban.api.controller;

import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.BoardList;
import com.kanban.api.service.BoardListService;
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
import static com.kanban.api.common.Constants.ERROR;
import static com.kanban.api.common.Constants.INTERNAL_SERVER_ERROR_MSG;
import static com.kanban.api.common.Constants.SUCCESS;

@RestController
@RequestMapping(BASE_API+"/project/{projectId}/list")
public class BoardListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardListController.class);

	@Autowired
	private BoardListService listService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public ResponseEntity getBoardList(@PathVariable() final Long projectId) {
		try {

			return ResponseEntity.ok(this.listService.getListFromProjectId(projectId));
		} catch (final ResourceNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap(ERROR, e.getMessage()));

		} catch (final Exception e) {
			LOGGER.error("error extracting the list of project " + projectId, e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, INTERNAL_SERVER_ERROR_MSG));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping
	@PutMapping
	public ResponseEntity createBoardList(@PathVariable final Long projectId,
										  @RequestBody final BoardList boardList) {
		try {

			return ResponseEntity.ok(this.listService.saveBoardList(projectId, boardList));

		} catch (final ResourceNotFoundException e) {


			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap(ERROR, e.getMessage()));
		} catch (final Exception e) {
			LOGGER.error("error adding the list into project " + projectId, e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, INTERNAL_SERVER_ERROR_MSG));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@DeleteMapping("/{listId}")
	public ResponseEntity deleteBoardList(@PathVariable final Long projectId, @PathVariable final Long listId) {
		try {

			this.listService.deleteBoardList(projectId, listId);

			return ResponseEntity.ok(Collections.singletonMap(SUCCESS, "Successfully Deleted"));

		} catch (final Exception e) {
			LOGGER.error("error deleting the list from a project " + projectId, e);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap(ERROR, INTERNAL_SERVER_ERROR_MSG));
		}
	}

}
