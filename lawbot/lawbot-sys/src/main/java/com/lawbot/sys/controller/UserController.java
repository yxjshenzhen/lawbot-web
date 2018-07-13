package com.lawbot.sys.controller;

import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lawbot.core.entity.LawbotUser;
import com.lawbot.core.entity.result.Result;
import com.lawbot.core.entity.result.ResultCode;
import com.lawbot.core.util.SessionUtil;
import com.lawbot.sys.domain.Priv;
import com.lawbot.sys.domain.User;
import com.lawbot.sys.service.PrivService;
import com.lawbot.sys.service.UserService;

/**
 * 
 * @author Cloud Lau
 *
 */


@RequestMapping("user")
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrivService privService;
	
	@Value("${lawbot.url}")
	private String lawUrl;
	
	/**
	 * 
	 * @param callback
	 * @return
	 */
	@GetMapping("login")
	public ModelAndView loginPage(){
		return new ModelAndView("login");
	}

	
	/**
	 * 
	 * @param params
	 * @param session
	 * @return
	 */
	@PostMapping("login")
	public ModelAndView login(@RequestParam String username,@RequestParam String password, @RequestParam String callback,HttpSession session ){	
		User user = userService.login(username, password);
		if(!Objects.isNull(user)){
			LawbotUser luser = new LawbotUser(user.getUid() , username);
			Priv priv = privService.findByUid(user.getUid());
			if(priv != null) 
				luser.setPriv(priv.getPriv());
			
			SessionUtil.setLogin(session, luser);
			return new ModelAndView("redirect:" + callback);
		}else{
			return new ModelAndView("login").addObject("message", "user or password is error");
		}

	}
	
	@GetMapping("logout")
	public String logout(HttpSession session, @RequestParam String callback){
		if(SessionUtil.logout(session) && !StringUtils.isEmpty(callback)){
			return "redirect:" + callback;
		}else{
			return "login";
		}

	}
	
	@GetMapping("me")
	@ResponseBody
	public Result currentUser(HttpSession session){
		LawbotUser user = SessionUtil.getLogin(session);
		if(Objects.isNull(user)) return Result.error(ResultCode.USER_NOT_LOGIN);
		
		return Result.success(new Result.ResultData("user", user));
	}
}
