package com.ansell.ask.vo;

import java.util.Date;

import com.ansell.ask.common.Utility;

public class ProductTestDataRequst {
	
	private String productId = null;

	private String testId = null;

	private String requestId = null;

	private String linkToOpuurnity = null;

	private int status = 1;

	private int isbenchmarkTestingRequired = 1;

	private int isBenchmarkTestingRequiredForCustomer = 1;

	private int isSupportSampleProcurement = 1;

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	private int urgency = 1;

	private int sendCopyOfResquest = 1;

	private Date whenTestResultNeeded = null;

	private Date lastUpdatedDt = null;

	private String updatedBy = null;

	private String additionalInfo = null;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getTestId() {
		return testId;
	}

	public void setTestId(String testId) {
		this.testId = testId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getLinkToOpuurnity() {
		return linkToOpuurnity;
	}

	public void setLinkToOpuurnity(String linkToOpuurnity) {
		this.linkToOpuurnity = linkToOpuurnity;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getIsbenchmarkTestingRequired() {
		return isbenchmarkTestingRequired;
	}

	public void setIsbenchmarkTestingRequired(int isbenchmarkTestingRequired) {
		this.isbenchmarkTestingRequired = isbenchmarkTestingRequired;
	}

	public int getIsBenchmarkTestingRequiredForCustomer() {
		return isBenchmarkTestingRequiredForCustomer;
	}

	public void setIsBenchmarkTestingRequiredForCustomer(int isBenchmarkTestingRequiredForCustomer) {
		this.isBenchmarkTestingRequiredForCustomer = isBenchmarkTestingRequiredForCustomer;
	}

	public int getIsSupportSampleProcurement() {
		return isSupportSampleProcurement;
	}

	public void setIsSupportSampleProcurement(int isSupportSampleProcurement) {
		this.isSupportSampleProcurement = isSupportSampleProcurement;
	}

	public int getUrgency() {
		return urgency;
	}

	public void setUrgency(int urgency) {
		this.urgency = urgency;
	}

	public int getSendCopyOfResquest() {
		return sendCopyOfResquest;
	}

	public void setSendCopyOfResquest(int sendCopyOfResquest) {
		this.sendCopyOfResquest = sendCopyOfResquest;
	}

	public Date getWhenTestResultNeeded() {
		return whenTestResultNeeded;
	}

	public void setWhenTestResultNeeded(Date whenTestResultNeeded) {
		this.whenTestResultNeeded = whenTestResultNeeded;
	}

	public Date getLastUpdatedDt() {
		return lastUpdatedDt;
	}

	public void setLastUpdatedDt(Date lastUpdatedDt) {
		this.lastUpdatedDt = lastUpdatedDt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

}
