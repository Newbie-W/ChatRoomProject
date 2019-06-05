import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.*;
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
	DataInputStream in;
	DataOutputStream out;
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
		setTitle("群聊");
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		portL = new JLabel("端口");
		ipL =new JLabel("服务器IP");
		nameL = new JLabel("姓名");
		portTf = new JTextField("8888");
		hostIpTf = new JTextField("127.0.0.1");
		nameTf = new JTextField();
		connectBtn =new JButton("连接");
		stopBtn = new JButton("停止");
		stopBtn.setEnabled(false);
		listModel = super.listModel;
		userList = super.userList;
		onlineUsers = new HashMap<String, User>();
		isConnected = false;
		msgSendTf.setEditable(false);
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
		stopBtn.addActionListener(this);
		msgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//contentTa.append(msgSendTf.getText()+"\n");
				/*System.out.println("我："+msgSendTf.getText());
				try {
					//contentTa.append(msgSendTf.getText());//+"\n"
					//out.writeUTF(msgSendTf.getText());
					//System.out.println("out"+out+" 一切正常");
					
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}*/
				sendMessage();
				msgSendTf.setText("");
				
			}
		});
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMessage();
				msgSendTf.setText("");
			}
		});
	}

	public void closeConnect() {
		try {
			System.out.println("关闭中");
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startConnect() {
		try {
				socket = new Socket(hostIpTf.getText(), Integer.parseInt(portTf.getText()));//socket = new Socket("127.0.0.1", 8888);
				
			} catch (Exception e1) {
				System.out.println(e1.getMessage());
				e1.printStackTrace();
			}
		new Thread(new Runnable() {
			public void run() {
				
				System.out.println("成功建立连接S，你是C，端口号"+socket.getLocalPort());
				try {
					
					in = new DataInputStream(socket.getInputStream());
					out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF("你好:我是客户端,连接成功。");
					System.out.println("成功发送认证信息");
					while (true) {
						s = in.readUTF();
						if (s!=null) {
							//contentTa.append(s+"\n");
							s = decapsulateMsg(s);
							contentTa.append(s.trim()+"\n");
						}
						System.out.println("hi"+s);
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
		isConnected = true;
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == connectBtn) {
			startConnect();
			if (isConnected) {			/*防止sSocket建立异常，还执行后续操作*/
				connectBtn.setEnabled(false);
				portTf.setEditable(false);
				hostIpTf.setEditable(false);
				nameTf.setEditable(false);
				stopBtn.setEnabled(true);
				msgSendTf.setEditable(true);
				isConnected = true;
			}
		} else if (e.getSource() == stopBtn) {
			
			if (!isConnected) {
				JOptionPane.showMessageDialog(this, "群聊未启动");
				return;
			}
			closeConnect();
			stopBtn.setEnabled(false);
			connectBtn.setEnabled(true);
			portTf.setEditable(true);
			hostIpTf.setEditable(true);
			nameTf.setEditable(true);
			msgSendTf.setEditable(false);
			isConnected = false;
		}
	}
	
	public void sendMessage() {
		System.out.println("11111111111");
		String message = msgSendTf.getText();
		try {
			out.writeUTF( encapsulateMsg(message) );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String encapsulateMsg(String s) {
		//还需进一步修订
		/*发送信息
		 * 关闭连接： 		CLOSE
		 * 登录： 			LOGIN@@@用户名@@@IP@@@PORT
		 * 发送群聊信息 ： 	MESSAGE@@@发送人@@@ALL@@@信息内容
		 * 发送私聊信息：  	MESSAGE@@@发送人@@@接收人@@@信息内容
		 */
		String msg = "";
		String name = "Client"; //后期改为唯一标识名
		if ("CLOSE".equals(s)) {
			msg = "CLOSE";
			System.out.println("服务器打算关闭");
		} else if ("".equals(s)) {
			
		} else if ("HELLO".equals(s)) {
			System.out.println("欢迎");
		} else {
			msg = "MESSAGE" + "@@@" + name + "@@@ALL@@@" + s;
		}
		System.out.println("------encapsulate："+msg);
		return msg;
	}
	
	public String decapsulateMsg(String s) {
		String msg = s;
		String sender = "";
		String receiver = "";
		String message = "";
		String orderMsg[] = msg.split("@@@");
		int length = orderMsg.length;
		String orderName = orderMsg[0];
		System.out.println("-----"+orderName);
		if (length >= 2)
			sender = orderMsg[1];
		if (length >= 3) 
			receiver = orderMsg[2];
		if (length >= 4)
			message = orderMsg[3];
		if ("CLOSE".equals(orderName)) {
			msg = "CLOSE";
			System.out.println("关闭连接");
		} else if ("LOGIN".equals(orderName)) {
			
		} else if ("MESSAGE".equals(orderName)) {
			sender = orderMsg[1];
			receiver = orderMsg[2];
			message = orderMsg[3];
			//System.out.println("-----"+sender+ " " + receiver + " " + message + "-----");
			msg = sender + ":" + message;
			System.out.println("------"+msg);
		}
		System.out.println("HH:" + msg);
		return msg;
	}
}
