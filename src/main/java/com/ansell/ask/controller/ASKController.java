
/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Ltd
 * 
 * Developed by: Trigyn Technologies Limited.
 * ASKController.java
 * Created on 8-Feb-2019
 */
package com.ansell.ask.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ansell.ask.common.AskSessionUser;
import com.ansell.ask.common.IConstant;
import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.service.interfaces.IASKService;
import com.ansell.ask.service.interfaces.IAskCommonsService;
import com.ansell.ask.service.interfaces.IGenericGridsService;
import com.ansell.ask.util.grid.CustomGridResponse;
import com.ansell.ask.util.grid.GenericGridParams;
import com.ansell.ask.util.grid.GridResponse;
import com.ansell.ask.vo.ProductDetailsVO;
import com.ansell.ask.vo.ProductTestResultsVO;
import com.ansell.ask.vo.TestCategoryVO;
import com.google.gson.JsonObject;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
@Controller
@SessionAttributes("askSessionUser")
public class ASKController {

	private static Logger gtLogger = LoggerFactory.getLogger(ASKController.class);

	@Autowired
	private IASKService askService = null;

	@Autowired
	private IAskCommonsService askCommonsService;

	@Autowired
	private IGenericGridsService genericGridsService;

	@ModelAttribute("askSessionUser")
	public AskSessionUser getAskSessionUser() {
		return new AskSessionUser();
	}

	@GetMapping("/welcome")
	public ModelAndView welcomePage(HttpSession session, HttpServletRequest request,
			@ModelAttribute("askSessionUser") AskSessionUser askSessionUser) throws Exception {
		gtLogger.info("Welcome Page Session User " + askSessionUser);

		ModelAndView mav = new ModelAndView("welcomePage.tile");
		mav.addObject("languageName", askCommonsService.getLanguageName(askSessionUser.getLanguageId()));
		return mav;
	}

	@GetMapping("/askContainer")
	public ModelAndView askContainer(HttpSession session, HttpServletResponse response, HttpServletRequest request,
			@ModelAttribute("askSessionUser") AskSessionUser askSessionUser) throws Exception {
		gtLogger.info("Ask Container Page Session User " + askSessionUser);
		if (askSessionUser.getUserId() == null) {
			response.sendError(403, "Hii");
		}
		ModelAndView mav = new ModelAndView("askContainer.tile");
		mav.addObject("languageName", askCommonsService.getLanguageName(askSessionUser.getLanguageId()));
		mav.addObject(IConstant.ASk_JS_DATE_FORMAT, System.getProperty(IConstant.ASk_JS_DATE_FORMAT));
		return mav;
	}

	@RequestMapping("/specificationTestResults")
	public ModelAndView specificationTestResults(HttpSession session, HttpServletRequest request) throws Exception {
		ModelAndView mav = new ModelAndView("comparisionTable");
		Integer productCategoryId = Integer.parseInt(request.getParameter("productCategoryId"));
		AskSessionUser askSessionUser = (AskSessionUser) session.getAttribute("askSessionUser");
		Integer regionId = askSessionUser.getRegionId();
		List<Map<String, Object>> standardsList = askService.getStandardsBasedOnCategory(regionId, productCategoryId);
		Integer defaultstandardId = null;
		for (Map<String, Object> standardsMap : standardsList) {
			Integer standardId = standardsMap.get("standardId") != null
					? Integer.parseInt(standardsMap.get("standardId").toString()) : null;
			Boolean isDefault = standardsMap.get("isDefault") != null
					? Boolean.parseBoolean(standardsMap.get("isDefault").toString()) : null;
					
			if (isDefault) {
				defaultstandardId = standardId;
				break;
			}
		}
		mav.addObject("standardsList", standardsList);
		mav.addObject("productSpecs", askService.getSpecs(productCategoryId));
		mav.addObject("testResults", askService.getTestResults(defaultstandardId, regionId));
		return mav;
	}

