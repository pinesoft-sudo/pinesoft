package org.pine.netty.server;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.UUID;

import org.pine.netty.NettyMessage;
import org.pine.netty.NettyMessage.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

/**
 * netty-socketio服务服务端事件及方法
 * 
 * @author yangs
 *
 */
@Component
public class MIServerHandler {

	private static SocketIOServer socketIoServer;
	private static ArrayList<UUID> listClient = new ArrayList<>();

	@Autowired
	public MIServerHandler(SocketIOServer server) {
		MIServerHandler.socketIoServer = server;
	}

	// 添加@OnConnect事件，当客户端发起连接时调用，将getSessionId存入静态列（可改为缓存或数据库）
	@OnConnect
	public void onConnect(SocketIOClient client) {
		listClient.add(client.getSessionId());
		System.out.println("客户端:" + client.getSessionId() + "已连接");
	}

	// 添加@OnDisconnect事件，客户端断开连接时调用，静态列移除客户端信息
	@OnDisconnect
	public void onDisconnect(SocketIOClient client) {
		listClient.remove(client.getSessionId());
		System.out.println("客户端:" + client.getSessionId() + "断开连接");
	}

	// 添加@OnEvent，事件消息接收入口，当接收到消息后，查找发送其他给客户端（web或client）
	@OnEvent(value = "server")
	public void onEvent(SocketIOClient client, AckRequest request, NettyMessage data) {
		// System.out.println("客户端：" + data.getMessage());
		for (UUID uuid : listClient) {
			if (!client.getSessionId().equals(uuid)) {
				if (data.getData() == null) {
					socketIoServer.getClient(uuid).sendEvent("web",
							new NettyMessage(ResultEnums.Success, data.getMessage()));
				} else {
					socketIoServer.getClient(uuid).sendEvent("client",
							new NettyMessage(ResultEnums.Success, data.getMessage()));
				}
			}
		}
	}

	public static void sendToWeb(String msg) throws URISyntaxException {
		sendToWeb(msg,null);
	}

	public static void sendToClient(String msg) throws URISyntaxException {
		sendToClient(msg,null);
	}

	public static void sendToWeb(String msg, Object data) throws URISyntaxException {
		for (UUID clientId : listClient) {
			if (socketIoServer.getClient(clientId) != null) {
				socketIoServer.getClient(clientId).sendEvent("web",
						new NettyMessage(ResultEnums.Success, "push data:" + msg), data);
			}
		}
	}

	public static void sendToClient(String msg, Object data) throws URISyntaxException {
		for (UUID clientId : listClient) {
			if (socketIoServer.getClient(clientId) != null) {
				socketIoServer.getClient(clientId).sendEvent("client",
						new NettyMessage(ResultEnums.Success, "push data:" + msg), data);
			}
		}
	}

}
