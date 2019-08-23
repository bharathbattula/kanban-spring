package com.kanban.api.service;

import com.kanban.api.model.Project;
import com.kanban.api.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProjectService.class);

	@Autowired
	private ProjectRepository projectRepository;

	public boolean duplicateProjectName(final String projectName) {

		final Optional<Project> project = this.projectRepository.findByName(projectName);

		return project.isPresent();
	}

	public Project createProject(final Project project) {
		final Project savedProject = this.projectRepository.save(project);

		return savedProject;
	}

	public List getAllProjects(final Long id) {
		return this.projectRepository.findAllById(id);
	}
}
