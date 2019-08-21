package com.kanban.api.model;

import com.kanban.api.config.authentication.UserPrincipal;
import lombok.Data;

@Data
public class UserSession {

	private final String name;
	private final String username;
	private final String emailId;
	private final String token;


	public static UserSession create(final UserPrincipal user, final String token) {
		return new UserSession(user.getName(), user.getUsername(), user.getEmail(), token);
	}

}
