package com.lawbot.core.entity.result;

public enum ResultCode{
		
	SUCCESS(200, "Success"),//code == 200 其他全部为错误
	
	/**
	 * 用户错误
	 */
	USER_NOT_LOGIN(401 , "User not login"),
	USER_PRIV_INSUFFICENT(402,"User privilege insufficent"),
	
	/**
	 * 参数错误
	 */
	REQUEST_PARAMS_ERROR(501 , "Request parameters error"),
	
	
	/**
	 * 其他错误
	 */
	OTHER_ERROR(601, "OTHER ERROR"),
	UNKNOW_ERROR(602,"Unknow error");
	
	
	
	private ResultCode(int code , String message){
		this.code = code;
		this.message = message;
	}
	
	private Integer code;
	
    private String message;

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
    
    
}