package com.lawbot.core.web.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Cloud Lau
 *
 */
public interface AccessInterceptorListener {
	
	void authHandler(HttpServletRequest request, HttpServletResponse response) throws IOException;
	
	void accessHandler(HttpServletRequest request, HttpServletResponse response) throws IOException;

}
