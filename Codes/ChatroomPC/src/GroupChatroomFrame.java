import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class GroupChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, ipL, nameL, historyMsgL;		//���ã�HistoryMsg��Ϊ�����������¼���
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
		portL = new JLabel("�˿�");
		ipL =new JLabel("������IP");
		nameL = new JLabel("����");
		portTf = new JTextField("8888");
		hostIpTf = new JTextField("127.0.0.1");
		nameTf = new JTextField();
		connectBtn =new JButton("����");
		stopBtn = new JButton("ֹͣ");
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
				System.out.println("�ң�"+msgSendTf.getText());
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
				System.out.println("�ɹ���������S������C���˿ں�"+socket.getLocalPort());
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
					out.writeUTF("���:���ǿͻ���,���ӳɹ���");
					while (true) {
						s = in.readUTF();
						if (s!=null) contentTa.append(s+"\n");
						//System.out.println("hi"+s);
						//if (s!=null) break;
						//System.out.println("���Է���������Ϣ"+s);
						if ("end\n".equals(s)||"end".equals(s)) {
							contentTa.append("�ر�\n");
							break;
						}
						
					}
					//contentTa.append(s);
					out.writeUTF("�ͻ���:���յ�");
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
