package com.ansell.ask.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.ansell.ask.service.interfaces.IResourceBundleService;
import com.ansell.ask.util.dbresource.VelocityUtil;

@Controller
public class JSResourceBundleController {
	
	@Autowired
	private MessageSource			messageSource				  		= null;
	
	@Autowired
	private LocaleResolver			sessionLocaleResolver				= null;
	
	@Autowired
	private IResourceBundleService	resourceBundleService				= null;
	
	@RequestMapping(value = "/getResourceBundleData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getResourceBundleData(HttpServletRequest request) throws Exception {
		String keyInitials = request.getParameter("keyInitials");
		String localeId = sessionLocaleResolver.resolveLocale(request).toString();
		return resourceBundleService.getResourceBundleData(localeId, keyInitials);
	}
	
	@RequestMapping(value = "/getParameterizedResourceBundleData", method = RequestMethod.POST,produces="text/html;charset=UTF-8")
	@ResponseBody
	public String getParameterizedResourceBundleData(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "resourceKeyParameter[]") Object[] params) throws Exception {
		String a_strKey = request.getParameter("resourceKey");
		VelocityUtil resourceBundleData = VelocityUtil.getVelocityUtil(messageSource, sessionLocaleResolver.resolveLocale(request));
		return resourceBundleData.getMessage(a_strKey, params);
	}
}
