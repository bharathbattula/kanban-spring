package com.kanban.api.repository;

import com.kanban.api.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

	List<Task> findAllByBoardListId(Long boardListId);

	long deleteByIdAndBoardListId(Long taskId, Long boardListId);

}
