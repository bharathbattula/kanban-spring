package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "Tasks", uniqueConstraints = {
		@UniqueConstraint(columnNames = "title", name = "UK_Tasks_title")
})
@Data
@NoArgsConstructor
@JsonIgnoreProperties(
		value = "boardList"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task extends DateAudit{

	private static final long serialVersionUID = 6810522483702548379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 8, max = 50)
	private String title;

	@NotBlank
	@Size(max = 150)
	private String description;

	@Column
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate deadLine;

	@JsonIgnore
	@JoinColumn(name = "listId", nullable = false, updatable = true)
	@ManyToOne(targetEntity = BoardList.class, fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private BoardList boardList;
}
