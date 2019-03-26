/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * GenericAutocompleteService.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansell.ask.autocomplete.AutocompleteParams;
import com.ansell.ask.dao.interfaces.IGenericAutocompleteDAO;
import com.ansell.ask.service.interfaces.IGenericAutocompleteService;
import com.ansell.ask.vo.AutocompleteVO;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */

@Service
@Transactional
public class GenericAutocompleteService implements IGenericAutocompleteService {

	@Autowired
	private IGenericAutocompleteDAO autocompleteDAO;
	
	public List<AutocompleteVO> getAutocompleteData(String a_autocompleteId, String a_searchText) {
		return autocompleteDAO.getAutocompleteData(a_autocompleteId, a_searchText);
		
	}

	public List<Map<String, Object>> getAutocompleteData(AutocompleteParams autocompleteParams) {
		return autocompleteDAO.getAutocompleteData(autocompleteParams);
	}
	
	public Integer getCountOfData(AutocompleteParams autocompleteParams) {
		return autocompleteDAO.getCountOfData(autocompleteParams);
	}
}
