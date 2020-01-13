package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Lists" , uniqueConstraints = {
		@UniqueConstraint(columnNames = "name", name = "UK_Lists_name")
})
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
		value = {"project", "task"},
		allowGetters = true
)
public class BoardList extends DateAudit {

	private static final long serialVersionUID = -3428825466995938465L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 5, max = 15)
	private String name;

	@JoinColumn(name = "projectId", nullable = false, updatable = false)
	@ManyToOne(targetEntity = Project.class, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private Project project;

	@OneToMany(cascade = CascadeType.ALL,
	mappedBy = "boardList", fetch = FetchType.LAZY)
	private List<Task> task;

}
