package com.kanban.api.controller;

import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.BoardList;
import com.kanban.api.service.BoardListService;
import com.kanban.api.service.ProjectService;
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

import javax.xml.ws.Response;
import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;

@RestController
@RequestMapping(BASE_API+"/project/{projectId}/list")
public class BoardListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardListController.class);

	@Autowired
	BoardListService listService;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public ResponseEntity getBoardList(@PathVariable() Long projectId) {
		try {

			return ResponseEntity.ok(listService.getListFromProjectId(projectId));
		} catch (ResourceNotFoundException e) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", e.getMessage()));

		} catch (Exception e){

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@PostMapping
	@PutMapping
	public ResponseEntity createBoardList(@PathVariable Long projectId,
										  @RequestBody BoardList boardList) {
		try {

			return ResponseEntity.ok(listService.saveBoardList(projectId, boardList));

		} catch (ResourceNotFoundException e){

			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(Collections.singletonMap("error", e.getMessage()));
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));
		}
	}

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@DeleteMapping("/{listId}")
	public ResponseEntity deleteBoardList(@PathVariable Long projectId, @PathVariable Long listId) {
		try {

			this.listService.deleteBoardList(projectId, listId);

			return ResponseEntity.ok(Collections.singletonMap("success", "Successfully Deleted"));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(Collections.singletonMap("error", "Something went wrong"));
		}
	}

}
