package com.kanban.api.repository;

import com.kanban.api.model.BoardList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardListRepository extends JpaRepository<BoardList, Long> {

	List<BoardList> findAllByProjectId(Long projectId);

	Optional<BoardList> findById(Long id);

}
