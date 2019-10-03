package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Tasks", uniqueConstraints = {
		@UniqueConstraint(columnNames = "title", name = "UK_Tasks_title")
})
@Data
@NoArgsConstructor
@JsonIgnoreProperties("boardList")
public class Task extends DateAudit{

	private static final long serialVersionUID = 6810522483702548379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 5, max = 15)
	private String title;

	@NotBlank
	@Size(max = 70)
	private String description;

	@JoinColumn(name = "listId", nullable = false, updatable = true)
	@ManyToOne(targetEntity = BoardList.class, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BoardList boardList;
}
