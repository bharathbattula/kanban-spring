/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * AutocompleteVO.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.vo;

import java.io.Serializable;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
public class AutocompleteVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String autocompleteId = null;
	
	private String autocompleteDesc = null;
	
	public AutocompleteVO() {
	}

	public AutocompleteVO(String autocompleteId, String autocompleteDesc) {
		super();
		this.autocompleteId = autocompleteId;
		this.autocompleteDesc = autocompleteDesc;
	}
	
	public String getAutocompleteId() {
		return autocompleteId;
	}
	public void setAutocompleteId(String autocompleteId) {
		this.autocompleteId = autocompleteId;
	}
	public String getAutocompleteDesc() {
		return autocompleteDesc;
	}
	public void setAutocompleteDesc(String autocompleteDesc) {
		this.autocompleteDesc = autocompleteDesc;
	}
	
}
