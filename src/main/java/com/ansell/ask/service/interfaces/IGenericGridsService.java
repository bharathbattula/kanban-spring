package com.ansell.ask.service.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.util.grid.GenericGridParams;

public interface IGenericGridsService {
	public Integer findCount(String gridId, GenericGridParams gridParams);

	public List<Map<String, String>> findAllRecords(String gridId, GenericGridParams gridParams);
}
