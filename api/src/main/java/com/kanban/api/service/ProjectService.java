package com.kanban.api.service;

import com.kanban.api.config.authentication.UserPrincipal;
import com.kanban.api.exception.BadRequestException;
import com.kanban.api.model.Project;
import com.kanban.api.model.User;
import com.kanban.api.repository.ProjectRepository;
import com.kanban.api.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);


	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private UserRepository userRepository;

	public static final List<Project> projects = new ArrayList<>();

	@PostConstruct
	public void init() {
		projects.addAll(this.projectRepository.findAll());
	}

	public boolean duplicateProjectName(final String projectName) {

		final Optional<Project> project = this.projectRepository.findByName(projectName);

		return project.isPresent();
	}

	public Project createProject(final Project project, final UserPrincipal userPrincipal) {

		final User user = this.userRepository.findById(userPrincipal.getId()).get();

		project.setCreator(user);

		return this.projectRepository.save(project);
	}

	public List getAllProjects(final Long userId) {
		return this.projectRepository.findAllByWhereUserId(userId);
	}

	@Transactional
	public void deleteProject(final Long projectId) {
		this.projectRepository.deleteById(projectId);
	}

	@Transactional
	public Project updateProject(final Project project, final UserPrincipal userPrincipal) {

		final User user = this.userRepository.findById(userPrincipal.getId()).get();

		project.setCreator(user);

		return this.projectRepository.save(project);
	}

	@Transactional
	public Project addNewUserToProject(final Long projectId, final User user) throws Exception {

		final Project project = this.projectRepository
				.findById(projectId)
				.orElseThrow(() -> new BadRequestException("Invalid Project"));

		final User newUser = this.userRepository
				.findById(user.getId())
				.orElseThrow(() -> new BadRequestException("Invalid User"));

		project.getUsers().add(newUser);

		return this.projectRepository.save(project);

	}

	public Project removeAccess(final Long projectId, final User user) {

		final Project project = this.projectRepository
				.findById(projectId)
				.orElseThrow(() -> new BadRequestException("Invalid Project"));

		final User newUser = this.userRepository
				.findById(user.getId())
				.orElseThrow(() -> new BadRequestException("Invalid User"));

		project.getUsers().remove(user);

		return this.projectRepository.save(project);

	}
}
