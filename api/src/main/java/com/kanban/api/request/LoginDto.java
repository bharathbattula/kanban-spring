package com.kanban.api.request;

import javax.validation.constraints.NotBlank;

public class LoginDto {

	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

	public LoginDto() {

	}

	public String getUsernameOrEmail() {
		return this.usernameOrEmail;
	}

	public void setUsernameOrEmail(final String usernameOrEmail) {
		this.usernameOrEmail = usernameOrEmail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}
}
