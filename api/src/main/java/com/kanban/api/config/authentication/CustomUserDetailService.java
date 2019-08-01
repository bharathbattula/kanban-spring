package com.kanban.api.config.authentication;

import com.kanban.api.model.User;
import com.kanban.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

		final User user  = this.userRepository.findByUsernameOrEmail(username, username)
				.orElseThrow(() ->
						new UsernameNotFoundException("User not found with the username "+username));

		return UserPrincipal.create(user);
	}

	public UserDetails loadUserByUserId(final Long id){

		final User user = this.userRepository.findById(id)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with the Id "));

		return UserPrincipal.create(user);
	}

}
