package com.mypro.configure.security.exception;

import org.springframework.security.core.AuthenticationException;
/**
 * security common exception
 * @author user
 *
 */
public class SecurityCommonException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public SecurityCommonException(String msg) {
		super(msg);
	}
	
	public SecurityCommonException(String msg, Throwable t) {
		super(msg, t);
	}

}
