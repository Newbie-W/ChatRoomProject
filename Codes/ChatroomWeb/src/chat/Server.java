package chat;

import java.util.*;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat")
public class Server {
	
	private String uname;
	private Session session;
	private static HashMap<String, Server> clients = new HashMap<String, Server>();
	private static int onlineNum = 0;
	private boolean isOffline = true;
	
	@OnOpen
	public void start(Session session) {
		this.session = session;
		changeOnlineNum(1);
	}

	@OnClose
	public void end() {
		clients.remove(uname);
		changeOnlineNum(-1);
		String msg = "<font color='blue'>系统消息：  " + uname + " 下线了.</font>";
		sendMessageToAll(msg);
	}

	@OnMessage
	public void sendMessage(String message) {
		if (isOffline) {
			uname = message;
			clients.put(uname, this);
			//System.out.println(clients.size());
			String msg = "<font color='blue'>系统消息：  " + uname + " 上线了.</font>";
			sendMessageToAll(msg);
			isOffline = false;
		} else {
			String msg = uname + ":<br> &emsp;&emsp;" + message;
			sendMessageToAll(msg);
		}
	}

	@OnError
	public void onError(Throwable e) {
		System.out.println("Websocket服务器错误" + e);
	}
	
	public int changeOnlineNum(int tem) {
		onlineNum += tem;
		return onlineNum;
	}
	
	public void sendMessageToAll(String msg) {
		for (Server client : clients.values()) {
			client.session.getAsyncRemote().sendText(msg);
			//System.out.println("send:"+msg);
		}
	}
	
}
