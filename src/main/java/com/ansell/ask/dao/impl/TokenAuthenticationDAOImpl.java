/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * TokenAuthenticationDAOImpl.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ansell.ask.dao.interfaces.ITokenAuthenticationDAO;
import com.ansell.ask.service.impl.TokenAuthenticationServiceImpl;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
@Repository
public class TokenAuthenticationDAOImpl implements ITokenAuthenticationDAO{
	
	private static Logger gtLogger = LoggerFactory.getLogger(TokenAuthenticationServiceImpl.class);
	
	@Autowired
	private NamedParameterJdbcTemplate namedJDBCTemplate = null;

	@Override
	public String getUserIdByToken(String token) {
		gtLogger.info("Inside getUserIdByToken() token:: "+token);
		String userId = null;
		try {
			StringBuilder selectQuery = new StringBuilder();
			selectQuery.append("SELECT user_id FROM int_user_token_assoc WHERE token =:token");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("token", token);
			
			userId = namedJDBCTemplate.queryForObject(selectQuery.toString(), paramMap, String.class);
		}catch (EmptyResultDataAccessException e) {
			userId = null;
			gtLogger.error("Error Ocuured while fetching UserId of token::"+token+" "+e.getMessage());
		}
		return userId;
	}

	@Override
	public Double getAskAppVersionByModuleName(String moduleName) {
		gtLogger.info("Inside getAskAppVersionByModuleName() ----- {moduleName} "+moduleName);
		Double askAppVersion = null;
		try {
			StringBuilder selectQuery = new StringBuilder();
			selectQuery.append("SELECT MAX(version_num) FROM app_version_info WHERE module_name = :moduleName");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("moduleName", moduleName);
			
			askAppVersion = namedJDBCTemplate.queryForObject(selectQuery.toString(), paramMap, Double.class);
		}catch (EmptyResultDataAccessException e) {
			askAppVersion = null;
			gtLogger.error("Error Ocuured while fetching askAppVersion of module name::"+moduleName+" "+e.getMessage());
		}
		return askAppVersion;
	}

}
