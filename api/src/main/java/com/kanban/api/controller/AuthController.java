package com.kanban.api.controller;

import com.kanban.api.config.authentication.JwtTokenProvider;
import com.kanban.api.config.authentication.UserPrincipal;
import com.kanban.api.exception.BadRequestException;
import com.kanban.api.model.UserSession;
import com.kanban.api.request.LoginDto;
import com.kanban.api.request.SignUpDto;
import com.kanban.api.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

import static com.kanban.api.common.Constants.*;

@RestController
@RequestMapping(BASE_API + "/auth")
public class AuthController {

	public static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider tokenProvider;

	private final UserService userService;

	@Autowired
	public AuthController(final AuthenticationManager authenticationManager, final JwtTokenProvider tokenProvider, final UserService userService) {
		this.authenticationManager = authenticationManager;
		this.tokenProvider = tokenProvider;
		this.userService = userService;
	}

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
		try {

			return ResponseEntity.ok(this.userService.saveUser(signUpDto));

		} catch (final BadRequestException e) {
			LOGGER.error("User registration failed", e);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(Collections.singletonMap(ERROR, e.getMessage()));

		} catch (final Exception e) {
			LOGGER.error("User registration failed", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap(ERROR, INTERNAL_SERVER_ERROR_MSG));
		}
	}

}
