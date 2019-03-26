package com.ansell.ask.service.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.common.AskSessionUser;
import com.ansell.ask.common.RequestProductVO;


public interface IAskCommonsService {
	List<Map<String, Object>> getAllRegions();
	List<Map<String, Object>> getAllManufacturers();
	List<Map<String, Object>> getAllBrands();
	List<Map<String, Object>> getAllProductCategories();
	List<Map<String, Object>> getUrgencyLevels();
	String saveOtherProduct(RequestProductVO vo,AskSessionUser user);
	
	int saveEmailDetails(Object vo, AskSessionUser user);
	String getNewOtherProductEmailTemplate(RequestProductVO vo,AskSessionUser user);
	Map<String, Object> getUserDetails(String userId);
	boolean isAskUser(String userId);
	String getRequestedProductId(String productName,String regionId,String prodManufacturer, String prodBrand);
	String getLanguageName(Integer langId);
	
}
