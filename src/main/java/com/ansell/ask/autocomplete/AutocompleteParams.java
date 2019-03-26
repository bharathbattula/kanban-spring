/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Ltd
 * 
 * Developed by: Trigyn Technologies Limited.
 * AutocompleteParams.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.autocomplete;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */

public class AutocompleteParams {
	
	private String				autocompleteId			= null;
	private String				searchText		= null;
	private Integer				startIndex		= Integer.MIN_VALUE;
	private Integer				pageSize		= Integer.MIN_VALUE;
	private Map<String, Object>	criteriaParams	= null;
	
	public AutocompleteParams(HttpServletRequest request){
		this.searchText=request.getParameter("searchText");
		this.autocompleteId=request.getParameter("autocompleteId");
		this.startIndex = request.getParameter("startIndex") == null ? -1 : Integer.parseInt(request.getParameter("startIndex"));
		this.pageSize = request.getParameter("pageSize") == null ? -1 : Integer.parseInt(request.getParameter("pageSize"));
		
		Set<String> reqParamKeys = request.getParameterMap().keySet();
		this.criteriaParams = new HashMap<String, Object>();
		for (String reqParamKey : reqParamKeys) {

			if (reqParamKey.contains("ap_")) {
				Object obj = null;
				if (request.getParameter(reqParamKey).contains("int_")) {
					obj = Integer.parseInt(request.getParameter(reqParamKey).replace("int_", ""));
				}
				if (request.getParameter(reqParamKey).contains("str_")) {
					obj = request.getParameter(reqParamKey).replace("str_", "");
				}
				this.criteriaParams.put(reqParamKey.replace("ap_", ""), obj);
			}else if(reqParamKey.contains("ainp_")){
				Object obj = null;
				if (request.getParameter(reqParamKey).contains("int_")) {
					obj = request.getParameter(reqParamKey).replace("int_", "");
					List<Integer> intListParameter = new Gson().fromJson(obj.toString(), new TypeToken<List<Integer>>() {}.getType());
					obj = intListParameter;
				}
				if (request.getParameter(reqParamKey).contains("str_")) {
					obj = request.getParameter(reqParamKey).replace("str_", "");
					List<String> strListParameter = new Gson().fromJson(obj.toString(), new TypeToken<List<String>>() {}.getType());
					obj = strListParameter;
				}
				this.criteriaParams.put(reqParamKey.replace("ainp_", ""), obj);
			}
		}
		
	}

	public String getAutocompleteId() {
		return autocompleteId;
	}
	
	public void setAutocompleteId(String autocompleteId) {
		this.autocompleteId = autocompleteId;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Map<String, Object> getCriteriaParams() {
		return criteriaParams;
	}

	public void setCriteriaParams(Map<String, Object> criteriaParams) {
		this.criteriaParams = criteriaParams;
	}

	
	
}
