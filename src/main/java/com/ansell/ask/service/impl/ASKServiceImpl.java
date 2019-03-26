package com.ansell.ask.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ansell.ask.common.IConstant;
import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.common.Utility;
import com.ansell.ask.dao.interfaces.IASKDataDAO;
import com.ansell.ask.dao.interfaces.IPropertyReader;
import com.ansell.ask.service.interfaces.IASKService;
import com.ansell.ask.service.interfaces.IAskCommonsService;
import com.ansell.ask.vo.ProductDetailsVO;
import com.ansell.ask.vo.ProductSpecificationVO;
import com.ansell.ask.vo.ProductTestDataRequst;
import com.ansell.ask.vo.ProductTestResultsVO;
import com.ansell.ask.vo.TestCategoryVO;
import com.ansell.ask.vo.TestResultsVO;

@Service
public class ASKServiceImpl implements IASKService {

	private static Logger gtLogger = LoggerFactory.getLogger(ASKServiceImpl.class);

	@Autowired
	private IASKDataDAO askDataDAO = null;
	
	@Autowired
	private IAskCommonsService askCommonService = null;
	
	@Autowired
	private IPropertyReader propertyReader;

	@Override
	public List<ProductSpecificationVO> getSpecs(Integer productCategoryId) {

		List<ProductSpecificationVO> specificationVOs = new ArrayList<ProductSpecificationVO>();
		List<Map<String, Object>> l_productSpecsList = askDataDAO.getSpecs(productCategoryId);

		String l_specificationId = null;
		String l_specificationName = null;

		for (Map<String, Object> spec : l_productSpecsList) {

			ProductSpecificationVO specificationVO = new ProductSpecificationVO();
			l_specificationId = spec.get("specificationId").toString();
			l_specificationName = spec.get("specificationName").toString();

			if (StringUtils.isNotEmpty(l_specificationId)) {
				specificationVO.setProdSpecificationId(l_specificationId);
			}
			if (StringUtils.isNotEmpty(l_specificationName)) {
				specificationVO.setProdSpecificationName(l_specificationName);
			}
			specificationVOs.add(specificationVO);

		}
		return specificationVOs;
	}

	@Override
	public List<TestCategoryVO> getTestResults(Integer defaultstandardId, Integer regionId) {


		List<TestCategoryVO> testCategoryVOs = new ArrayList<TestCategoryVO>();
		Map<String, String> testCategoryMap = askDataDAO.getTestCategoryMap();
		Map<String, Map<String, String>> l_productTestsMap = askDataDAO.getTestResults(defaultstandardId, regionId);

		Set<String> testCategoriesSet = l_productTestsMap.keySet();
		for (String testCategoryId : testCategoriesSet) {
			List<TestResultsVO> testResultsVOs = new ArrayList<TestResultsVO>();
			TestCategoryVO testCategory = new TestCategoryVO();
			testCategory.setTestCategoryId(testCategoryId);
			testCategory.setTestCategroyName(testCategoryMap.get(testCategoryId));
			
			Map<String, String> labtestsMap = l_productTestsMap.get(testCategoryId);
			for(Map.Entry<String, String> mapEntry : labtestsMap.entrySet()) {
				TestResultsVO testResultsVO = new TestResultsVO();
				String testId = mapEntry.getKey();
				String testName = mapEntry.getValue();
				
				if (StringUtils.isNotBlank(testId)) {
					testResultsVO.setTestId(testId);
				}
				if (StringUtils.isNotBlank(testName)) {
					testResultsVO.setTestName(testName);
				}
				testResultsVOs.add(testResultsVO);
			}
			testCategory.setTestResultsVOs(testResultsVOs);
			testCategoryVOs.add(testCategory);
		}
		
		return testCategoryVOs;
	}

