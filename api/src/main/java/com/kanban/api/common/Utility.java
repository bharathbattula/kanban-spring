package com.kanban.api.common;

import com.kanban.api.config.authentication.UserPrincipal;
import org.springframework.security.core.Authentication;

public class Utility {

	public static UserPrincipal getUserPrincipalFromAuthentication(final Authentication authentication) {
		return (UserPrincipal) authentication.getPrincipal();
	}
}
