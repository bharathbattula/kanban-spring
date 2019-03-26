package com.ansell.ask.vo;

import java.util.List;

public class TestCategoryVO {

	private String testCategoryId = null;
	private String testCategroyName = null;
	private List<TestResultsVO> testResultsVOs = null;
	
	/**
	 * @return the testCategoryId
	 */
	public String getTestCategoryId() {
		return testCategoryId;
	}
	/**
	 * @param testCategoryId the testCategoryId to set
	 */
	public void setTestCategoryId(String testCategoryId) {
		this.testCategoryId = testCategoryId;
	}
	/**
	 * @return the testCategroyName
	 */
	public String getTestCategroyName() {
		return testCategroyName;
	}
	/**
	 * @param testCategroyName the testCategroyName to set
	 */
	public void setTestCategroyName(String testCategroyName) {
		this.testCategroyName = testCategroyName;
	}
	/**
	 * @return the testResultsVOs
	 */
	public List<TestResultsVO> getTestResultsVOs() {
		return testResultsVOs;
	}
	/**
	 * @param testResultsVOs the testResultsVOs to set
	 */
	public void setTestResultsVOs(List<TestResultsVO> testResultsVOs) {
		this.testResultsVOs = testResultsVOs;
	}
	
}
