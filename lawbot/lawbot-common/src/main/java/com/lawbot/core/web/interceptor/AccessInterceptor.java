package com.lawbot.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.lawbot.core.entity.LawbotUser;
import com.lawbot.core.priv.PrivilegeManage;
import com.lawbot.core.util.SessionUtil;

/**
 * 
 * @author Cloud Lau
 *
 */
public class AccessInterceptor implements HandlerInterceptor {

	
	private PrivilegeManage privilegeManage = new PrivilegeManage(0);
	
	private AccessInterceptorListener listener;
	
	public AccessInterceptor(int priv , AccessInterceptorListener listener) {
		privilegeManage.setPriv(priv);
		this.listener = listener;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		HttpSession session = request.getSession();
		
		LawbotUser user = SessionUtil.getLogin(session);
		
		Assert.notNull(listener);
		if(user == null){
			this.listener.authHandler(request, response);
			return false;
		};
		
		int priv = user.getPriv();
		
		if(!privilegeManage.has(priv)){
			this.listener.accessHandler(request, response);
			return false;
		};
		
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
		// TODO Auto-generated method stub

	}
	
}
