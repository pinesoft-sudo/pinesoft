package mytest.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.alibaba.fastjson.JSON;

import mytest.socket.SocketMsg.SocketState;

public class SocketProcessClient {
	public Socket socket;

	public SocketProcessClient(Socket socket) throws Exception {
		this.socket = socket;
	}

	public SocketProcessClient(String ip, int port) throws Exception {
		this.socket = new Socket(ip, port);
	}

	public SocketProcessClient(String ip, int port, String identity) throws Exception {
		this.socket = new Socket(ip, port);
		clientInit(identity);
	}

	public void clientInit(String identity) throws Exception {
		// 客户端输出流
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
		// 服务端输入流
		BufferedReader bufferedReaderFromServer = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "utf-8"));
		SocketMsg initmsg = new SocketMsg();
		initmsg.setIdentity(identity);
		initmsg.setState(SocketState.start);
		initmsg.setRespond(true);
		// socket连接初始化发送数据
		printWriter.println(JSON.toJSONString(initmsg));
		printWriter.flush();
		// socket连接初始化接收数据
		System.out.println("Server conn Response:" + bufferedReaderFromServer.readLine());
	}

	public String clientAccept() throws Exception {
		// 客户端输出流
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
		// 服务端输入流
		BufferedReader bufferedReaderFromServer = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "utf-8"));

		String retStr = bufferedReaderFromServer.readLine();
		SocketMsg msg = JSON.parseObject(retStr, SocketMsg.class);
		if (msg.isRespond()) {
			// Returns the information to the service
			SocketMsg _msg = new SocketMsg();
			_msg.setIdentity("server");
			_msg.setState(SocketState.process);
			_msg.setRespond(false);
			
			SendData data=new SendData();
			data.setResult("0");
			data.setType("live");
			_msg.setData(data);
			
			printWriter.println(JSON.toJSONString(_msg));
			printWriter.flush();
		}
		if (msg.getState().equals(SocketState.over)) {
			printWriter.close();
			bufferedReaderFromServer.close();
			socket.close();
		}
		System.out.println("Client received:[" + retStr + "]");
		return retStr;
	}

	public String clientSend(String identity, Object send, boolean respond, SocketState state) throws Exception {
		if (state == null) {
			state = SocketState.process;
		}
		SocketMsg msg = new SocketMsg();
		msg.setData(send);
		msg.setIdentity(identity);
		msg.setRespond(respond);
		msg.setState(state);

		return clientSend(msg);
	}

	public String clientSend(SocketMsg msg) throws Exception {
		if (msg == null) {
			throw new Exception("No valid object parameters are obtained");
		}

		// 客户端输出流
		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"));
		// 服务端输入流
		BufferedReader bufferedReaderFromServer = new BufferedReader(
				new InputStreamReader(socket.getInputStream(), "utf-8"));

		// 将输出流发送给服务端
		printWriter.println(JSON.toJSONString(msg));
		printWriter.flush();

		String retStr = null;
		if (msg.isRespond()) {
			retStr = bufferedReaderFromServer.readLine();
		}
		if (msg.getState().equals(SocketState.over)) {
			printWriter.close();
			bufferedReaderFromServer.close();
			socket.close();
		}
		return retStr;
	}
}
