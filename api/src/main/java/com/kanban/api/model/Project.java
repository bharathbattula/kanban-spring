package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "project",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "name", name = "UK_Porject_name")
		}
)
@Data
@NoArgsConstructor
@JsonIgnoreProperties("user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Project extends DateAudit {

	private static final long serialVersionUID = -5316677316429747386L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "creator", nullable = false, updatable = false)
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	private User creator;

	@NotBlank
	@Size(min = 5, max = 10)
	@Column(nullable = false)
	private String name;

	@Size(min = 10, max = 50)
	@Column
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "project_users",
			joinColumns = @JoinColumn(name = "project_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> users = new HashSet<>();

}
