package com.lawbot.core.entity.result;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Cloud Lau
 *
 */
public class Result {

	private int code;

	private String message;

	private Map data;

	public Result(){}

	public Result(int code , String message){
		this.code = code;
		this.message = message;
	}
	
	public static Result success(){
		Result result = new Result();
		result.setResultCode(ResultCode.SUCCESS);
		return result;
	}
	
	public static Result success(Map data){
		Result result = success();
		result.setData(data);
		return result;
	}
	
	public static Result error(ResultCode resultCode){
		Result result = new Result();
		result.setResultCode(resultCode);
		return result;
	}
	public static Result error(ResultCode resultCode , Map data){
		Result result = new Result();
		result.setResultCode(resultCode);
		result.setData(data);
		return result;
	}
	
	public void setResultCode(ResultCode code){
		this.code = code.getCode();
        this.message = code.getMessage();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Map data) {
		this.data = data;
	}
	
	
	public static class ResultData extends HashMap<String, Object>{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public ResultData(){
			
		}
		
		public ResultData(String key , Object value){
			this.put(key, value);
		}
		public ResultData add(String key , Object value){
			this.put(key, value);
			return this;
		}
		
	}
	
	
}
