package org.pine.netty.client;

import java.net.URISyntaxException;

import org.pine.netty.NettyMessage;
import org.pine.netty.NettyMessage.ResultEnums;

import com.alibaba.fastjson.JSON;

import io.socket.client.IO;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * socketio客户端(后端用法)方法（需实例化，可根据使用情况重写）
 * 
 * @author yangs
 *
 */
public class MIClientHandler {
	private static IO.Options OPTIONS;

	 static{
		OPTIONS = new IO.Options();
		OPTIONS.forceNew = false;
		OPTIONS.reconnection = true;
	}

	public static void sendToServer(String msg) throws URISyntaxException {
		sendToServer(msg, "http://localhost:8200", OPTIONS);
	}

	public static void sendToServer(String msg, String url) throws URISyntaxException {
		sendToServer(msg, url, OPTIONS);
	}
	
	public static void sendToServer(String msg, String url, IO.Options options) throws URISyntaxException {
		final Socket socket = IO.socket(url, options);
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {// 连接成功
			@Override
			public void call(Object... args) {
				// 发送到服务器
				socket.emit("server", JSON.toJSON(new NettyMessage(ResultEnums.Success, msg)));
			}
		});
		socket.on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {// 连接超时
			@Override
			public void call(Object... args) {
				socket.disconnect();
				System.out.println("connect timeout");
			}
		});
		socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() { // 连接失败
			@Override
			public void call(Object... args) {
				socket.disconnect();
				System.out.println("connect error");
			}
		});
		socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {// 断开连接
			@Override
			public void call(Object... args) {
				socket.disconnect();
				System.out.println("disconnect");
			}
		});

		socket.on("client", new Emitter.Listener() { // 接收服务端消息
			@Override
			public void call(Object... args) {
				System.out.println("client receive");
				NettyMessage s = JSON.parseObject(args[0].toString(), NettyMessage.class);
				if (s != null) {
					System.out.println("server data : " + s.getMessage());
				}
			}
		});
		socket.connect();
	}
}
