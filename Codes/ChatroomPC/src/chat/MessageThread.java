package chat;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class MessageThread extends Thread {
	Socket socket;
	DataInputStream in;
	DataOutputStream out;
	JTextArea contentTa;
	
	MessageThread(Socket cSocket, JTextArea contentTa) {
		socket = cSocket;
		this.contentTa = contentTa;
	}

	public void run() {
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF("你好:我是服务器,连接成功。");
			String s;
			while (true) {
				s = in.readUTF();
				//System.out.println("收到客户端的一条消息"+s+",end?"+"end\n".equals(s));
				if (s!=null) contentTa.append(s+"\n");
				//out.writeUTF("我是服务器，我已收到");
				//if (s!=null) break;
				if ("end\n".equals(s)||"end".equals(s)) {
					out.writeUTF("end");
					contentTa.append("关闭\n");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public DataInputStream getIn() {
		return in;
	}

	public void setIn(DataInputStream in) {
		this.in = in;
	}

	public DataOutputStream getOut() {
		return out;
	}

	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	
	
}
