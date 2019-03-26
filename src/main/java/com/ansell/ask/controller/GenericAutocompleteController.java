/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Ltd
 * 
 * Developed by: Trigyn Technologies Limited.
 * GenericAutocompleteController.java
 * Created on 8-Feb-2019
 */
package com.ansell.ask.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansell.ask.autocomplete.AutocompleteParams;
import com.ansell.ask.service.interfaces.IGenericAutocompleteService;

/**
 * 
 * @author Sudhirkumarrao.Allada
 * @version $Revision$ Last changed by $Author$ on $Date$ as $Revision$
 */

@Controller
public class GenericAutocompleteController {

	@Autowired
	private IGenericAutocompleteService genericAutocompleteService = null;

	@RequestMapping(value = { "/getAutocompleteData" }, method = RequestMethod.POST, produces = "application/json" )
	@ResponseBody
	public List<Map<String, Object>> getAutocompleteData(HttpServletRequest request, HttpServletResponse response) {
		AutocompleteParams autocompleteParams = new AutocompleteParams(request);
		List<Map<String, Object>> data = genericAutocompleteService.getAutocompleteData(autocompleteParams);
		return data;

	}

	@RequestMapping(value = { "/getCountOfData" })
	@ResponseBody
	public Integer getCountOfData(HttpServletRequest request, HttpServletResponse response) {
		AutocompleteParams autocompleteParams = new AutocompleteParams(request);
		Integer count = genericAutocompleteService.getCountOfData(autocompleteParams);
		return count;

	}
}
