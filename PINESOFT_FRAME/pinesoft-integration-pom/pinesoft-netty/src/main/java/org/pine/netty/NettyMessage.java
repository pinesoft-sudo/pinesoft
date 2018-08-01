package org.pine.netty;

import java.io.Serializable;

public class NettyMessage implements Serializable{
	private static final long serialVersionUID = 1L;
	private ResultEnums result;
	private String message;
	private Object data;

	public NettyMessage() {

	}

	public NettyMessage(ResultEnums result, String message) {
		this.result = result;
		this.message = message;
	}

	public NettyMessage(ResultEnums result, String message, String data) {
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
