package com.kanban.api.service;

import com.kanban.api.exception.BadRequestException;
import com.kanban.api.model.User;
import com.kanban.api.repository.UserRepository;
import com.kanban.api.request.SignUpDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> loadMatchingUsers(final String email) {
		return this.userRepository.findAllByEmailContains(email);
	}

    public User saveUser(final SignUpDto signUpDto) {

        try {

            if (this.userRepository.existsByUsername(signUpDto.getUsername())) {
                throw new BadRequestException("Username already exist");
            }

            if (this.userRepository.existsByEmail(signUpDto.getEmail())) {
                throw new BadRequestException("Email already exist");
            }

            final User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getUsername(),
                    signUpDto.getEmail(), signUpDto.getPassword());

            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            return this.userRepository.save(user);

        } catch (final Exception e) {

            if (e instanceof DataIntegrityViolationException) {
                LOGGER.error("Duplicate username or email : {}, {}", signUpDto.getUsername(), signUpDto.getEmail(), e);
                throw new BadRequestException("Username or Email already exist");
            }

            LOGGER.error("error saving user", e);
            throw e;
        }
    }
}
