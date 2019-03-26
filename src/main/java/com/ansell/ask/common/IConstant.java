/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited. IConstant.java Created on
 * 06-Feb-2019
 */
package com.ansell.ask.common;

/**
 * @author Sudhirkumarrao.Allada
 * @version $Revision$ Last changed by $Author$ on $Date$ as $Revision$
 */
public interface IConstant {

	int 						TOKEN_NOT_VALID_STATUS 												= 900;
	int 						ASK_APP_ID 															= 8;
	String 						DEFAULT_LOCALE 														= "en_US";
	String 						MODULE_NAME 														= "ask";
	String 						ASk_APP_VERSION_NAME 												= "askAppVersion";
	String 						ASk_JS_DATE_FORMAT 													= "dateFormatJS";
	int 						DEFAULT_LANGUAGE_ID 												= 1;
	String						RESOURCE_BUNDLE_HEADER_KEY											= "ask.header.js";
	
	Integer HAND_PROTECTION=2; // Set Default product category to hand protection
    Integer BODY_PROTECTION=105; // Set Default product category to Body protection
    Integer BODY_PROTECTION_OLD=110;// Set Default product category to Body protection Old
    Integer BODY_PROTECTION_LP_OLD=106; // Set Default product category to Body protection LP Old
    
    String OTHER_PRODUCT_MANUFACTURER_ID = "32";
    String OTHER_PRODUCT_BRAND_ID = "93";
	Integer APP_ID = 1;
	
	String URGENCY_CATEGORY="URGENCY";
	Integer DEFAULT_URGENCY_LEVEL = 1;
	
	String OTHER_PRODUCT_EMAIL_TEMPLATE_VM_FILE = "vmFiles/otherProductEmailTemplate.vm";
	String REQUEST_TEST_DATA_PRODUCT_EMAIL_TEMPLATE_VM_FILE = "vmFiles/requestForTestDataForProductsTemplate.vm";
	String DELIMETER = "::@@::";
	
	String ASK_EMAIL_TO 		=	"ask.newproduct.email.to";
	String ASK_EMAIL_FROM   	=   "ask.newproduct.email.from";
	String ASK_EMAIL_CC			=	"ask.newproduct.email.cc";
	String ASK_EMAIL_BCC		=	"ask.newproduct.email.bcc";
	String ASK_EMAIL_SUBJECT	=	"ask.newproduct.email.subject";
	String 	REQUEST_ID_TOKEN_STRING = "TEST_REQ_ID_";
	String DATE_FORMAT_PROPERTY_NAME = "dateFormat-JS";
	int productTestStandardRequestNo = 1000;
	
	 public enum queryImplementationType
	    {
	        VIEW(1), STORED_PROCEDURE(2);
	        final int type;

	        queryImplementationType(int i)
	        {
	        	type = i;
	        }

	        public int getType()
	        {
	            return type;
	        }
	    }
	public enum RecordStatus{
        DELETED(1), INSERTED(0), UPDATED(0), EDITED(2), NOTCHANGED(3);
        final int status;

        RecordStatus(int i)
        {
            status = i;
        }

        public int getStatus()
        {
            return status;
        }
    }
	
	public enum SendCopyOfResponseEmail{
        TRUE(1), FALSE(0);
        final int status;
        SendCopyOfResponseEmail(int i)
        {
            status = i;
        }

        public int getStatus()
        {
            return status;
        }
    }
	
	
	public enum DeafultRecordStatus{
        DEFAULT(1), NOT_DEFAULT(0);
        final int status;

        DeafultRecordStatus(int i){
            status = i;
        }

        public int getStatus(){
            return status;
        }
    }
	
	public enum RecordStatusForNewProducts{
        DRAFT(1), APPROVED(0);
        final int status;

        RecordStatusForNewProducts(int i){
            status = i;
        }

        public int getStatus(){
            return status;
        }
    }
	
}
