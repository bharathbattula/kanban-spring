package com.ansell.ask.dao.interfaces;

import java.util.Map;

public interface IResourceBundleDAO {
	Map<String, String> getResourceBundleData(String localeId, String keyInitials) throws Exception;
}
