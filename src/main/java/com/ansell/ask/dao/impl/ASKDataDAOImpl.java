package com.ansell.ask.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ansell.ask.common.IConstant;
import com.ansell.ask.dao.interfaces.IASKDataDAO;
import com.ansell.ask.vo.ProductTestDataRequst;
import com.ansell.ask.vo.ProductTestResultsVO;

@Repository
public class ASKDataDAOImpl implements IASKDataDAO {

	private static Logger gtLogger = LoggerFactory.getLogger(ASKDataDAOImpl.class);

	@Autowired
	private NamedParameterJdbcTemplate namedParameterjdbcTemplate = null;

	@Autowired
	private JdbcTemplate jdbcTemplate = null;

	private static final StringBuilder selectProductSpecificationQuery = new StringBuilder(
			"select ips.specification_id as specificationId, ipsi18n.specification_name as specificationName"
					+ " from int_product_specification as ips "
					+ " LEFT OUTER JOIN int_product_specification_i18n as ipsi18n ON ips.specification_id = ipsi18n.specification_id  and ipsi18n.language_id= :defaultLanguageId"
					+ " where ips.product_category_id= :productCategoryId;");

	private static final StringBuilder selectproductTestsQuery = new StringBuilder(
			"SELECT CONCAT(itsa.test_standard_id, '::@@::', it.test_category_id) as concatString, COALESCE(CONCAT(iti18n.test_name,' (',cs.name,')'), (SELECT CONCAT(subTesti18n.test_name,' (',cs.name,')') FROM int_test_i18n subTesti18n WHERE subTesti18n.test_id= it.test_id AND subTesti18n.language_id= :defaultLanguageId), 'TRANSLATION MISSING') as testName FROM int_test it " + 
			"  LEFT OUTER JOIN int_test_i18n iti18n ON it.test_id = iti18n.test_id AND iti18n.language_id= :languageId " + 
			"  INNER JOIN int_test_region_assoc itra ON itra.test_id = it.test_id AND itra.region_id= :regionId " + 
			"  INNER JOIN int_test_standard_assoc itsa ON it.test_id = itsa.test_id AND itsa.standard_id= :standardId " + 
			"  INNER JOIN crms_standard cs ON cs.standard_id = itsa.standard_id " + 
			" WHERE it.is_deleted= :isDeleted AND it.is_default=:isDefaultTest AND itsa.is_deleted= :isDeleted AND itra.is_deleted= :isDeleted ORDER BY testName ASC");

	private static final String PRODUCT_SPECIFICATION_VALUES_FOR_PRODUCT_QUERY = "SELECT specification_id as specId, product_specification_value as value FROM int_product_specification_value "
			+ "WHERE specification_id IN (:productSpecificationIds) AND product_id= :productId";

	
	private static final String LAB_TEST_VALUES_FOR_PRODUCT_QUERY = "SELECT test_standard_id as testId, value as value FROM int_product_test_values "
			+ "WHERE test_standard_id IN (:testIds) AND product_id= :productId";

	private static final String LAB_TEST_CATEGORY_QUERY = "SELECT tc.test_category_id as testCategoryId, tci18n.test_category_name as testcategoryName  FROM int_test_category tc "
			+ " LEFT OUTER JOIN int_test_category_i18n tci18n ON tc.test_category_id = tci18n.test_category_id AND tci18n.language_id= :defaultLanguageId "
			+ " WHERE tc.is_deleted= :isDeleted";
	
//	private static final String GET_MAX_TEST_REQ_ID = "SELECT IFNULL(MAX(SUBSTRING(request_id, 13)),0) from int_product_test_data_request";

		
	private static final String LAB_TEST_HIGHLIGHT_LOGIC = " select test.test_highlight_logic as highlightLogic from int_test  test,int_test_standard_assoc its"
															+ " where test.test_id = its.test_id and its.test_standard_id = :testId";

	private static final String GET_MAX_TEST_REQ_ID = "SELECT count(*) FROM int_product_test_data_request";
	
	private static final String STANDARDS_QUERY = "SELECT cs.standard_id as standardId, cs.name as standardName, crsc.is_default as isDefault FROM crms_standard cs "+ 
			  " LEFT OUTER JOIN int_product_category_standard_ask_assoc ipcsaa ON cs.standard_id = ipcsaa.standard_id AND ipcsaa.is_deleted= :isDeleted "+
			  " LEFT OUTER JOIN crms_region_standard_association crsc ON crsc.standard_id = cs.standard_id "+
			  " WHERE ipcsaa.product_category_id= :categoryId AND crsc.region_id= :regionId";
	
	private static final String QUERY_GET_TEST_RESULTS_PRODUCTS = " SELECT value from int_product_test_values where " +
																  " product_id = :productId and test_standard_id = :testStandardId" ;
	
