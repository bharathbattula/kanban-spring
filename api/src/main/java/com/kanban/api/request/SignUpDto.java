package com.kanban.api.request;

import javax.validation.constraints.NotBlank;

public class SignUpDto {

	@NotBlank
	private String name;

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public SignUpDto() {}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
