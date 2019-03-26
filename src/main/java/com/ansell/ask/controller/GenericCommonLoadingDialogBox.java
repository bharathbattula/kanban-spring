package com.ansell.ask.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ansell.ask.common.ResultPath;
import com.ansell.ask.common.Utility;

@Controller
public class GenericCommonLoadingDialogBox {
	
	private static Logger gtLogger = LoggerFactory.getLogger(ASKController.class);
	
	@RequestMapping(value = "/loaddialogbox")
    public ModelAndView loadDialog(HttpServletRequest request) throws Exception
    {
		gtLogger.info("loadDialog :");
        ModelAndView mav = new ModelAndView(ResultPath.DIALOG_CONFIRMATION);

        if (Utility.isNotNullOrBlank(request.getParameter("yes")))
        {
            mav.addObject("yes", request.getParameter("yes"));
        }
        if (Utility.isNotNullOrBlank(request.getParameter("no")))
        {
            mav.addObject("no", request.getParameter("no"));
        }
        if (Utility.isNotNullOrBlank(request.getParameter("cancel")))
        {
            mav.addObject("cancel", request.getParameter("cancel"));
        }
        if (Utility.isNotNullOrBlank(request.getParameter("messageKey")))
        {
            mav.addObject("messageKey", request.getParameter("messageKey"));
        }
        if (Utility.isNotNullOrBlank(request.getParameter("specificData")))
        {
            mav.addObject("specificData", request.getParameter("specificData"));
        }
        
        if (Utility.isNotNullOrBlank(request.getParameter("ok")))
        {
            mav.addObject("ok", request.getParameter("ok"));
        }

        return mav;
    }
}
