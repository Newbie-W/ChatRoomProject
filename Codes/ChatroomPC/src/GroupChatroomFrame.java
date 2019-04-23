import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class GroupChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, ipL, nameL, historyMsgL;		//备用，HistoryMsg作为将来的聊天记录存放
	JTextField portTf, hostIpTf, nameTf;
	JButton connectBtn, stopBtn;
	DefaultListModel listModel;
	JList userList;
	Map<String, User> onlineUsers;
	Socket socket;
	String s=null;
	//PrintWriter writer;
	//BufferedReader reader;
	DataInputStream in ;
	DataOutputStream out ;
	boolean isConnected;
	GroupChatroomFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		super.initFrame();
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		portL = new JLabel("端口");
		ipL =new JLabel("服务器IP");
		nameL = new JLabel("姓名");
		portTf = new JTextField("8888");
		hostIpTf = new JTextField("127.0.0.1");
		nameTf = new JTextField();
		connectBtn =new JButton("连接");
		stopBtn = new JButton("停止");
		listModel = super.listModel;
		userList = super.userList;
		onlineUsers = new HashMap<String, User>();
		isConnected = false;
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		
		portL.setBounds(0, 0, 60, 30); portTf.setBounds(65, 0, 120, 30);
		ipL.setBounds(0, 35, 60, 30); hostIpTf.setBounds(65, 35, 120, 30);
		nameL.setBounds(0, 70, 60, 30); nameTf.setBounds(65, 70, 120, 30);
		connectBtn.setBounds(0, 105, 70, 30); stopBtn.setBounds(75, 105, 70, 30);
		infoP.add(portL); infoP.add(portTf);
		infoP.add(ipL); infoP.add(hostIpTf);
		infoP.add(nameL); infoP.add(nameTf);
		infoP.add(connectBtn); infoP.add(stopBtn);
	}
	
	public void actionProcessor() {
		super.actionProcessor();
		connectBtn.addActionListener(this);
		msgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//contentTa.append(msgSendTf.getText()+"\n");
				System.out.println("我："+msgSendTf.getText());
				try {
					contentTa.append(msgSendTf.getText());//+"\n"
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
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectStart() {
		new Thread(new Runnable() {
			public void run() {
				try {
					socket = new Socket(hostIpTf.getText(), Integer.parseInt(portTf.getText()));//socket = new Socket("127.0.0.1", 8888);
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				System.out.println("成功建立连接S，你是C，端口号"+socket.getLocalPort());
				try {
					
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					/*} catch (NumberFormatException e2) {
						e2.printStackTrace();
					} catch (UnknownHostException e2) {
						e2.printStackTrace();
					} catch (IOException e2) {
						e2.printStackTrace();
					}
					try {*/
					//int i=0;
					out.writeUTF("你好:我是客户端,连接成功。");
					while (true) {
						s = in.readUTF();
						if (s!=null) contentTa.append(s+"\n");
						//System.out.println("hi"+s);
						//if (s!=null) break;
						//System.out.println("来自服务器的消息"+s);
						if ("end\n".equals(s)||"end".equals(s)) {
							contentTa.append("关闭\n");
							break;
						}
						
					}
					//contentTa.append(s);
					out.writeUTF("客户端:已收到");
					//
					//out.writeUTF(msgSendTf.getText());
					//contentTa.append(s);
					/*writer = new PrintWriter(socket.getOutputStream());
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
					String message = reader.readLine();
					writer.println(message);  
			        writer.flush();*/
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}  
			}
		}).start();
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectBtn) {
			connectStart();
             
		} else if (e.getSource() == stopBtn) {
			closeConnect();
		}
	}
}
