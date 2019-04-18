import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class GroupChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, ipL, nameL, historyMsgL;		//备用，HistoryMsg作为将来的聊天记录存放
	JTextField portTf, hostIpTf, nameTf;
	JButton connectBtn, stopBtn;
	Map<String, User> onlineUsers;
	Socket socket;
	PrintWriter writer;
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
		portL = new JLabel("端口");
		ipL =new JLabel("服务器IP");
		nameL = new JLabel("姓名");
		portTf = new JTextField("8888");
		hostIpTf = new JTextField("127.0.0.1");
		nameTf = new JTextField();
		connectBtn =new JButton("连接");
		stopBtn = new JButton("停止");
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
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
