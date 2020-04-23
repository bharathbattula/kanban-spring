package com.kanban.api.service;

import com.kanban.api.model.User;
import com.kanban.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public List<User> loadMatchingUsers(final String email) {
		return this.userRepository.findAllByEmailContains(email);
	}
}
