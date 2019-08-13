package com.kanban.api.controller;

import com.kanban.api.config.authentication.JwtTokenProvider;
import com.kanban.api.exception.ApplicationException;
import com.kanban.api.model.Role;
import com.kanban.api.model.RoleName;
import com.kanban.api.model.User;
import com.kanban.api.repository.RoleRepository;
import com.kanban.api.repository.UserRepository;
import com.kanban.api.request.LoginDto;
import com.kanban.api.request.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	JwtTokenProvider tokenProvider;

	@PostMapping("signin")
	public ResponseEntity<?> loginUser(@Valid @RequestBody final LoginDto loginDto, final HttpServletResponse response) {
		final Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
						loginDto.getPassword())
		);

		final Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();

		final Collection<? extends GrantedAuthority> grantedAuthority = authentication1.getAuthorities();

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final String token = this.tokenProvider.generateToken(authentication);

		final HttpCookie cookie = ResponseCookie.from("token", token)
				.path("/")
				.build();

		final Cookie cookie1 = new Cookie("token", token);
		cookie1.setPath("/");
		response.addCookie(cookie1);

		/*return ResponseEntity
				.ok()
				.header(HttpHeaders.COOKIE, cookie.toString())
				.body(Collections.singletonMap("success", token));
*/
		return ResponseEntity.ok(Collections.singletonMap("token", token));
	}


	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody final SignUpDto signUpDto) {
		if (this.userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity(Collections.singletonMap("error", "Username already exist"), HttpStatus.BAD_REQUEST);
		}

		if (this.userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity(Collections.singletonMap("error", "Email already exist"), HttpStatus.BAD_REQUEST);
		}

		final User user = new User(signUpDto.getName(), signUpDto.getUsername(),
				signUpDto.getEmail(), signUpDto.getPassword());

		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		final Role role = this.roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new ApplicationException("User role not set yet."));

		user.setRoles(Collections.singleton(role));

		this.userRepository.save(user);

		return ResponseEntity.ok().body(Collections.singletonMap("success", "User created successfully"));
	}

}
