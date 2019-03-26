package com.ansell.ask.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PropertyMasterVO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5239142421270083603L;

	private String ownerType = null;

    private String ownerId = null;
	
    private String propertyName = null;
	
    private String propertyValue = null;

    private Integer isDeleted = null;
    
    private Date lastModifiedDate = null;

    private String modifiedBy = null;

    private BigDecimal appVersion = null;

    public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public BigDecimal getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(BigDecimal appVersion) {
		this.appVersion = appVersion;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	private String comments = null; 
    
	public String getOwnerType()
	{
		return ownerType;
	}

	public void setOwnerType(String ownerType)
	{
		this.ownerType = ownerType;
	}

	public String getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(String ownerId)
	{
		this.ownerId = ownerId;
	}

	public String getPropertyName()
	{
		return propertyName;
	}

	public void setPropertyName(String propertyName)
	{
		this.propertyName = propertyName;
	}

	public String getPropertyValue()
	{
		return propertyValue;
	}

	public void setPropertyValue(String propertyValue)
	{
		this.propertyValue = propertyValue;
	}

	public Integer getIsDeleted()
	{
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted)
	{
		this.isDeleted = isDeleted;
	}

}