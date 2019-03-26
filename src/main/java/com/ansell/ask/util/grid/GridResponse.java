package com.ansell.ask.util.grid;

import java.util.List;
import java.util.Map;

public class GridResponse {
	List<Map<String, String>> list;

	Integer matchingRowCount;

	GenericGridParams gridParams;

	Map<String, String> userData;
	
	public GridResponse(List<Map<String, String>> list, Integer matchingRowCount, GenericGridParams gridParams)
	{
		super();
		this.list = list;
		this.matchingRowCount = matchingRowCount;
		this.gridParams = gridParams;
	}

	public GridResponse(List<Map<String, String>> list, Integer matchingRowCount, GenericGridParams gridParams, Map<String, String> userData)
	{
		super();
		this.list = list;
		this.matchingRowCount = matchingRowCount;
		this.gridParams = gridParams;
		this.userData = userData;
	}

	public CustomGridResponse getResponse() throws Exception
	{

		Double rowCount = new Double(matchingRowCount);

		Double totalPages = Math.ceil(rowCount / gridParams.getRowsPerPage());
		
		//TODO confirm the need for below check
		if(gridParams.getStartIndex() > matchingRowCount)
		{
		    gridParams.setPageIndex(1);
		    gridParams.setStartIndex(0);		 
		}

		CustomGridResponse response = new CustomGridResponse();

		response.setRows(list);

		response.setRecords(String.valueOf(matchingRowCount));

		response.setPage(String.valueOf(gridParams.getPageIndex()));

		response.setTotal(String.valueOf(totalPages.intValue()));

		if (userData != null)
		{
			response.setUserdata(userData);
		}

		return response;
	}
}
