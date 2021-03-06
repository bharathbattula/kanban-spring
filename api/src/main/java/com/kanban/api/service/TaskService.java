package com.kanban.api.service;

import com.kanban.api.config.authentication.UserPrincipal;
import com.kanban.api.exception.BadRequestException;
import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.BoardList;
import com.kanban.api.model.Task;
import com.kanban.api.model.User;
import com.kanban.api.repository.BoardListRepository;
import com.kanban.api.repository.ProjectRepository;
import com.kanban.api.repository.TaskRepository;
import com.kanban.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	private final TaskRepository taskRepository;

	private final BoardListRepository boardListRepository;

	private final ProjectRepository projectRepository;

	private final UserRepository userRepository;

	@Autowired
	public TaskService(final TaskRepository taskRepository, final BoardListRepository boardListRepository, final ProjectRepository projectRepository, final UserRepository userRepository) {
		this.taskRepository = taskRepository;
		this.boardListRepository = boardListRepository;
		this.projectRepository = projectRepository;
		this.userRepository = userRepository;
	}

	public List<Task> getTasks(final Long projectId, final Long listId) {

		if(!this.projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		return this.boardListRepository.findAllByProjectId(projectId)
				.stream()
				.filter(boardList -> boardList.getId().equals(listId))
				.map(boardList -> this.taskRepository.findAllByBoardListId(listId))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Board List " + listId + "is not found"));

	}

	@Transactional
	public Task saveTask(final Long projectId,
						 final Long boardListId,
						 final Task task,
						 final UserPrincipal userPrincipal) {

		if (!this.projectRepository.existsById(projectId)) {
			throw new BadRequestException("Project " + projectId + "is not found");
		}

		if (task.getCreator() == null) {
			final User creator  = this.userRepository
					.findById(userPrincipal.getId())
					.orElseThrow(() -> new BadRequestException("Unable to create a Task"));

			task.setCreator(creator);
		}

		final Optional<BoardList> boardList = this.boardListRepository.findById(boardListId);

		if (!boardList.isPresent()) {
			new BadRequestException("Board List " + boardListId + "is not found");
		}

		task.setBoardList(boardList.get());

		return this.taskRepository.save(task);
	}


	@Transactional
	public void deleteTask(final Long projectId, final Long boardListId, final Long taskId) {

		if (!this.projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project " + projectId + "is not found");
		}

		if (this.taskRepository.deleteByIdAndBoardListId(taskId, boardListId) <= 0)
			throw new BadRequestException("Could not delete the Task");
	}

}
