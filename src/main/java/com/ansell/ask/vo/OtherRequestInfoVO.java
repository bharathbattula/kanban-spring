package com.ansell.ask.vo;

import java.sql.Date;

public class OtherRequestInfoVO {
	
	private boolean isBenchmarktestingRequiredForCompetitiveBid = false;
	private boolean isBenchmarktestingRequiredForSpecificCustomer = false;
	private String specificCustomerName = null;
	private String urgencyLevel = null;
	private boolean supportSampleProcurement = false;
	private Date testResultsNeededByDate = null;
	private String linkedOppurnity = null;
	private boolean copyOfResponse = false;
	
	
	public boolean isBenchmarktestingRequiredForCompetitiveBid() {
		return isBenchmarktestingRequiredForCompetitiveBid;
	}
	public void setBenchmarktestingRequiredForCompetitiveBid(boolean isBenchmarktestingRequiredForCompetitiveBid) {
		this.isBenchmarktestingRequiredForCompetitiveBid = isBenchmarktestingRequiredForCompetitiveBid;
	}
	public boolean isBenchmarktestingRequiredForSpecificCustomer() {
		return isBenchmarktestingRequiredForSpecificCustomer;
	}
	public void setBenchmarktestingRequiredForSpecificCustomer(boolean isBenchmarktestingRequiredForSpecificCustomer) {
		this.isBenchmarktestingRequiredForSpecificCustomer = isBenchmarktestingRequiredForSpecificCustomer;
	}
	public String getSpecificCustomerName() {
		return specificCustomerName;
	}
	public void setSpecificCustomerName(String specificCustomerName) {
		this.specificCustomerName = specificCustomerName;
	}
	public String getUrgencyLevel() {
		return urgencyLevel;
	}
	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}
	public boolean isSupportSampleProcurement() {
		return supportSampleProcurement;
	}
	public void setSupportSampleProcurement(boolean supportSampleProcurement) {
		this.supportSampleProcurement = supportSampleProcurement;
	}
	public Date getTestResultsNeededByDate() {
		return testResultsNeededByDate;
	}
	public void setTestResultsNeededByDate(Date testResultsNeededByDate) {
		this.testResultsNeededByDate = testResultsNeededByDate;
	}
	public String getLinkedOppurnity() {
		return linkedOppurnity;
	}
	public void setLinkedOppurnity(String linkedOppurnity) {
		this.linkedOppurnity = linkedOppurnity;
	}
	public boolean isCopyOfResponse() {
		return copyOfResponse;
	}
	public void setCopyOfResponse(boolean copyOfResponse) {
		this.copyOfResponse = copyOfResponse;
	}
	
	
}
