package com.ansell.ask.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ansell.ask.dao.interfaces.IResourceBundleDAO;
import com.ansell.ask.service.interfaces.IResourceBundleService;

@Service
@Transactional
public class ResourceBundleService implements IResourceBundleService {

	@Autowired
	private IResourceBundleDAO resourceBundleDAO = null;
	
	@Override
	public Map<String, String> getResourceBundleData(String localeId, String keyInitials) throws Exception {
		return resourceBundleDAO.getResourceBundleData(localeId, keyInitials);
	}

}
