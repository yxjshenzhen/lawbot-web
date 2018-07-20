package com.lawbot.contract.configurer;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.ResultCode;
import com.lawbot.core.entity.result.Result.ResultData;
import com.lawbot.core.priv.PrivilegeManage;
import com.lawbot.core.util.ResponseUtil;
import com.lawbot.core.web.interceptor.AccessInterceptor;
import com.lawbot.core.web.interceptor.AccessInterceptorListener;

/**
 * 
 * @author Cloud Lau
 *
 */
@Configuration
public class ContractWebConfigurerAdapter extends WebMvcConfigurerAdapter {

	@Value("${lawbot.url}")
	private String url;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		addAccessInterceptor(registry);
	}
	
	private void addAccessInterceptor(InterceptorRegistry registry){
		registry.addInterceptor(new AccessInterceptor(PrivilegeManage.CONTRACT_PRIV,listener)).addPathPatterns("/**")
		.excludePathPatterns("/error");
	}
	
	private AccessInterceptorListener listener = new AccessInterceptorListener() {
		
		@Override
		public void authHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
			ResultData params = new ResultData("loginUrl", url + "/account/login");
			ResponseUtil.handleInterceptorResponse(request, response, Result.error(ResultCode.USER_NOT_LOGIN , params));
		}
		
		@Override
		public void accessHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
			ResponseUtil.handleInterceptorResponse(request, response, Result.error(ResultCode.USER_PRIV_INSUFFICENT));	
		}
	};
}
