package com.kanban.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class LoginDto {

	@NotBlank
	private String usernameOrEmail;

	@NotBlank
	private String password;

}
