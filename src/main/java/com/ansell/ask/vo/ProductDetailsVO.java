package com.ansell.ask.vo;

import java.util.List;

public class ProductDetailsVO {

	private String productId = null;
	private String productName = null;
	private String productImagePath = null;
	private List<ProductSpecificationVO> productSpecificationVOs = null;
	private List<TestCategoryVO> testCategoryVOs = null;
	
	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the productImagePath
	 */
	public String getProductImagePath() {
		return productImagePath;
	}
	/**
	 * @param productImagePath the productImagePath to set
	 */
	public void setProductImagePath(String productImagePath) {
		this.productImagePath = productImagePath;
	}
	/**
	 * @return the productSpecificationVOs
	 */
	public List<ProductSpecificationVO> getProductSpecificationVOs() {
		return productSpecificationVOs;
	}
	/**
	 * @param productSpecificationVOs the productSpecificationVOs to set
	 */
	public void setProductSpecificationVOs(List<ProductSpecificationVO> productSpecificationVOs) {
		this.productSpecificationVOs = productSpecificationVOs;
	}
	/**
	 * @return the testCategoryVOs
	 */
	public List<TestCategoryVO> getTestCategoryVOs() {
		return testCategoryVOs;
	}
	/**
	 * @param testCategoryVOs the testCategoryVOs to set
	 */
	public void setTestCategoryVOs(List<TestCategoryVO> testCategoryVOs) {
		this.testCategoryVOs = testCategoryVOs;
	}
	
	
}