	@RequestMapping(value = "getProductDetails", method = RequestMethod.GET)
	@ResponseBody
	public ProductDetailsVO getProductData(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("productSpecsIds[]") List<String> productSpecsIds,
			@RequestParam("testResultsIds[]") List<String> testResultsIds) throws IOException {
		String productId = request.getParameter("productId");
		String productName = request.getParameter("productName");
		if (productSpecsIds.contains("0")) {
			productSpecsIds = new ArrayList<String>();
		}
		if (testResultsIds.contains("0")) {
			testResultsIds = new ArrayList<String>();
		}

		Map<String, List<String>> map = new HashMap<>();
		for (String testResultId : testResultsIds) {
			String[] testResults = testResultId.split("::@::");
			if (map.containsKey(testResults[1])) {
				List<String> testResultIdsList = map.get(testResults[1]);
				testResultIdsList.add(testResults[0]);
			} else {
				List<String> testResultIdsList = new ArrayList<String>();
				testResultIdsList.add(testResults[0]);
				map.put(testResults[1], testResultIdsList);
			}
		}
		ProductDetailsVO productDetailsVO = askService.getProductDetails(productId, productSpecsIds, map, productName);
		return productDetailsVO;
	}

	@RequestMapping("/requestProduct")
	public ModelAndView openProductCurrentAddContainer(HttpSession httpSession, HttpServletRequest request,
			@RequestParam(value = "regionId") Integer regionId,
			@ModelAttribute("askSessionUser") AskSessionUser askSessionUser) throws Exception {

		gtLogger.info("requestProduct :" + askSessionUser);
		ModelAndView mav = new ModelAndView("requestProduct");

		Integer languageId = askSessionUser.getLanguageId();
		regionId = askSessionUser.getRegionId();

		List<Map<String, Object>> scopeRegion = askCommonsService.getAllRegions();
		List<JsonObject> jsonRegionList = new ArrayList<JsonObject>();
		Iterator<Map<String, Object>> iterator = scopeRegion.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> element = iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("region_id") != null && element.get("region_id").equals(regionId)) {
				mav.addObject("selectedRegionId", element.get("region_id"));
				mav.addObject("selectedRegionName", element.get("region_name"));
			}
			jsonObject.addProperty("id", (Integer) element.get("region_id"));
			jsonObject.addProperty("label", (String) element.get("region_name"));
			jsonRegionList.add(jsonObject);

		}

		List<Map<String, Object>> lstProdCatagory = askCommonsService.getAllProductCategories();
		List<JsonObject> jsonProdCategoryList = new ArrayList<JsonObject>();
		Iterator<Map<String, Object>> c_iterator = lstProdCatagory.iterator();
		while (c_iterator.hasNext()) {
			Map<String, Object> element = c_iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("product_category_id") != null
					&& element.get("product_category_id").equals(IConstant.HAND_PROTECTION)) {
				mav.addObject("selectedCategoryId", element.get("product_category_id"));
				mav.addObject("selectedCategoryName", element.get("product_category_name"));
			}

			jsonObject.addProperty("id", (Integer) element.get("product_category_id"));
			jsonObject.addProperty("label", (String) element.get("product_category_name"));
			jsonProdCategoryList.add(jsonObject);
		}

		List<Map<String, Object>> lstProdManufacturer1 = askCommonsService.getAllManufacturers();
		List<JsonObject> listProdManufacturer = new ArrayList<JsonObject>();
		Iterator<Map<String, Object>> m_iterator = lstProdManufacturer1.iterator();
		while (m_iterator.hasNext()) {
			Map<String, Object> element = m_iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("product_manufacturer_id") != null
					&& (element.get("product_manufacturer_id")).equals(IConstant.OTHER_PRODUCT_MANUFACTURER_ID)) {
				mav.addObject("selectedManufacturerId", element.get("product_manufacturer_id"));
				mav.addObject("selectedManufacturerIdName", element.get("manufacturer"));
			}
			jsonObject.addProperty("id", (String) element.get("product_manufacturer_id"));
			jsonObject.addProperty("label", (String) element.get("manufacturer"));
			listProdManufacturer.add(jsonObject);

		}

		List<Map<String, Object>> lstProductBrand1 = askCommonsService.getAllBrands();
		List<JsonObject> lstProductBrand = new ArrayList<JsonObject>();
		Iterator<Map<String, Object>> b_iterator = lstProductBrand1.iterator();
		while (b_iterator.hasNext()) {
			Map<String, Object> element = b_iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("product_brand_id") != null
					&& (element.get("product_brand_id")).equals(IConstant.OTHER_PRODUCT_BRAND_ID)) {
				mav.addObject("selectedBrandId", element.get("product_brand_id"));
				mav.addObject("selectedBrandName", element.get("product_brand_name"));
			}
			jsonObject.addProperty("id", (String) element.get("product_brand_id"));
			jsonObject.addProperty("label", (String) element.get("product_brand_name"));
			lstProductBrand.add(jsonObject);

		}

		List<Map<String, Object>> urgencyLevels = askCommonsService.getUrgencyLevels();
		List<JsonObject> urgencyLevelsList = new ArrayList<JsonObject>();
		Iterator<Map<String, Object>> u_iterator = urgencyLevels.iterator();
		while (u_iterator.hasNext()) {
			Map<String, Object> element = u_iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("record_id") != null
					&& (element.get("record_id")).equals(IConstant.DEFAULT_URGENCY_LEVEL)) {
				mav.addObject("record_id", element.get("record_id"));
				mav.addObject("record_description", element.get("record_description"));
			}
			jsonObject.addProperty("id", (Integer) element.get("record_id"));
			jsonObject.addProperty("label", (String) element.get("record_description"));
			urgencyLevelsList.add(jsonObject);

		}

		mav.addObject("languageId", languageId);
		mav.addObject("jsonRegionList", jsonRegionList);
		mav.addObject("jsonProdCategoryList", jsonProdCategoryList);
		mav.addObject("listProdManufacturer", listProdManufacturer);
		mav.addObject("lstProductBrand", lstProductBrand);
		mav.addObject("urgencyLevelsList", urgencyLevelsList);

		return mav;
	}

	
	@RequestMapping(value="openTest", method=RequestMethod.GET)
	public ModelAndView openTest(HttpServletRequest request, HttpServletResponse response,HttpSession session,
			@RequestParam(name = "dashboardtestIdArray") String[] dashboardtestIdArray) {		
		
		gtLogger.info("Inside openTest Method in ASKController Class"+dashboardtestIdArray.length);
		ObjectMapper objectMapper = new ObjectMapper();
		ModelAndView mav = new ModelAndView("askTests");
		String arrayToJson ="";
		String productCategory = request.getParameter("comparisonChartCategoryId");
		AskSessionUser askSessionUser = (AskSessionUser) session.getAttribute("askSessionUser");
		Integer regionId = askSessionUser.getRegionId();
		try {
			arrayToJson = objectMapper.writeValueAsString(dashboardtestIdArray);			
		} catch (Exception e) {
			gtLogger.error("Error inside openTest method in ASKController Class" + e.getMessage());
		}
		mav.addObject("dashboardtestIdArray",arrayToJson);
		mav.addObject("regionId",regionId);
		mav.addObject("productCategory", productCategory);
		//mav.addObject("standardId",standardIds);
		return mav;
	}

	@RequestMapping("/submitNewProductRequest")
	public ResponseEntity<String> submitNewProductRequest(HttpSession httpSession, HttpServletRequest request,
			RequestProductVO requestProductVO, @ModelAttribute("askSessionUser") AskSessionUser askSessionUser)
			throws Exception {
		try {
			gtLogger.info("submitNewProductRequest :");
			String generatedRequestId = askCommonsService.saveOtherProduct(requestProductVO, askSessionUser);
			return ResponseEntity.ok(generatedRequestId);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
		}
	}

	@RequestMapping(value = "/loadGridData")
	@ResponseBody
	public CustomGridResponse loadGridData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String gridId = request.getParameter("gridId");
		GenericGridParams gridParams = new GenericGridParams(request);
		Integer matchingRowCount = genericGridsService.findCount(gridId, gridParams);
		List<Map<String, String>> list = genericGridsService.findAllRecords(gridId, gridParams);
		GridResponse gridResponse = new GridResponse(list, matchingRowCount, gridParams);
		return gridResponse.getResponse();
	}

	@RequestMapping(value = "/getTestResultsForSelectedProducts")
	@ResponseBody
	public List<ProductTestResultsVO> getTestResultsForSelectedProducts(HttpServletRequest request,
			HttpServletResponse response,@RequestParam("productIds[]") List<String> productIds) throws Exception {

		List<ProductTestResultsVO> l_dataList = new ArrayList<ProductTestResultsVO>();		
		String testId = request.getParameter("testId");
		String testStandardId = request.getParameter("testStandardId");
		List<String> requestedProductIds = productIds;
		l_dataList = askService.getTestResultsForSelectedProducts(testId,testStandardId,requestedProductIds);
		return l_dataList;
	}

	@RequestMapping(value = "/requestTestsForProduct")
	@ResponseBody 
	public ModelAndView requestTestsForProduct(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("testRequestsForProducts");
		Map<String,String[]> m_testProductNameMap = (Map<String,String[]>) request.getParameterMap();
		 ObjectMapper mapper = new ObjectMapper();
		 List<Map<String, Object>> urgencyLevels = askCommonsService.getUrgencyLevels();
		 List<JsonObject> urgencyLevelsList = new ArrayList<JsonObject>();
		 Iterator<Map<String, Object>> u_iterator = urgencyLevels.iterator();
		
		 while (u_iterator.hasNext()) {
			Map<String, Object> element = u_iterator.next();
			JsonObject jsonObject = new JsonObject();
			if (element.get("record_id") != null
					&& (element.get("record_id")).equals(IConstant.DEFAULT_URGENCY_LEVEL)) {
				mav.addObject("record_id", element.get("record_id"));
				mav.addObject("record_description", element.get("record_description"));
			}
			jsonObject.addProperty("id", (Integer) element.get("record_id"));
			jsonObject.addProperty("label", (String) element.get("record_description"));
			urgencyLevelsList.add(jsonObject);
		}

		mav.addObject("urgencyLevelsList", urgencyLevelsList);
		mav.addObject("testProductNameMap", mapper.writeValueAsString(m_testProductNameMap));
		return mav;
	}

	@RequestMapping("/checkRequestedProductAlreadyExists")
	public ResponseEntity<String> checkRequestedProductAlreadyExists(HttpServletRequest request,
			@RequestParam("productName") String productName, @RequestParam("regionId") String regionId,@RequestParam("prodManufacturer") String prodManufacturer,@RequestParam("prodBrand") String prodBrand) {
		try {
			gtLogger.info("checkRequestedProductAlreadyExists :");
			String generatedRequestId = askCommonsService.getRequestedProductId(productName, regionId,prodManufacturer,prodBrand);
			return ResponseEntity.ok(generatedRequestId);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
		}
	}

	@RequestMapping(value = "/submitTestRequestsForProduct", method = RequestMethod.POST)
	public String submitTestRequestsForProduct(@RequestBody RequestProductVO productVO) throws Exception {
		try {
			askService.createProductTestRequestData(productVO);
			return "SUCCESS";
		} catch (Exception ex) {
			ex.printStackTrace();
			return ex.getMessage();
		}
	}

	@RequestMapping(value = "/getTestsBasedOnStandardId", method = RequestMethod.GET)
	@ResponseBody
	public List<ProductDetailsVO> getTestBasedOnStandardId(HttpServletRequest request, HttpSession session,
			@RequestParam(name = "standardId") Integer standardId,
			@RequestParam(name = "productIds[]") List<String> productIds) {
		List<TestCategoryVO> categoryVOs = new ArrayList<TestCategoryVO>();
		AskSessionUser askSessionUser = (AskSessionUser) session.getAttribute("askSessionUser");
		List<ProductDetailsVO> detailsVOs = new LinkedList<ProductDetailsVO>();
		Integer regionId = askSessionUser.getRegionId();
		categoryVOs = askService.getTestResults(standardId, regionId);
		for (String productId : productIds) {
			ProductDetailsVO detailsVO = askService.getTestResultsForProductByStandard(categoryVOs, productId);
			detailsVOs.add(detailsVO);
		}

		return detailsVOs;
	}

}
