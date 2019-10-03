package com.kanban.api.service;

import com.kanban.api.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

	@Autowired
	private TaskRepository taskRepository;
}