	@Override
	public ProductDetailsVO getProductDetails(String productId, List<String> productSpecIds,
			Map<String, List<String>> map, String productName) throws IOException {

		ProductDetailsVO detailsVO = new ProductDetailsVO();
		detailsVO.setProductId(productId);
		detailsVO.setProductName(productName);

		File  imageFile = new File("D:\\ASK_IMAGES\\Product_Images\\"+productId);
		
		if(imageFile.exists() == Boolean.FALSE) {
			imageFile = new ClassPathResource("/static_images/noimage.jpg").getFile();
		}

		String base64Image = convertImageToBASE64(imageFile);
		detailsVO.setProductImagePath(base64Image);
		try {
			List<ProductSpecificationVO> productSpecificationVOs = new ArrayList<ProductSpecificationVO>();
			Map<String, String> productSpecificationValues = new HashMap<String, String>();
			if(productSpecIds != null && productSpecIds.isEmpty() == Boolean.FALSE) {
				productSpecificationValues = askDataDAO.getProductSpecificationValuesForProduct(productSpecIds, productId);
			}		
			
			for (String specId : productSpecIds) {
				ProductSpecificationVO specificationVO = new ProductSpecificationVO();
				String specsValue = productSpecificationValues.get(specId) != null ? productSpecificationValues.get(specId) : "";
				specificationVO.setProdSpecificationId(specId);
				specificationVO.setProdSpecificationValue(specsValue);
				productSpecificationVOs.add(specificationVO);
			}
			detailsVO.setProductSpecificationVOs(productSpecificationVOs);
			List<TestCategoryVO> categoryVOs = new ArrayList<TestCategoryVO>();
			Set<String> testCategoryIds = map.keySet();
	
			for (String testCatId : testCategoryIds) {
				List<String> testResultIdsList = map.get(testCatId);
				Map<String, String> testResultsMap = askDataDAO.getTestResultsValuesForProduct(testResultIdsList, productId);			
				TestCategoryVO categoryVO = new TestCategoryVO();
				List<TestResultsVO> resultsVOs = new ArrayList<TestResultsVO>();
				categoryVO.setTestCategoryId(testCatId);
				for (String testResultId : testResultIdsList) {
					TestResultsVO resultsVO = new TestResultsVO();
					String testValue = testResultsMap.get(testResultId) != null ? testResultsMap.get(testResultId) : "";
					resultsVO.setTestId(testResultId);
					resultsVO.setTestValue(testValue);				
					resultsVO.setTestHighlightLogic(askDataDAO.getHighlightInfo(testResultId));				
					resultsVOs.add(resultsVO);
				}
				categoryVO.setTestResultsVOs(resultsVOs);
				categoryVOs.add(categoryVO);
			}
			detailsVO.setTestCategoryVOs(categoryVOs);
		}
		catch (Exception ex) {			
			gtLogger.error("Exception in getProductDetails method"+ex);
		}
		return detailsVO;
	}

	private String convertImageToBASE64(File imageFile) throws IOException {
		String base64Image = "";
		FileInputStream imageInFile = null;
		try {
			imageInFile = new FileInputStream(imageFile);
			byte imageData[] = new byte[(int) imageFile.length()];
			imageInFile.read(imageData);
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (imageInFile != null) {
				imageInFile.close();
			}
		}
		return base64Image;
	}

	

	public List<ProductTestResultsVO> getTestResultsForSelectedProducts(String testId,String testStandardId , List<String> productIds)
			throws Exception {
		List<ProductTestResultsVO> l_productTestResultsVO = null;
		List<Map<String, Object>> l_testResultsMap = null;
		try {
			l_productTestResultsVO = new ArrayList<ProductTestResultsVO>();
			l_productTestResultsVO = askDataDAO.getTestResultsForSelectedProducts(productIds,testStandardId,testId);
			/*
			 * if(!CollectionUtils.isEmpty(l_tests)){ l_testResultsMap = new
			 * ArrayList<Map<String,Object>>(); l_testResultsMap =
			 * convertTestResultListToMap(l_tests); }
			 */
		} catch (Exception ex) {
			gtLogger.error("error in getTests() method" + ex.getMessage());
		}
		return l_productTestResultsVO;
	}

	@Override
	public String createProductTestRequestData(RequestProductVO productVO){	
	try{		
			Date resultDate = null;
			Map<String, List<String>> productTestIdsMap = new HashMap<String, List<String>>();

			gtLogger.info("createProductTestRequestData : RequestProductVO : " + productVO);
			productTestIdsMap = (Map<String, List<String>>) productVO.getProductRequestedTestMap();

			if (StringUtils.isNotBlank(productVO.getTestResultsNeededByDate())) {
				SimpleDateFormat sdf1 = new SimpleDateFormat(propertyReader.getProperty("dateFormat"));
				resultDate = sdf1.parse(productVO.getTestResultsNeededByDate());
			}

			ProductTestDataRequst o_productTestDataRequest = new ProductTestDataRequst();
			o_productTestDataRequest.setIsbenchmarkTestingRequired(productVO.getIsBenchmarktestingRequired());
			o_productTestDataRequest.setIsSupportSampleProcurement(productVO.getSupportsProcurement());
			o_productTestDataRequest.setUrgency(productVO.getUrgencyLevel());
			o_productTestDataRequest.setSendCopyOfResquest(productVO.getCopyOfResponse());
			o_productTestDataRequest.setWhenTestResultNeeded(resultDate);
			o_productTestDataRequest.setAdditionalInfo(productVO.getAdditionalInfo());
		
			List<ProductTestDataRequst> l_productTestDataRequst = convertMapToProductTestDataRequstObj(o_productTestDataRequest,productTestIdsMap);			
			if(IConstant.SendCopyOfResponseEmail.TRUE.getStatus() == o_productTestDataRequest.getSendCopyOfResquest()){
				askCommonService.saveEmailDetails(productTestIdsMap,null);
			} 			
			askDataDAO.insertProductTestRequestData(l_productTestDataRequst);
		} 
		catch (Exception ex) {
			gtLogger.error("Exception in insertProductTestRequestData" + ex);
		}
		return "success";
	}
	
