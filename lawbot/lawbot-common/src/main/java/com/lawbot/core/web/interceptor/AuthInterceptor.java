package com.lawbot.core.web.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.core.entity.result.ResultCode;
import com.lawbot.core.util.SessionUtil;

/**
 * 
 * @author Cloud Lau
 *
 */
public class AuthInterceptor implements HandlerInterceptor {
	
	private AccessInterceptorListener listener;
	
	public AuthInterceptor(AccessInterceptorListener listener){
		this.listener = listener;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		if(!SessionUtil.isLogin(session)){
			this.listener.authHandler(request, response);
			return false;
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}


}
