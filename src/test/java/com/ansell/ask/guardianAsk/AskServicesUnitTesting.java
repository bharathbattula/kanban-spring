package com.ansell.ask.guardianAsk;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ansell.ask.common.RequestProductVO;
import com.ansell.ask.service.interfaces.IASKService;
import com.ansell.ask.service.interfaces.IAskCommonsService;
import com.ansell.ask.service.interfaces.IGenericGridsService;

@RunWith(SpringJUnit4ClassRunner.class)
public class AskServicesUnitTesting {
	
	@Autowired
	private IASKService askService = null;

	@Autowired
	private IAskCommonsService askCommonsService;
	
	@Autowired
	private IGenericGridsService genericGridsService;

	@Before
	public void setUp(){
		RequestProductVO requestProductVO = new RequestProductVO();
				/*requestProductVO.setAdditionalInfo("Testing Addtional Info");
				requestProductVO.setCopyOfResponse(1);
				requestProductVO.setIsBenchmarktestingRequired("1");
				requestProductVO.setOtherProductName("New Product Test 1");
				requestProductVO.setOtherProductStyleName("New Product Style 1");
				requestProductVO.setOthProdBrand(othProdBrand);
				requestProductVO.setOthProdManufacturer(othProdManufacturer);
				requestProductVO.setProductCategoryId(productCategoryId);
				requestProductVO.setProductManufacturerId(productManufacturerId);
				requestProductVO.setRegionScopeSelected(regionScopeSelected);
				requestProductVO.setSpecificCustomerName(specificCustomerName);
				requestProductVO.setSupportsProcurement(supportsProcurement);
				requestProductVO.setTestResultsNeededByDate(testResultsNeededByDate);
				requestProductVO.setTestToPerform(testToPerform);
				requestProductVO.setUrgencyLevel(urgencyLevel);*/
			
	}
	
	/*RequestProductVO requestProductVO = new RequestProductVO() {
		{
		address("Herve", "4650", "Rue de la gare", "1", null, "Belgium");
		credentials("john", "secret");
		name("John", "Doe");
		}
		}.build(true);
		Mockito.when(accountRepository.findByUsername("john")).thenReturn(account);
		}*/
}
