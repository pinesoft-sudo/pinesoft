package org.pine.boot.netty;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.pine.common.util.MessageInfo;
import org.pine.common.util.MessageInfo.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

@Component
public class IMEventHandler {

	private static SocketIOServer socketIoServer;
	private static ArrayList<UUID> listClient = new ArrayList<>();

	@Autowired
	public IMEventHandler(SocketIOServer server) {
		IMEventHandler.socketIoServer = server;
	}

	// 添加connect事件，当客户端发起连接时调用，将getSessionId存入静态列（可改为缓存或数据库）
	@OnConnect
	public void onConnect(SocketIOClient client) {
		listClient.add(client.getSessionId());
		System.out.println("客户端:" + client.getSessionId() + "已连接");
	}

	// 添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		listClient.remove(client.getSessionId());
		System.out.println("客户端:" + client.getSessionId() + "断开连接");
	}

	// 消息接收入口，当接收到消息后，查找发送其他给客户端（web或client）
	@OnEvent(value = "server")
	public void onEvent(SocketIOClient client, AckRequest request, MessageInfo data) {
		// System.out.println("客户端：" + data.getMessage());
		for (UUID uuid : listClient) {
			if (!client.getSessionId().equals(uuid)) {
				if (StringUtils.isEmpty(data.getData())) {
					socketIoServer.getClient(uuid).sendEvent("web",
							new MessageInfo(ResultEnums.Success, data.getMessage()));
				} else {
					socketIoServer.getClient(uuid).sendEvent("client",
							new MessageInfo(ResultEnums.Success, data.getMessage()));
				}
			}
		}
	}

	public static void sendToWeb(String msg) {
		for (UUID clientId : listClient) {
			if (socketIoServer.getClient(clientId) != null) {
				socketIoServer.getClient(clientId).sendEvent("web",
						new MessageInfo(ResultEnums.Success, "push data:" + msg));
			}
		}
	}

	public static void sendToServer(String msg) throws URISyntaxException {
		IO.Options options = new IO.Options();
		options.forceNew = false;
		options.reconnection = true;
		final Socket socket = IO.socket("http://192.168.10.215:8200", options);

		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {// 连接成功
			@Override
			public void call(Object... args) {
				// 发送到服务器
				socket.emit("server", JSON.toJSON(new MessageInfo(ResultEnums.Success, msg)));
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
				MessageInfo s = JSON.parseObject(args[0].toString(), MessageInfo.class);
				if (s != null) {
					System.out.println("server data : " + s.getMessage());
				}
			}
		});

		socket.connect();
	}
}
