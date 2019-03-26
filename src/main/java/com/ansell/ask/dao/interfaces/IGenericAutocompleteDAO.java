/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * IGenericAutocompleteDAO.java
 * Created on 8-Feb-2019
 */
package com.ansell.ask.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.autocomplete.AutocompleteParams;
import com.ansell.ask.vo.AutocompleteVO;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */

public interface IGenericAutocompleteDAO {

	List<AutocompleteVO> getAutocompleteData(String a_autocompleteId, String a_searchText);

	List<Map<String, Object>> getAutocompleteData(AutocompleteParams autocompleteParams);

	Integer getCountOfData(AutocompleteParams autocompleteParams);
}
