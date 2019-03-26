package com.ansell.ask.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductTestRequestVO {
	
	private Map<String, List<String>> productTestsMap = new HashMap<>();
	
	private OtherRequestInfoVO otherRequestInfoVO = null;

	public Map<String, List<String>> getProductTestsMap() {
		return productTestsMap;
	}

	public void setProductTestsMap(Map<String, List<String>> productTestsMap) {
		this.productTestsMap = productTestsMap;
	}

	public OtherRequestInfoVO getOtherRequestInfoVO() {
		return otherRequestInfoVO;
	}

	public void setOtherRequestInfoVO(OtherRequestInfoVO otherRequestInfoVO) {
		this.otherRequestInfoVO = otherRequestInfoVO;
	}
	

}
