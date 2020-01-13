package com.kanban.api.service;

import com.kanban.api.exception.BadRequestException;
import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.BoardList;
import com.kanban.api.model.Task;
import com.kanban.api.repository.BoardListRepository;
import com.kanban.api.repository.ProjectRepository;
import com.kanban.api.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private BoardListRepository boardListRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public List<Task> getTasks(final Long projectId, final Long listId) {

		if(!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		return this.boardListRepository.findAllByProjectId(projectId)
				.stream()
				.filter(boardList -> boardList.getId().equals(listId))
				.map(boardList -> this.taskRepository.findAllByBoardListId(listId))
				.findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Board List "+ listId + "is not found"));

	}

	public Task saveTask(Long projectId, Long boardListId, Task task) {

		if(!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		final Optional<BoardList> boardList = this.boardListRepository.findById(boardListId);

		if (boardList.isPresent()) {
			new ResourceNotFoundException("Board List "+ boardListId + "is not found");
		}

		task.setBoardList(boardList.get());

		return this.taskRepository.save(task);
	}

	public void deleteTask(Long projectId, Long boardListId, Long taskId) {

		if(!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		if(this.taskRepository.deleteByIdAndBoardListId(taskId, boardListId) <= 0)
			throw new BadRequestException("Could not delete the Task");
	}

}
