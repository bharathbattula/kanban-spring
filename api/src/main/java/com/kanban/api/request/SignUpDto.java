package com.kanban.api.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
public class SignUpDto {

	@NotBlank
	private String name;

	@NotBlank
	private String username;

	@NotBlank
	private String email;

	@NotBlank
	private String password;

}
