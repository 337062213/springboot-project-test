package com.springboot.test.model;

public class BaseResponse<T> extends ResponseBody<T> {
	
	private String status;
	
	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[status : " + this.status + ", " );
		sb.append("[message : " + this.message + "]" );
		return sb.toString();
	}
	
	
	
	

}
