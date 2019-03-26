package com.ansell.ask.dao.interfaces;

import java.util.List;

import com.ansell.ask.common.PropertyMasterVO;

public interface IPropertyMasterDAO 
{
	public List<PropertyMasterVO> getAllProperties(String ownerType, String ownerId, String propertyName);
	
	public PropertyMasterVO getSingleProperty(String ownerType, String ownerId, String propertyName);
}