	@Override
	public String getTestHighlightLogicValue(String testId) throws Exception{
		String highlightLogic = "";
		try{
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			paramsMap.put("testId", testId);

			highlightLogic = namedParameterjdbcTemplate
					.queryForObject(selectproductTestsQuery.toString(), paramsMap,String.class);
		}
		catch(Exception ex){
			gtLogger.error("Exception in getTestHighlightLogicValue method"+ex);;
		}
		return highlightLogic;
	}
	
	
	
	@Override
	public List<ProductTestResultsVO> getTestResultsForSelectedProducts(List<String> l_productIds,String testStandardId,String testId)
			throws Exception {
		List<ProductTestResultsVO> l_testList = null;
		List<String> l_testResult = null;
		String value = "";		
		try {
			l_testList = new ArrayList<ProductTestResultsVO>();
			l_testResult = new ArrayList<String>();			
			for(String productId : l_productIds){
				SqlParameterSource namedParameters = new MapSqlParameterSource("productId",productId)
						.addValue("testStandardId", testStandardId);	
				
				l_testResult = namedParameterjdbcTemplate.queryForList(QUERY_GET_TEST_RESULTS_PRODUCTS, namedParameters,String.class);
				ProductTestResultsVO o_productTestResultsVO = new  ProductTestResultsVO();
				o_productTestResultsVO.setTestId(testId);
				o_productTestResultsVO.setProductId(productId);
				if(!CollectionUtils.isEmpty(l_testResult)){
					value = l_testResult.get(0);
				}else{
					value = "";
				}
				o_productTestResultsVO.setTestResult(value);				
				l_testList.add(o_productTestResultsVO);
				
			}
		} catch (Exception ex) {
			gtLogger.error("error in getTestResultsForSelectedProducts()" + ex);
		}
		return l_testList;
	}

