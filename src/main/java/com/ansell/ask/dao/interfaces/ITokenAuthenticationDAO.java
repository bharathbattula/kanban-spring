/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * Company: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited.
 * ITokenAuthenticationDAO.java
 * Created on 8-Feb-2019
 */

package com.ansell.ask.dao.interfaces;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */
public interface ITokenAuthenticationDAO {

	String getUserIdByToken(String token);

	Double getAskAppVersionByModuleName(String moduleName);
}
