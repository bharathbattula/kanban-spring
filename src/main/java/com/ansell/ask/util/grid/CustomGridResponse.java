/**
 * 
 */
/**
 * @author Debalaxmi.Sahoo
 *
 */
package com.ansell.ask.util.grid;

import java.util.List;
import java.util.Map;

public class CustomGridResponse {
	/**
	 * Current page of the query
	 */
	private String page;
	
	/**
	 * Total pages for the query
	 */
	private String total;
	
	/**
	 * Total number of records for the query
	 */
	private String records;
	
	/**
	 * An array that contains the actual objects
	 */
	private List<Map<String,String>> rows;
	
	private Map userdata;

	
	public CustomGridResponse() {
	}
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRecords() {
		return records;
	}

	public void setRecords(String records) {
		this.records = records;
	}

	public List<Map<String,String>> getRows() {
		return rows;
	}

	public void setRows(List<Map<String,String>> rows) {
		this.rows = rows;
	}

	public Map getUserdata() {
	    return userdata;
	}

	public void setUserdata(Map userdata) {
	    this.userdata = userdata;
	}
}