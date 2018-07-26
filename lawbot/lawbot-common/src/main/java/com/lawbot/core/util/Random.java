package com.lawbot.core.util;

import java.util.UUID;

/**
 * 
 * @author Cloud Lau
 *
 */
public final class Random {

	/**
	 * 
	 * @return
	 */
	public static String unique(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	public static void main(String[] args){
		System.out.print(Random.unique());
	}
}
