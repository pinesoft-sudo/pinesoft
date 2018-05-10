package mytest.socket;

import java.io.Serializable;

public class SendData implements Serializable {

	// 登录成功返回协议（服务端发送）： {"type":"Lgoin","result":"0"}##
	// 要求客户端开启流媒体（服务端发送）： {"type":"StartRtmp","result":"0"}##

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String result;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
