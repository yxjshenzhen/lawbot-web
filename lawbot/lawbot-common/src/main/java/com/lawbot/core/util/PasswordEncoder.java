package com.lawbot.core.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * 
 * @author Cloud Lau
 *
 */
public final class PasswordEncoder {

	public static void main(String[] args){
		System.out.println(encode(args[0]));
		
	}
	
	public static String encode(String text){
		return new BCryptPasswordEncoder().encode(text);
	}
	
	public static boolean matches(String rawPassword, String encodedPassword){
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}
}