	@Override
	public void insertProductTestRequestData(List<ProductTestDataRequst> l_productTestDataRequst) {		
		try{
			String query = "insert into int_product_test_data_request(product_id,test_standard_id,request_id,status,link_to_opportunity,is_benchmark_testing_required,when_test_results_needed,last_updated_ts) values " 
						+  "(?,?,?,?,?,?,?,?) " ;
			List<Object[]> inputList = new ArrayList<Object[]>();
			
			for(ProductTestDataRequst o_productTestDataRequst:l_productTestDataRequst){				
				Object[] productTestDataRequst = {o_productTestDataRequst.getProductId(),
						o_productTestDataRequst.getTestId(), o_productTestDataRequst.getRequestId(), o_productTestDataRequst.getStatus(),
						o_productTestDataRequst.getLinkToOpuurnity(),o_productTestDataRequst.getIsbenchmarkTestingRequired(),o_productTestDataRequst.getWhenTestResultNeeded(),
						o_productTestDataRequst.getLastUpdatedDt()};
				inputList.add(productTestDataRequst);				
			}
			jdbcTemplate.batchUpdate(query, inputList); 
		}
		catch(Exception ex){
			ex.printStackTrace();
			gtLogger.error("Exception in insertProductTestRequestData"+ex.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> getSpecs(int categoryId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("productCategoryId", categoryId);
		paramsMap.put("defaultLanguageId", IConstant.DEFAULT_LANGUAGE_ID);
		return namedParameterjdbcTemplate.queryForList(selectProductSpecificationQuery.toString(), paramsMap);
	}

	@Override
	public Map<String, Map<String, String>> getTestResults(int defaultStandardId, Integer regionId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("isDefaultTest", IConstant.DeafultRecordStatus.DEFAULT.getStatus());
		paramsMap.put("languageId", IConstant.DEFAULT_LANGUAGE_ID);
		paramsMap.put("defaultLanguageId", IConstant.DEFAULT_LANGUAGE_ID);
		paramsMap.put("regionId", regionId);
		paramsMap.put("standardId", defaultStandardId);
		paramsMap.put("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());

		List<Map<String, Object>> fetchedList = namedParameterjdbcTemplate
				.queryForList(selectproductTestsQuery.toString(), paramsMap);

		Map<String, Map<String, String>> returnedMap = new LinkedHashMap<String, Map<String, String>>();
		for (Map<String, Object> fetchedMap : fetchedList) {
			String testCategoryId = String.valueOf(fetchedMap.get("concatString"));
			String[] ids = testCategoryId.split(IConstant.DELIMETER);

			String testName = String.valueOf(fetchedMap.get("testName"));

			// Map<String, String> map = new HashMap<String, String>();
			if (returnedMap.containsKey(ids[1])) {
				Map<String, String> map = returnedMap.get(ids[1]);
				map.put(ids[0], testName);
			} else {
				Map<String, String> map = new LinkedHashMap<String, String>();
				map.put(ids[0], testName);
				returnedMap.put(ids[1], map);
			}

		}

		return returnedMap;
	}

	@Override
	public Map<String, String> getProductSpecificationValuesForProduct(List<String> productSpecIds, String productId) {
		Map<String, String> returnedMap = new HashMap<String, String>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("productSpecificationIds", productSpecIds);
		paramsMap.put("productId", productId);

		List<Map<String, Object>> productSpecificationValues = namedParameterjdbcTemplate
				.queryForList(PRODUCT_SPECIFICATION_VALUES_FOR_PRODUCT_QUERY, paramsMap);

		if (productSpecificationValues != null && productSpecificationValues.isEmpty() == Boolean.FALSE) {
			for (Map<String, Object> productSpecsValueMap : productSpecificationValues) {
				String specificationId = productSpecsValueMap.get("specId") != null
						? String.valueOf(productSpecsValueMap.get("specId"))
						: null;
				String specificationValue = productSpecsValueMap.get("value") != null
						? String.valueOf(productSpecsValueMap.get("value"))
						: "";

				returnedMap.put(specificationId, specificationValue);
			}
		} else {
			/* This is for empty specifications */
			for (String specId : productSpecIds) {
				returnedMap.put(specId, "");
			}
		}
		return returnedMap;
	}

	@Override
	public Map<String, String> getTestResultsValuesForProduct(List<String> testResultIdsList, String productId) {
		String testValueHLG = "";
		Map<String, String> returnedMap = new HashMap<String, String>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("testIds", testResultIdsList);
		paramsMap.put("productId", productId);

		List<Map<String, Object>> testResultsValues = namedParameterjdbcTemplate
				.queryForList(LAB_TEST_VALUES_FOR_PRODUCT_QUERY, paramsMap);

		if (testResultsValues != null && testResultsValues.isEmpty() == Boolean.FALSE) {
			for (Map<String, Object> testResultsMap : testResultsValues) {
				String testId = testResultsMap.get("testId") != null ? String.valueOf(testResultsMap.get("testId"))
						: null;
				String value = testResultsMap.get("value") != null ? String.valueOf(testResultsMap.get("value")) : "";			
				
				
				/*if(StringUtils.isNotEmpty(value)){
					testValueHLG = value.concat("@@").concat(highlightLogic);
				}*/
				
				//returnedMap.put(testId, testValueHLG);
				returnedMap.put(testId,value);
			}
		} else {
			/* This is for empty specifications */
			for (String testId : testResultIdsList) {
				returnedMap.put(testId, "");
			}
		}
		return returnedMap;
	}

	@Override
	public Map<String, String> getTestCategoryMap() {
		Map<String, String> returnedMap = new HashMap<String, String>();
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("defaultLanguageId", IConstant.DEFAULT_LANGUAGE_ID);
		paramsMap.put("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());

		List<Map<String, Object>> testCategoriesList = namedParameterjdbcTemplate.queryForList(LAB_TEST_CATEGORY_QUERY,
				paramsMap);

		if (testCategoriesList != null && testCategoriesList.isEmpty() == Boolean.FALSE) {
			for (Map<String, Object> testCategoryMap : testCategoriesList) {
				String testCategoryId = testCategoryMap.get("testCategoryId") != null
						? String.valueOf(testCategoryMap.get("testCategoryId"))
						: null;
				String testCategoryName = testCategoryMap.get("testcategoryName") != null
						? String.valueOf(testCategoryMap.get("testcategoryName"))
						: "";

				returnedMap.put(testCategoryId, testCategoryName);
			}
		}
		return returnedMap;
	}

	@Override
	public List<Map<String, Object>> getStandardsBasedOnCategory(Integer regionId, Integer productCategoryId) {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		paramsMap.put("regionId", regionId);
		paramsMap.put("isDeleted", IConstant.RecordStatus.INSERTED.getStatus());
		paramsMap.put("categoryId", productCategoryId);
		
		List<Map<String, Object>> standardsList = namedParameterjdbcTemplate.queryForList(STANDARDS_QUERY,paramsMap);
		return standardsList;
	}
	
	/*
	 * public Integer generateTestRequestId() { try { SqlParameterSource
	 * namedParameters = new MapSqlParameterSource(); Integer count =
	 * namedParameterjdbcTemplate.queryForObject(GET_MAX_TEST_REQ_ID,
	 * namedParameters, Integer.class); return count;
	 * 
	 * } catch (Exception e) { gtLogger.error("getMaxId() : " + e.getMessage());
	 * return 0; } }
	 */

	public synchronized String generateTestRequestId(int cnt) {
		
		String productTestRequestId = null;
		int productTestStandardRequestNo = IConstant.productTestStandardRequestNo + cnt;
		int productTestNo = productTestStandardRequestNo + jdbcTemplate.queryForObject(GET_MAX_TEST_REQ_ID, Integer.class);
		
		productTestRequestId = IConstant.REQUEST_ID_TOKEN_STRING+""+productTestNo;
		
		return productTestRequestId;		
	}

	@Override
	public String getHighlightInfo(String testId) throws Exception{
		String highlightInfo = "";
		try{
			SqlParameterSource namedParameters = new MapSqlParameterSource("testId",testId);					
			highlightInfo = namedParameterjdbcTemplate.queryForObject(LAB_TEST_HIGHLIGHT_LOGIC, namedParameters, String.class);
		}
		catch(Exception ex){
			gtLogger.error("Exception in getHighlightInfo method"+ex);
		}
		return highlightInfo;
	}
}
