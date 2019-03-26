package com.ansell.ask.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.util.grid.GenericGridParams;
import com.ansell.ask.util.grid.GridDetails;

public interface IGenericGridsDAO {

	public Integer findCount(GridDetails gridDetails, GenericGridParams gridParams);

	public List<Map<String, String>> findAllRecords(GridDetails gridDetails, GenericGridParams gridParams);

	public GridDetails getGridDetails(String gridId);
}
