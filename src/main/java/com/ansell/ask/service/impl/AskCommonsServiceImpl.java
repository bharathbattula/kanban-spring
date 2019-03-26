package com.ansell.ask.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.ansell.ask.common.AskSessionUser;
import com.ansell.ask.common.IConstant;
import com.ansell.ask.common.IConstant.SendCopyOfResponseEmail;
import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.dao.interfaces.IAskCommonsDAO;
import com.ansell.ask.dao.interfaces.IPropertyReader;
import com.ansell.ask.service.interfaces.IAskCommonsService;

@Service
public class AskCommonsServiceImpl implements IAskCommonsService {

	@Autowired
	private IAskCommonsDAO askDao;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	@Autowired
	private IPropertyReader propertyReader;

	private static Logger gtLogger = LoggerFactory.getLogger(AskCommonsServiceImpl.class);
	
	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	@Override
	public List<Map<String, Object>> getAllRegions() {		
		return askDao.getAllRegions();
	}

	@Override
	public List<Map<String, Object>> getAllManufacturers() {	
		return askDao.getAllManufacturers();
	}

	@Override
	public List<Map<String, Object>> getAllBrands() {
		return askDao.getAllBrands();
	}

	@Override
	public List<Map<String, Object>> getAllProductCategories() {		
		return askDao.getAllProductCategories();
	}

	@Override
	public List<Map<String, Object>> getUrgencyLevels() {
		return askDao.getUrgencyLevels();
	}

	@Override
	@Transactional
	public String saveOtherProduct(RequestProductVO vo,AskSessionUser user) {			
			if(vo.getCopyOfResponse().equals(SendCopyOfResponseEmail.TRUE.getStatus()))
					saveEmailDetails(vo,user); // Send eMail ack
			
			return askDao.saveOtherProduct(vo,user.getEmail());
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getNewOtherProductEmailTemplate(RequestProductVO vo,AskSessionUser user) {		
		Map<String, Object> mapOfVariables =new HashMap<String, Object>();		
		mapOfVariables.put("name", user.getFirstName());
		mapOfVariables.put("pName", vo.getOtherProductName());
		mapOfVariables.put("pBrand", vo.getOthProdBrand());
		mapOfVariables.put("pCategory", vo.getProductCategoryId());
		mapOfVariables.put("pManufacturer", vo.getOthProdManufacturer());
				
		String divString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, IConstant.OTHER_PRODUCT_EMAIL_TEMPLATE_VM_FILE, mapOfVariables);		
		return divString;
		
	}
	
	@SuppressWarnings("deprecation")
	public String getEmailTemplateForTestDataForProducts(Map<String, Object> productTestIdsMap) {    
        Map<String,Object> velocityMap = new HashMap<String,Object>();        
        velocityMap.put("productTestIdsMap", productTestIdsMap);     
           
        String divString = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, IConstant.REQUEST_TEST_DATA_PRODUCT_EMAIL_TEMPLATE_VM_FILE, velocityMap);
		gtLogger.info("Email Template = "+divString);
		return divString;
	}

	@Override
	public int saveEmailDetails(Object vo, AskSessionUser user){
			String toUser   = 	propertyReader.getProperty(IConstant.ASK_EMAIL_TO);
			String fromUser =	propertyReader.getProperty(IConstant.ASK_EMAIL_FROM);
			String cc		=	propertyReader.getProperty(IConstant.ASK_EMAIL_CC);
			String bcc		=	propertyReader.getProperty(IConstant.ASK_EMAIL_BCC);
			String subject  =	propertyReader.getProperty(IConstant.ASK_EMAIL_SUBJECT);
			String body = null; 		
			if(vo instanceof RequestProductVO){
				body = this.getNewOtherProductEmailTemplate((RequestProductVO) vo,user);			
			}else if(vo instanceof Map){
				body= this.getEmailTemplateForTestDataForProducts((Map<String,Object>) vo);			
			}		
					
			if(toUser !=null && fromUser != null){
				return askDao.saveEmailDetails(toUser, fromUser, cc, bcc, subject, body);
			}else{
				gtLogger.error("saveEmailDetails : Email address not found in property master!");
				return 0;
			}				
	}


	@Override
	public Map<String, Object> getUserDetails(String userId) {		
		return askDao.getUserDetails(userId);
	}

	@Override
	public boolean isAskUser(String userId) {		
		return askDao.isAskUser(userId);
	}

	@Override
	public String getRequestedProductId(String productName,String regionId,String prodManufacturer,String prodBrand) {		
		return askDao.getRequestedProductId(productName,regionId,prodManufacturer,prodBrand);
	}

	@Override
	public String getLanguageName(Integer langId) {		
		return askDao.getLanguageName(langId);
	}
}

