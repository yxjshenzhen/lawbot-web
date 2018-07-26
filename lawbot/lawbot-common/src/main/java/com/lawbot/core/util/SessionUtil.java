package com.lawbot.core.util;

import javax.servlet.http.HttpSession;

import org.springframework.util.Assert;

import com.lawbot.core.entity.LawbotUser;


/**
 * 
 * @author Cloud Lau
 *
 */
public final class SessionUtil {
	public final static String LAWBOT_USER = "LAWBOT_USER";
	
	public static void setLogin(HttpSession session , LawbotUser user){
		Assert.notNull(session, "Session is null");
		Assert.notNull(user, "User is null");
		
		session.setAttribute(LAWBOT_USER, user);
	}
	
	/**
	 * 获取当前登录的session
	 * @param session
	 * @return
	 */
	public static LawbotUser getLogin(HttpSession session){
		Assert.notNull(session, "Session is null");
		
		Object user = session.getAttribute(LAWBOT_USER);
		if(user == null || !(user instanceof LawbotUser)) return null;
		return (LawbotUser) user;
	}
	
	public static boolean isLogin(HttpSession session){
		Assert.notNull(session, "Session is null");
		return getLogin(session) != null;
	}
	
	public static boolean logout(HttpSession session){
		Assert.notNull(session);
		session.removeAttribute(LAWBOT_USER);
		return true;
	}
}
