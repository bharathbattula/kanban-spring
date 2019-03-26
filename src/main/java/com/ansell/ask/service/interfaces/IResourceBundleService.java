package com.ansell.ask.service.interfaces;

import java.util.Map;

public interface IResourceBundleService {

	Map<String, String> getResourceBundleData(String localeId, String keyInitials) throws Exception;
}
