/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * ITokenAuthenticationService.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.service.interfaces;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
public interface ITokenAuthenticationService {
	
	String getUserIdByToken(String token);

	Double getAskAppVersionByModuleName(String moduleName);
}
