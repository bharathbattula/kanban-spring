package com.ansell.ask.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.vo.ProductDetailsVO;
import com.ansell.ask.vo.ProductSpecificationVO;
import com.ansell.ask.vo.ProductTestDataRequst;
import com.ansell.ask.vo.ProductTestResultsVO;
import com.ansell.ask.vo.TestCategoryVO;

public interface IASKService {
	List<ProductSpecificationVO> getSpecs(Integer productCategoryId);
	List<TestCategoryVO> getTestResults(Integer defaultstandardId, Integer regionId);
	ProductDetailsVO getProductDetails(String productId, List<String> productSpecIds, Map<String, List<String>> map, String productName) throws IOException;
	List<ProductTestResultsVO> getTestResultsForSelectedProducts(String testId,String testStandardId,List<String> productIds) throws Exception;
	
	String createProductTestRequestData(RequestProductVO productVO);
	List<Map<String, Object>> getStandardsBasedOnCategory(Integer regionId, Integer productCategoryId);
	ProductDetailsVO getTestResultsForProductByStandard(List<TestCategoryVO> categoryVOs, String productId);
}
