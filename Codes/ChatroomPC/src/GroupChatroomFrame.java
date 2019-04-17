import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class GroupChatroomFrame extends ChatFrame implements ActionListener {
	JLabel PortL, IpL, NameL, HistoryMsgL;		//备用，HistoryMsg作为将来的聊天记录存放
	JTextField PortTf, HostIpTf, NameTf;
	JButton ConnectBtn, StopBtn;
	Map<String, User> OnlineUsers;
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
		PortL = new JLabel("端口");
		IpL =new JLabel("服务器IP");
		NameL = new JLabel("姓名");
		PortTf = new JTextField("8888");
		HostIpTf = new JTextField("127.0.0.1");
		NameTf = new JTextField();
		ConnectBtn =new JButton("连接");
		StopBtn = new JButton("停止");
		OnlineUsers = new HashMap<String, User>();
		isConnected = false;
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		
		PortL.setBounds(0, 0, 60, 30); PortTf.setBounds(65, 0, 120, 30);
		IpL.setBounds(0, 35, 60, 30); HostIpTf.setBounds(65, 35, 120, 30);
		NameL.setBounds(0, 70, 60, 30); NameTf.setBounds(65, 70, 120, 30);
		ConnectBtn.setBounds(0, 105, 70, 30); StopBtn.setBounds(75, 105, 70, 30);
		InfoP.add(PortL); InfoP.add(PortTf);
		InfoP.add(IpL); InfoP.add(HostIpTf);
		InfoP.add(NameL); InfoP.add(NameTf);
		InfoP.add(ConnectBtn); InfoP.add(StopBtn);
	}
	
	public void actionProcessor() {
		super.actionProcessor();
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
