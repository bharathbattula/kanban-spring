package com.kanban.api.config.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kanban.api.model.User;
import com.kanban.api.service.ProjectService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -8606223997869397724L;

	private final Long id;

	private final String name;

	private final String username;

	@JsonIgnore
	private final String email;

	@JsonIgnore
	private final String password;

	public UserPrincipal(final Long id, final String name, final String username, final String email, final String password, final Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserPrincipal create(final User user) {
		return create(user, null);
	}

	public static UserPrincipal create(final User user, final Long projectId) {

		final List<GrantedAuthority> authorities = extractAuthorities(user, projectId);/*user.getProjects().stream()
				.map(project -> {
					new SimpleGrantedAuthority(role.getName().name())
				})
				.collect(Collectors.toList());*/

		return new UserPrincipal(
				user.getId(),
				user.getName(),
				user.getUsername(),
				user.getEmail(),
				user.getPassword(),
				authorities);

	}

	private static List<GrantedAuthority> extractAuthorities(final User user, final Long projectid) {

		return ProjectService.projects.stream()
				.filter(project -> projectid.equals(project.getId()))
				.map(project -> {
					return user.getUsername().equals(project.getUser().getUsername()) ?
							new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER");
				})
				.collect(Collectors.toList());
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getEmail() {
		return this.email;
	}

	private final Collection<? extends GrantedAuthority> authorities;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
