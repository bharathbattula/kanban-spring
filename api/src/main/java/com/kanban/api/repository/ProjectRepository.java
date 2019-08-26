package com.kanban.api.repository;

import com.kanban.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByName(String name);

	List<Project> findAllById(Long id);

	List<Project> findAllByUserId(Long userId);
}
