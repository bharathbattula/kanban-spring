package com.kanban.api.service;

import com.kanban.api.exception.ResourceNotFoundException;
import com.kanban.api.model.BoardList;
import com.kanban.api.model.Project;
import com.kanban.api.repository.BoardListRepository;
import com.kanban.api.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardListService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardListService.class);

	@Autowired
	private BoardListRepository boardListRepository;

	@Autowired
	private ProjectRepository projectRepository;

	public List<BoardList> getListFromProjectId(final Long projectId) {

		if(!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		this.boardListRepository.findAllByProjectId(projectId)
				.stream()
				.forEach(boardList -> boardList.getTask());

		return this.boardListRepository.findAllByProjectId(projectId);
	}

	public BoardList saveBoardList(final Long projectId, final BoardList boardList) {

		final Optional<Project> project = projectRepository.findById(projectId);

		if(!project.isPresent()) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		boardList.setProject(project.get());
		return this.boardListRepository.save(boardList);
	}

	public void deleteBoardList(final Long projectId, final Long boardListId) {
		if(!projectRepository.existsById(projectId)) {
			throw new ResourceNotFoundException("Project "+ projectId + "is not found");
		}

		this.boardListRepository.deleteById(boardListId);
	}

}
