package com.kanban.api.config.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailService userDetailService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		try {

			String origin = "*";

			try {
				origin = request.getHeader("origin");
			} catch (final Exception e) {
			}

			response.setHeader("Access-Control-Allow-Origin", origin);
			response.setHeader("Access-Control-Allow-Methods", "GET,POST,DELETE,PUT,OPTIONS");
			response.setHeader("Access-Control-Allow-Headers", "*");
//			response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, X-Requested-With, Content-Type, Cookie, Origin, Authorization");
			response.setHeader("Access-Control-Allow-Credentials", "true");
			response.setHeader("Access-Control-Max-Age", "180");

			final String token = this.getTokenFromRequest(request);

			if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {

				final Long userId = this.tokenProvider.getUserIdFromToken(token);

				final UserDetails userDetails = this.userDetailService.loadUserByUserId(userId);

				final UsernamePasswordAuthenticationToken authenticationToken =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);

			}

		} catch (final Exception e) {
			LOGGER.error("Token invalid", e);
		}

		filterChain.doFilter(request, response);
	}

	public String getTokenFromRequest(final HttpServletRequest request) {
		final String bearer = request.getHeader("Authorization");

		if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
			return bearer.substring(7);
		}
		return null;
	}
}
