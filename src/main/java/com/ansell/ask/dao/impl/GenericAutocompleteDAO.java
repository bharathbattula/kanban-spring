/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * GenericAutocompleteDAO.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.ansell.ask.autocomplete.AutocompleteParams;
import com.ansell.ask.dao.interfaces.IGenericAutocompleteDAO;
import com.ansell.ask.vo.AutocompleteVO;




/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */

@Repository
public class GenericAutocompleteDAO implements IGenericAutocompleteDAO {
	
	@Autowired
	protected NamedParameterJdbcTemplate	namedJdbcTemplate	= null;
	
	private static final String QUERY_SELECTOR = "select ac_select_query from int_autocomplete_details where ac_id = :ac_id";

	public List<Map<String, Object>> getAutocompleteData(AutocompleteParams autocompleteParams) {
		String query = getQueryForAutoComplete(autocompleteParams);
		List<Map<String, Object>> displayList = getAutocompleteDetails(query, autocompleteParams);
		return displayList;
	}

	private String getQueryForAutoComplete(AutocompleteParams autocompleteParams) {		
		SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("ac_id", autocompleteParams.getAutocompleteId());		
		return namedJdbcTemplate.queryForObject(QUERY_SELECTOR, namedParameters, String.class);		
	}

	private List<Map<String, Object>> getAutocompleteDetails(String a_autocompleteQuery, AutocompleteParams a_autocompleteParams) {

		boolean is_LimitPresent = true;
		Integer startIndex = a_autocompleteParams.getStartIndex();
		if(startIndex.intValue() == -1) {
			is_LimitPresent = false;
		}
		
		if(is_LimitPresent){
			a_autocompleteQuery = a_autocompleteQuery + " LIMIT :startIndex , :pageSize ";
		}
		
		Map<String,Object> namedParameters = new HashMap<String,Object>();

		Set<String> additionalParameter = a_autocompleteParams.getCriteriaParams().keySet();
		for (Object object : additionalParameter) {
			namedParameters.put(object.toString(), a_autocompleteParams.getCriteriaParams().get(object.toString()));
		}
		namedParameters.put("searchText", a_autocompleteParams.getSearchText());
		namedParameters.put("startIndex", a_autocompleteParams.getStartIndex());
		namedParameters.put("pageSize", a_autocompleteParams.getPageSize());
		List<Map<String, Object>> displayList = namedJdbcTemplate.queryForList(a_autocompleteQuery, namedParameters);

		return displayList;
	}

	public List<AutocompleteVO> getAutocompleteData(String a_autocompleteId, String a_searchText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountOfData(AutocompleteParams a_autocompleteParams) {
		String a_autocompleteQuery = "SELECT COUNT(*) FROM ("+getQueryForAutoComplete(a_autocompleteParams);
		boolean is_LimitPresent = true;
		Integer startIndex = a_autocompleteParams.getStartIndex();
		if(startIndex.intValue() == -1) {
			is_LimitPresent = false;
		}
		
		if(is_LimitPresent){
			a_autocompleteQuery = a_autocompleteQuery + " LIMIT :startIndex , :pageSize ";
		}
		a_autocompleteQuery += ") as count";
		Map<String,Object> namedParameters = new HashMap<String,Object>();

		Set<String> additionalParameter = a_autocompleteParams.getCriteriaParams().keySet();
		for (Object object : additionalParameter) {
			namedParameters.put(object.toString(), a_autocompleteParams.getCriteriaParams().get(object.toString()));
		}
		namedParameters.put("searchText", a_autocompleteParams.getSearchText());
		namedParameters.put("startIndex", a_autocompleteParams.getStartIndex());
		namedParameters.put("pageSize", a_autocompleteParams.getPageSize());
		//Integer count = namedJdbcTemplate.queryForInt(a_autocompleteQuery, namedParameters);
		Integer count = namedJdbcTemplate.queryForObject(a_autocompleteQuery, namedParameters,Integer.class);
		return count;
	}

}
