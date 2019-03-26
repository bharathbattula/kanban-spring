package com.ansell.ask.dao.interfaces;

import java.util.List;
import java.util.Map;

import com.ansell.ask.vo.ProductTestDataRequst;
import com.ansell.ask.vo.ProductTestResultsVO;

public interface IASKDataDAO {
	
	List<ProductTestResultsVO> getTestResultsForSelectedProducts(List<String> l_productIds,String testStandardId,String testId) throws Exception;
	
	void insertProductTestRequestData(List<ProductTestDataRequst> l_productTestDataRequst) throws Exception;

	List<Map<String, Object>> getSpecs(int categoryId);
	
	Map<String, Map<String, String>> getTestResults(int defaultStandardId, Integer regionId);

	Map<String, String> getProductSpecificationValuesForProduct(List<String> productSpecIds, String productId);
	Map<String, String> getTestResultsValuesForProduct(List<String> testResultIdsList, String productId);
	String getTestHighlightLogicValue(String testId) throws Exception;
	Map<String, String> getTestCategoryMap();
	String generateTestRequestId(int cnt);
	String getHighlightInfo(String testId) throws Exception;
	List<Map<String, Object>> getStandardsBasedOnCategory(Integer regionId, Integer productCategoryId);
}
