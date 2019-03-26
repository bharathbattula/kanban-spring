package com.ansell.ask.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansell.ask.util.grid.GridDetails;
import com.ansell.ask.dao.interfaces.IGenericGridsDAO;
import com.ansell.ask.service.interfaces.IGenericGridsService;
import com.ansell.ask.util.grid.GenericGridParams;

@Service
public class GenericGridsService implements IGenericGridsService{
	@Autowired IGenericGridsDAO genericGridsDAO;

	@Override
	public Integer findCount(String gridId, GenericGridParams gridParams) {
		GridDetails gridDetails = getGridDetails(gridId);
		return genericGridsDAO.findCount(gridDetails, gridParams);
	}

	@Override
	public List<Map<String, String>> findAllRecords(String gridId, GenericGridParams gridParams) {
		GridDetails gridDetails = getGridDetails(gridId);
		return genericGridsDAO.findAllRecords(gridDetails, gridParams);
	}
	
	private GridDetails getGridDetails(String gridId){
		return genericGridsDAO.getGridDetails(gridId);
	}
}
