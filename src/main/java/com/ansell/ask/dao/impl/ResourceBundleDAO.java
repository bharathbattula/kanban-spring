package com.ansell.ask.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ansell.ask.common.IConstant;
import com.ansell.ask.dao.interfaces.IResourceBundleDAO;

@Repository
public class ResourceBundleDAO implements IResourceBundleDAO {

	@Autowired
	private NamedParameterJdbcTemplate namedJdbcTemplate = null;
	
	public final static String QUERY_TO_GET_I18N_DATA = "SELECT rb.resource_key AS id, COALESCE( (SELECT rb1.text FROM resource_bundle    AS rb1 "
			+ "JOIN language AS lang ON     lang.language_id = rb1.language_id AND lang.language_code LIKE :localeId  "
			+ "WHERE rb1.resource_key = rb.resource_key) ,  rb.text) AS value FROM resource_bundle    rb "
			+ "LEFT OUTER JOIN language ON rb.language_id = language.language_id "
			+ "WHERE language.language_code LIKE :defaultLocaleId  "
			+ "AND ( rb.resource_key LIKE concat(:keyInitials, '%')  OR rb.resource_key LIKE concat(:headerKeyInitials, '%'))"; 
	
	@Override
	public Map<String, String> getResourceBundleData(String localeId, String keyInitials) throws Exception {
	
		Map<String,Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("localeId", localeId + "%");
		namedParameters.put("defaultLocaleId", IConstant.DEFAULT_LOCALE);
		namedParameters.put("keyInitials", keyInitials);
		namedParameters.put("headerKeyInitials",IConstant.RESOURCE_BUNDLE_HEADER_KEY);
		List<Map<String, Object>> resultSet = namedJdbcTemplate.queryForList(QUERY_TO_GET_I18N_DATA, namedParameters);
		Map<String, String> result = new HashMap<String, String>();

		for (Map<String, Object> each : resultSet)
		{
			if(localeId.equals(IConstant.DEFAULT_LOCALE)){
				 result.put(each.get("id").toString(),each.get("value").toString());
			}else{
				String originalString = StringEscapeUtils.unescapeHtml(each.get("value").toString());
				String convertedTOUTF8 = new String(originalString.getBytes("UTF8"),"UTF8");
				result.put(each.get("id").toString(),convertedTOUTF8);
			}
		}
		return result;
	
	}

}
