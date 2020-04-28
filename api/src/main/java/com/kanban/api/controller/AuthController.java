package com.kanban.api.controller;

import com.kanban.api.config.authentication.JwtTokenProvider;
import com.kanban.api.config.authentication.UserPrincipal;
import com.kanban.api.model.User;
import com.kanban.api.model.UserSession;
import com.kanban.api.repository.RoleRepository;
import com.kanban.api.repository.UserRepository;
import com.kanban.api.request.LoginDto;
import com.kanban.api.request.SignUpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

import static com.kanban.api.common.Constants.BASE_API;
import static com.kanban.api.common.Constants.ERROR;
import static com.kanban.api.common.Constants.SUCCESS;

@RestController
@RequestMapping(BASE_API + "/auth")
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

		SecurityContextHolder.getContext().setAuthentication(authentication);

		final String token = this.tokenProvider.generateToken(authentication);

		final Cookie cookie1 = new Cookie("token", token);
		cookie1.setPath("/");
		response.addCookie(cookie1);

		final UserSession userSession = UserSession.create((UserPrincipal) authentication.getPrincipal(), token);

		/*return ResponseEntity
				.ok()
				.header(HttpHeaders.COOKIE, cookie.toString())
				.body(Collections.singletonMap("success", token));
*/
		return ResponseEntity.status(HttpStatus.CREATED).body(userSession);
	}


	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody final SignUpDto signUpDto) {
		if (this.userRepository.existsByUsername(signUpDto.getUsername())) {
			return new ResponseEntity(Collections.singletonMap(ERROR, "Username already exist"), HttpStatus.BAD_REQUEST);
		}

		if (this.userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity(Collections.singletonMap(ERROR, "Email already exist"), HttpStatus.BAD_REQUEST);
		}

		final User user = new User(signUpDto.getFirstName(), signUpDto.getLastName(), signUpDto.getUsername(),
				signUpDto.getEmail(), signUpDto.getPassword());


		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		this.userRepository.save(user);

		return ResponseEntity.ok().body(Collections.singletonMap(SUCCESS, "User created successfully"));
	}

}
