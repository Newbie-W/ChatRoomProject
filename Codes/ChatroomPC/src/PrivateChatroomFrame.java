import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class PrivateChatroomFrame extends ChatFrame implements ActionListener {
	JLabel isFriendOnlineL, portL, ipL, friendPortL, friendIpL, historyMsgL;	//isOnline那个，用于显示用户是否在线，暂时未设，备用
	Socket socket;
	String s=null;
	DataInputStream in;
	DataOutputStream out;
	int connectServerPort;
	String connectServerIP;
	boolean isConnected;
	PrivateChatroomFrame() {
		startConnect();
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		super.initFrame();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		connectServerPort = 8888;
		connectServerIP = "127.0.0.1";
		isFriendOnlineL = new JLabel("对方在线"); 
		portL = new JLabel("当前使用端口：");
		ipL = new JLabel("当前IP：");
		friendPortL = new JLabel("好友使用端口：");
		friendIpL = new JLabel("好友当前IP：");
		isConnected = false;
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		portL.setBounds(0, 0, 120, 30);
		ipL.setBounds(0, 35, 120, 30);
		friendPortL.setBounds(0, 70, 120, 30);
		friendIpL.setBounds(0, 105, 120, 30);
		infoP.add(portL); infoP.add(ipL);
		infoP.add(friendPortL); infoP.add(friendIpL);
	}
	
	public void actionProcessor() {
		super.actionProcessor();
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isConnected) {
					closeConnect();
				}
				setVisible(false);
			}
		});
		msgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					contentTa.append(msgSendTf.getText());
					out.writeUTF(msgSendTf.getText());
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				msgSendTf.setText("");
			}
		});
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentTa.append(msgSendTf.getText()+"\n");
				msgSendTf.setText("");
			}
		});
	}
	
	public void closeConnect() {
		try {
			out.close();
			in.close();
			socket.close();
			JOptionPane.showMessageDialog(this, "成功断开连接");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startConnect() {
		new Thread(new Runnable() {
			public void run() {
				try {
					//socket = new Socket(connectServerIP, connectServerPort);		//使用此句不行，但使用下面那句可以
					socket = new Socket("127.0.0.1", 8888);
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF("你好:我是客户端,连接成功。");
					while (true) {
						s = in.readUTF();
						if (s!=null) contentTa.append(s+"\n");
						//if (s!=null) break;
						if ("end\n".equals(s)||"end".equals(s)) {
							contentTa.append("关闭\n");
							break;
						}
					}
					out.writeUTF("客户端:已收到");
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}  
			}
		}).start();
	}
}
