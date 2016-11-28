package com.mypro.configure.security.exception;

import org.springframework.security.core.AuthenticationException;
/**
 * captcha is wrong
 * @author user
 *
 */
public class WrongCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public WrongCaptchaException(String msg) {
		super(msg);
	}
	
	public WrongCaptchaException(String msg, Throwable t) {
		super(msg, t);
	}

}
