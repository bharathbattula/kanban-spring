package com.kanban.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
public class Project extends DateAudit {

	private static final long serialVersionUID = -5316677316429747386L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 5, max = 10)
	@Column(nullable = false)
	private String name;

	@Size(min = 10, max = 50)
	@Column
	private String description;

}
