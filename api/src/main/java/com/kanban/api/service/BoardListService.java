package com.kanban.api.service;

import com.kanban.api.model.BoardList;
import com.kanban.api.repository.BoardListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardListService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardListService.class);

	@Autowired
	private BoardListRepository boardListRepository;

	public List<BoardList> getListFromProjectId(final Long projectId) {

		return this.boardListRepository.findAllByProjectId(projectId);
	}

	public List<BoardList> getAllBoardList() {

		return this.boardListRepository.findAll();
	}

	public BoardList saveBoardList(final BoardList boardList) {
		return this.boardListRepository.save(boardList);
	}

	public void deleteBoardList(final Long id) {
		this.boardListRepository.deleteById(id);
	}

}
