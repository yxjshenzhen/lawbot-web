package com.lawbot.core.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawbot.core.entity.result.Result;

/**
 * 
 * @author Cloud Lau
 *
 */
public class ResponseUtil {

	public static void handleInterceptorResponse(HttpServletRequest request, HttpServletResponse response , Result result) throws IOException{
		response.setContentType("application/json; charset=utf-8"); 
        
    	PrintWriter writer = response.getWriter();
		writer.print(new ObjectMapper().writeValueAsString(result));
        writer.close();
        response.flushBuffer();
	}
}
