package com.ansell.ask.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.common.RequestProductVO;

public interface IAskCommonsDAO{
	List<Map<String, Object>> getAllRegions();
	List<Map<String, Object>> getAllManufacturers();
	List<Map<String, Object>> getAllBrands();
	List<Map<String, Object>> getAllProductCategories();
	List<Map<String, Object>> getUrgencyLevels();
	String saveOtherProduct(RequestProductVO vo,String updated_by);
	int saveEmailDetails(String toUser,String fromUser,String cc, String bcc,String subject,String body);
	int getMaxId(String query);
	Map<String,Object> getUserDetails(String userId);
	boolean isAskUser(String userId);
	String getRequestedProductId(String productName,String  regionId,String prodManufacturer,String prodBrand);
	String getLanguageName(Integer langId);
}
