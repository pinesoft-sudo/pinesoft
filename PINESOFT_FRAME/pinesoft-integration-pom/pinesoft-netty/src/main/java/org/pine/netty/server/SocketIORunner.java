package org.pine.netty.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.SocketIOServer;

/**
 * netty-socketio服务启动
 * 
 * @author yangs
 *
 */
@Component
public class SocketIORunner implements CommandLineRunner {

	private final SocketIOServer server;

	@Autowired  
    public SocketIORunner(SocketIOServer server) {  
        this.server = server;  
    }

	@Override
	public void run(String... args) throws Exception {
		server.start();
		System.out.println("socket.io启动成功！");
	}
}
