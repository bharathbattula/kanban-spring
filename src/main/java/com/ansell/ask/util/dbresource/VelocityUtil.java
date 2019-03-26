package com.ansell.ask.util.dbresource;

import java.util.Locale;

import org.springframework.context.MessageSource;

public final class VelocityUtil {

	private MessageSource	messageSource	= null;
	private Locale			locale			= null;

	private VelocityUtil(MessageSource a_messageSource, Locale a_local) {
		messageSource = a_messageSource;
		locale = a_local;
	}

	public static VelocityUtil getVelocityUtil(MessageSource a_messageSource, Locale a_local) {
		return new VelocityUtil(a_messageSource, a_local);
	}

	public String getMessage(String a_strKey) {
		return messageSource.getMessage(a_strKey, null, locale);
	}

	public String getMessage(String a_strKey, Object args[]) {
		return messageSource.getMessage(a_strKey, args, locale);
	}

	public String getMessages(Object... args) {
		if (args == null) {
			return "???null???";
		} else if (args.length == 1 && args[0] != null) {
			return messageSource.getMessage(args[0].toString(), null, locale);
		} else {
			Object l_args[] = new Object[args.length - 1];
			System.arraycopy(args, 1, l_args, 0, l_args.length);
			return messageSource.getMessage(args[0].toString(), l_args, locale);
		}
	}
}