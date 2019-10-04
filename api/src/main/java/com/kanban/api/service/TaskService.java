package com.kanban.api.service;

import com.kanban.api.model.Task;
import com.kanban.api.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private BoardListService boardListService;

	public List getTaskFromProjectId(final Long projectId) {

		return this.boardListService.getListFromProjectId(projectId)
				.stream()
				.map(boardList -> this.taskRepository.findAllByBoardListId(boardList.getId()))
				.collect(Collectors.toList());
	}

	public Task saveTask(final Task task, final Long BoardListId) {
		return this.taskRepository.save(task);
	}
	
}
