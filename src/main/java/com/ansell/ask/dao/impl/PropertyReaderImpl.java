package com.ansell.ask.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ansell.ask.dao.interfaces.IPropertyReader;

@Repository
public class PropertyReaderImpl implements IPropertyReader {

	@Autowired
	protected NamedParameterJdbcTemplate	namedJdbcTemplate	= null;
	
	private static final String QUERY = "select COALESCE((select property_value from property_master where owner_type=:ownerType and property_name=:propertyName and owner_id=:ownerId),"
							+ "(select property_value from property_master where owner_type=:ownerType and property_name=:propertyName and owner_id=:systemId))";
	
	private static final Logger gtLogger 			= LoggerFactory.getLogger(PropertyReaderImpl.class);
	
	public String getProperty(String propertyName) {
		if(propertyName ==null || propertyName.trim().isEmpty() || propertyName.trim().equalsIgnoreCase("null")) {
			return null;
		}
		String value = null;
		try {	
		
			SqlParameterSource namedParameters = new MapSqlParameterSource()
					.addValue("ownerType", "system")
					.addValue("ownerId", "system")					
					.addValue("propertyName", propertyName)
					.addValue("systemId", "system");			
					
			value =  (String) namedJdbcTemplate.queryForObject(QUERY, namedParameters, String.class);
		 } catch (Exception e) {
			 gtLogger.error("Error while fetching messages from database : PropertyReader "+e);
		}
		return value ;
	}
}
