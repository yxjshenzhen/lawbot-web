package com.lawbot.chatbot.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import com.lawbot.core.entity.LawbotUser;
import com.lawbot.core.priv.PrivilegeManage;
import com.lawbot.core.util.SessionUtil;

@WebFilter("/desk/*")
@Order(0)
public class ChatbotAccessFilter implements Filter {
	
	
	@Value("${lawbot.chatbot.url}")
	private String chatbotUrl;
	
	@Value("${lawbot.url}")
	private String lawbotUrl;
	
	private PrivilegeManage privilegeManage = new PrivilegeManage(PrivilegeManage.ROBOT_PRIV);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res	 = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		LawbotUser user = SessionUtil.getLogin(session);
		
		if(user == null){
			res.sendRedirect(String.format("%s/account/login?callback=%s" ,lawbotUrl , chatbotUrl));
			return;
		};
		
		int priv = user.getPriv();
		
		if(!privilegeManage.has(priv)){
			res.setStatus(HttpStatus.FORBIDDEN.value());
			res.getWriter().write("No sufficient privilege");
			res.getWriter().flush();
			return;
		};
		chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		

	}

}
