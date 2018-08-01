package org.pine.mvc;

public class MvcResponse {
	private ResultEnums result;
	private String message;
	private Object data;

	public MvcResponse() {

	}

	public MvcResponse(ResultEnums result, String message) {
		this.result = result;
		this.message = message;
	}

	public MvcResponse(ResultEnums result, String message, String data) {
		this.result = result;
		this.message = message;
		this.data = data;
	}

	public ResultEnums getResult() {
		return result;
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public enum ResultEnums {
		/**
		 * 异常
		 */
		Error,
		/**
		 * 失败
		 */
		Fail,
		/**
		 * 成功
		 */
		Success

	}

}