	private List<ProductTestDataRequst> convertMapToProductTestDataRequstObj(ProductTestDataRequst o_productTestDataRequst,Map<String,List<String>> productTestIdsMap){
		
		List<ProductTestDataRequst> l_productTestDataRequst = new ArrayList<ProductTestDataRequst>();
		ProductTestDataRequst productTestDataRequst = null;
		
		for (Entry<String, List<String>> entry : productTestIdsMap.entrySet())
		{
		   String productId = entry.getKey();
		   List<String> testIds = productTestIdsMap.get(productId);
		   int cnt = 0;
		   for(String testId : testIds) {
			   productTestDataRequst = new ProductTestDataRequst();  
			   productTestDataRequst.setRequestId(askDataDAO.generateTestRequestId(cnt));
			   productTestDataRequst.setProductId(productId);
			   productTestDataRequst.setTestId(testId);	   
			  
			   productTestDataRequst = addOtherInfoIntoObject(productTestDataRequst,o_productTestDataRequst);
			   l_productTestDataRequst.add(productTestDataRequst);
			   cnt = cnt + 1;
		   }
		   
		}
		return l_productTestDataRequst;
	}
	
	private ProductTestDataRequst addOtherInfoIntoObject(ProductTestDataRequst productTestDataRequst,ProductTestDataRequst o_productTestDataRequst){
		
		productTestDataRequst.setIsbenchmarkTestingRequired(o_productTestDataRequst.getIsbenchmarkTestingRequired());
		productTestDataRequst.setIsSupportSampleProcurement(o_productTestDataRequst.getIsSupportSampleProcurement());
		productTestDataRequst.setUrgency(o_productTestDataRequst.getUrgency());
		productTestDataRequst.setSendCopyOfResquest(o_productTestDataRequst.getSendCopyOfResquest());
		return productTestDataRequst;
	}

	@Override
	public List<Map<String, Object>> getStandardsBasedOnCategory(Integer regionId, Integer productCategoryId) {
		List<Map<String, Object>> standardsList = askDataDAO.getStandardsBasedOnCategory(regionId, productCategoryId);
		
		/*for(Map<String, Object> standardsMap : standardsList) {
			Integer standardId = standardsMap.get("standardId") != null ? Integer.parseInt(standardsMap.get("standardId").toString()) : null;
			String standardName = standardsMap.get("standardName") != null ? standardsMap.get("standardName").toString() : null;
			Boolean isDefault = standardsMap.get("isDefault") != null ? Boolean.parseBoolean(standardsMap.get("isDefault").toString()) : null;
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("standardId", standardId);
			jsonObject.addProperty("standardName", standardName);
			jsonObject.addProperty("isDefault", isDefault);
			standardsJSON.add(jsonObject);
		}*/
		
		return standardsList;
	}

	@Override
	public ProductDetailsVO getTestResultsForProductByStandard(List<TestCategoryVO> categoryVOs, String productId) {
		for (TestCategoryVO testCategoryVO : categoryVOs) {
			List<TestResultsVO> resultsVOs = testCategoryVO.getTestResultsVOs();
			for (TestResultsVO testVo : resultsVOs) {
				List<String> testsList = new ArrayList<>();
				testsList.add(testVo.getTestId());
				Map<String, String> testResultsMap = askDataDAO.getTestResultsValuesForProduct(testsList, productId);
				String testValue = testResultsMap.get(testVo.getTestId()) != null ? testResultsMap.get(testVo.getTestId()) : "";
				testVo.setTestValue(testValue);
			}
		}
		
		ProductDetailsVO detailsVO = new ProductDetailsVO();
		detailsVO.setProductId(productId);
		detailsVO.setTestCategoryVOs(categoryVOs);
		return detailsVO;
	}

	
}
