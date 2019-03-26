package com.ansell.ask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ansell.ask.dao.interfaces.IPropertyReader;
import com.ansell.ask.service.interfaces.IPropertyReaderService;

@Service
public class PropertyReaderServiceImpl implements IPropertyReaderService {

	@Autowired
	private IPropertyReader propertyReaderDao = null;
	
	@Override
	public String getProperty(String propertyName) {
		return propertyReaderDao.getProperty(propertyName);
	}

}
