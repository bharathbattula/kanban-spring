package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@JsonIgnoreProperties("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project extends DateAudit {

	private static final long serialVersionUID = -5316677316429747386L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "userId", nullable = false, updatable = false)
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User user;

	@NotBlank
	@Size(min = 5, max = 10)
	@Column(nullable = false)
	private String name;

	@Size(min = 10, max = 50)
	@Column
	private String description;

}
