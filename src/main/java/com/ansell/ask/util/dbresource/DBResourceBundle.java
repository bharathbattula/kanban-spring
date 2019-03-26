/**
 * Project: Ansell Sales Knowledge: Ansell Limited
 * 
 * Developed by: Trigyn Technologies Limited. DBResourceBundle.java Created on
 * 11-Feb-2019
 */

package com.ansell.ask.util.dbresource;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ansell.ask.common.IConstant;

/**
 * 
 * @author Sudhirkumarrao.Allada
 *
 */

@Repository
public class DBResourceBundle extends AbstractMessageSource {

	@Autowired
	private JdbcTemplate		jdbcTemplate		= null;
	private static String		valueQueryMySQL		= "SELECT COALESCE((select text from resource_bundle rb inner join language lang on rb.language_id = lang.language_id inner join language_app_codes_association AS laca on laca.language_id = lang.language_id where rb.resource_key='%s'and lang.language_code='%s' and laca.app_id = ?),"
			+ "															(select text from resource_bundle rb inner join language lang on rb.language_id = lang.language_id inner join language_app_codes_association AS laca on laca.language_id = lang.language_id where rb.resource_key='%s' and lang.language_code like '%s' and laca.app_id = ? ),"
																	+ " (select text from resource_bundle rb inner join language lang on rb.language_id = lang.language_id inner join language_app_codes_association AS laca on laca.language_id = lang.language_id where rb.resource_key='%s'and lang.language_code='%s' and laca.app_id = ? ),'???%s???')";
	private static String		valueQueryOracle	= "SELECT COALESCE((select text from resource_bundle rb inner join language lang on rb.language_id = lang.language_id where rb.resource_key='%s'and lang.language_code='%s' and lang.app_id=?),(select text from resource_bundle rb join language lang on rb.language_id = lang.language_id where rb.resource_key='%s' and lang.language_code='%s' and lang.app_id=?),'???%s???') from dual";

	private static final Locale	defaultLocale		= Locale.ENGLISH;

	private static String		dbName				= null;

	private static final Logger gtLogger 			= LoggerFactory.getLogger(DBResourceBundle.class);

	@Override
	protected MessageFormat resolveCode(String a_key, Locale a_locale) {
		
		String message = null;
		MessageFormat mfReturn = null;
		String selectQuery = valueQueryMySQL;
		if (a_key == null || a_key.length() < 1) {
			return new MessageFormat("???null???", a_locale);
		}

		try {
			if(dbName == null) {
				dbName = jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName();	
			}
			if("Oracle".equalsIgnoreCase(dbName)) {
				selectQuery = valueQueryOracle;
			}
		} catch (Throwable a_th) {
			gtLogger.error("Error while fetching messages from database"+a_th);
			return new MessageFormat("???" + a_key + "???", a_locale);
		}
		

		if (a_locale == null) {
			a_locale = defaultLocale;
		}

			if("Oracle".equals(dbName)){
				message = jdbcTemplate.queryForObject(String.format(selectQuery, a_key, a_locale, a_key, a_locale.getLanguage(),a_key),new Object[] { IConstant.ASK_APP_ID,IConstant.ASK_APP_ID}, String.class);
			}else{
				message = jdbcTemplate.queryForObject(String.format(selectQuery, a_key, a_locale, a_key, a_locale.getLanguage()+"%",a_key,IConstant.DEFAULT_LOCALE,a_key),new Object[] { IConstant.ASK_APP_ID,IConstant.ASK_APP_ID,IConstant.ASK_APP_ID }, String.class);
			}
			mfReturn = new MessageFormat(message, a_locale);

		return mfReturn;
	}
}