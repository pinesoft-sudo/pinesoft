package mytest.socket;

public class SocketMsg {
	private SocketState state;
	/*
	 * Client use requirements ： Start--required process--unrequired over--required
	 */
	private String identity;
	private Object data;
	private boolean respond;

	public SocketState getState() {
		return state;
	}

	public void setState(SocketState state) {
		this.state = state;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public boolean isRespond() {
		return respond;
	}

	public void setRespond(boolean respond) {
		this.respond = respond;
	}

	public enum SocketState {
		start, process, over
	}
}
