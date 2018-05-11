package org.pine.common.util;

public class MessageInfo {

	private ResultEnums result;
	private String message;
	private String data;

	public MessageInfo() {
		
	}

	public MessageInfo(ResultEnums result, String message) {
		this.result = result;
		this.message = message;
	}

	public MessageInfo(ResultEnums result, String message, String data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public ResultEnums getResult() {
		return result;
		/*if(result.equals("1")){
			return ResultEnums.True;
		}
		if(result.equals("0")){
			return ResultEnums.False;
		}
		if(result.equals("-1")){
			return ResultEnums.Error;
		}
		return ResultEnums.Error;*/
	}
	

	public void setResult(ResultEnums result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	/**
	 * 转给json
	* @Title: toString 
	* @Description:
	* @author xuj
	* @param  isJson data数据是否是json格式
	* @param @return
	 */
	public String toJson(Boolean isJson) {
		if(data==null|| data.equals("")){
			return "{\"result\":\""+result+"\",\"data\":\""+data+"\", \"message\":\""+message+"\"}";
		}
		if(!isJson){

			if(data.contains("{")&&data.contains("}")){
				
			}
			else {
				data="\""+data+"\"";
			}
		}
		return "{\"result\":\""+result+"\",\"data\":"+data+", \"message\":\""+message+"\"}";

	}
	
	
	/** 
	 * 普通枚举 
	 * 
	 */  
	public enum ResultEnums {  
		/**
		 * 异常
		 */
		Error,//
		/**
		 * 失败
		 */
		Fail,
		/**
		 * 成功
		 */
		Success//
		
	}
	
	
}


