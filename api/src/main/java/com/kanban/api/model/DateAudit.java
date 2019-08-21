package com.kanban.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
		value = {"createdAt", "updatedAt"},
		allowGetters = true
)

public abstract class DateAudit implements Serializable {

	private static final long serialVersionUID = 6166211908829379534L;

	@Getter
	@Setter
	@CreatedDate
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@Getter
	@Setter
	@LastModifiedDate
	@Column(nullable = false)
	private Instant updatedAt;


	/*public Instant getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(final Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(final Instant updatedAt) {
		this.updatedAt = updatedAt;
	}*/
}