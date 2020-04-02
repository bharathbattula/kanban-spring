package com.kanban.api.config.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationFilter extends OncePerRequestFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private CustomUserDetailService userDetailService;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain) throws ServletException, IOException {
		try {

			LOGGER.info("Request ==> {}", request.getRequestURI());

			final String token = this.getTokenFromRequest(request);

			LOGGER.info("Token ==> {}", token);


			final Secured secured = this.hasAnnotation(request);


			if (StringUtils.hasText(token) && this.tokenProvider.validateToken(token)) {

				final Long userId = this.tokenProvider.getUserIdFromToken(token);

				final UserDetails userDetails =
						this.userDetailService.loadUserByUserId(userId, secured != null ? 9L : null);

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

	private Secured hasAnnotation(final HttpServletRequest request) {

		final RequestMappingHandlerMapping handlerMapping = (RequestMappingHandlerMapping) this.applicationContext.getBean("requestMappingHandlerMapping");

		final HandlerExecutionChain handler;

		try {
			handler = handlerMapping.getHandler(request);

			final HandlerMethod handlerMethod = (HandlerMethod) handler.getHandler();
			return handlerMethod.getMethodAnnotation(Secured.class);

		} catch (final Exception e) {
			return null;
		}
	}

}
