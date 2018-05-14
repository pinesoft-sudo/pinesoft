package org.pine.common.entity;

/**
 * 返回值公共对象
 * 
 * @author yangsoon
 *
 */
public class MessageInfo {

	private ResultEnums result;
	private String message;
	private Object data;

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
		// 逻辑错误
		Error,

		// 执行结果失败
		Fail,

		// 成功
		Success,

		// 调试|测试
		Debug,

		// 信息
		Info
	}
}
