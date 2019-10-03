package com.kanban.api.service;

import com.kanban.api.repository.BoardListRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardListService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BoardListService.class);

	@Autowired
	private BoardListRepository boardListRepository;
}
