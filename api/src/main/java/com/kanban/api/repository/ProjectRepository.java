package com.kanban.api.repository;

import com.kanban.api.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

	Optional<Project> findByName(String name);

	@Override
	Optional<Project> findById(Long id);

	@Query(value = "select * from Project where id in (select project_id from project_users where user_id = :userId)", nativeQuery = true)
	List<Project> findAllByWhereUserId(@Param("userId") Long userId);
}
