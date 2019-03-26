/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Ltd
 * 
 * Developed by: Trigyn Technologies Limited.
 * TokenAuthenticationFilter.java
 * Created on 8-Feb-2019
 */
package com.ansell.ask.common;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.ansell.ask.service.interfaces.IAskCommonsService;
import com.ansell.ask.service.interfaces.IPropertyReaderService;
import com.ansell.ask.service.interfaces.ITokenAuthenticationService;
/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
@Component
@Order(1)
public class TokenAuthenticationFilter implements Filter {

	private static Logger gtLogger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

	@Autowired
	private ITokenAuthenticationService tokenAuthService = null;
	
	@Autowired
	private IPropertyReaderService propertyReaderService = null;

	@Autowired
	private IAskCommonsService askCommonsService = null;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		gtLogger.debug("Initializing TokenAuthenticationFilter..");
	}
	
	@SuppressWarnings("null")
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain )
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession session = req.getSession();

		if (session.getAttribute(IConstant.ASk_APP_VERSION_NAME) == null
				|| ("").equals(session.getAttribute(IConstant.ASk_APP_VERSION_NAME))) {
			Double askAppVersion = tokenAuthService.getAskAppVersionByModuleName(IConstant.MODULE_NAME);
			if (askAppVersion != null) {
				session.setAttribute(IConstant.ASk_APP_VERSION_NAME, askAppVersion);
			}
		}
		
		
		if (System.getProperty(IConstant.ASk_JS_DATE_FORMAT) == null
				|| ("").equals(System.getProperty(IConstant.ASk_JS_DATE_FORMAT))) {
			String dateFromatJS = propertyReaderService.getProperty(IConstant.DATE_FORMAT_PROPERTY_NAME);
			if (dateFromatJS != null) {
				System.setProperty(IConstant.ASk_JS_DATE_FORMAT, dateFromatJS);
			}
		}
		
		AskSessionUser askSessionUser = null;
		if (session.getAttribute("askSessionUser") != null) {
			askSessionUser = (AskSessionUser) session.getAttribute("askSessionUser");
		}else{
			Map<String,Object> userDetailsMap = askCommonsService.getUserDetails("deepak.patil@trigyn.com");
			askSessionUser = new AskSessionUser();
			askSessionUser.setEmail((String)userDetailsMap.get("email"));
			askSessionUser.setFirstName((String)userDetailsMap.get("firstName"));
			askSessionUser.setLastName((String)userDetailsMap.get("lastName"));
			askSessionUser.setUserId((String)userDetailsMap.get("userId"));
			askSessionUser.setRegionId((Integer)userDetailsMap.get("regionId"));
			askSessionUser.setLanguageId((Integer)userDetailsMap.get("languageId"));
			
			session.setAttribute("askSessionUser", askSessionUser);
		}
		
		String requestedURI = req.getRequestURI();
		try {
			if ((requestedURI.contains("/pingServer") == Boolean.FALSE)
					&& (requestedURI.contains("/index") == Boolean.FALSE)
					&& (requestedURI.contains("/actuator") == Boolean.FALSE)
					&& (requestedURI.contains("/favicon.ico") == Boolean.FALSE) 
					&& (requestedURI.contains("/css/") == Boolean.FALSE)
					&& (requestedURI.contains("/js/") == Boolean.FALSE)
					&& (requestedURI.contains("/img/") == Boolean.FALSE)
					&& askSessionUser == null) {

				String token = "58808539-40a4-11e9-b340-484d7e9da934"; // req.getHeader("xssf-token");
				String userId = "deepak.patil@trigyn.com";// req.getParameter("userId");

				gtLogger.info("Requested URL is ::" + requestedURI + " Having User Id::" + userId + " Token::" + token);
				String userIdByTokenId = tokenAuthService.getUserIdByToken(token);

				gtLogger.info("User Id By Token Id::" + token + " is ::" + userIdByTokenId);
				if (userIdByTokenId != null) {
					if (userId.equalsIgnoreCase(userIdByTokenId) == Boolean.FALSE) {
						gtLogger.info("User id not same");
						resp.reset();
						resp.setStatus(IConstant.TOKEN_NOT_VALID_STATUS);
						return;
					}else{
						gtLogger.info("User id is same. For Checking user have access to ASk or NOT userId::"
								+ userIdByTokenId);
						if (!askCommonsService.isAskUser(userIdByTokenId)) {
							gtLogger.error(HttpStatus.UNAUTHORIZED.value() + " User " + userIdByTokenId
									+ " does not have permission to Ask tool");
							resp.setStatus(HttpStatus.UNAUTHORIZED.value());
							return;
						} 
					}
				} else {
					gtLogger.info("User token id is null");
					resp.reset();
					resp.setStatus(IConstant.TOKEN_NOT_VALID_STATUS);
					return;
				}
			}
			chain.doFilter(request, response);
			gtLogger.info("Starting a transaction for req : {}", req.getRequestURI());
		} catch (Exception e) {
			e.printStackTrace();
			gtLogger.error("Error TokenAuthenticationFilter :" + e);
		}

	}

	@Override
	public void destroy() {
		gtLogger.debug("Destroying TokenAuthenticationFilter..");
	}

}
