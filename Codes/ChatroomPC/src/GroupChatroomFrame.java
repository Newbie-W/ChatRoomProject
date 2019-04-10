import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class GroupChatroomFrame extends JFrame implements ActionListener {
	JList userList;
	JPanel UserListP, InfoP;//ContentP, , MsgSendP
	JLabel PortL, IpL, NameL, HistoryMsgL;		//���ã�HistoryMsg��Ϊ�����������¼���
	JTextArea ContentTa;
	JTextField MsgSendTf, PortTf, HostIpTf, NameTf;
	JButton ConnectBtn, StopBtn, SendBtn;
	JScrollPane ContentScroll, UserListScroll;
	Map<String, User> OnlineUsers;
	Socket socket;
	PrintWriter writer;
	GroupChatroomFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(720,450);
		setTitle("Ⱥ��");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ContentP = new JPanel();
		//MsgSendP = new JPanel();
		UserListP = new JPanel();
		InfoP = new JPanel();
		PortL = new JLabel("�˿�");
		IpL =new JLabel("������IP");
		NameL = new JLabel("����");
		ContentTa = new JTextArea();
		MsgSendTf = new JTextField();
		PortTf = new JTextField("8080");
		HostIpTf = new JTextField("127.0.0.1");
		NameTf = new JTextField();
		ConnectBtn =new JButton("����");
		StopBtn = new JButton("ֹͣ");
		SendBtn = new JButton("����");
		ContentScroll = new JScrollPane();
		UserListScroll = new JScrollPane();
		
	}
	
	public void setFrameLook() {
		setLayout(null);
		
		ContentTa.setBounds(10, 5, 500, 350);
		MsgSendTf.setBounds(10, 360, 415, 30);
		SendBtn.setBounds(430, 360, 80, 30);
		
		//InfoP
		InfoP.setBounds(515, 5, 185, 200);
		InfoP.setLayout(new GridLayout(7,2));
		InfoP.add(PortL); InfoP.add(PortTf);
		InfoP.add(IpL); InfoP.add(HostIpTf);
		InfoP.add(NameL); InfoP.add(NameTf);
		InfoP.add(ConnectBtn); InfoP.add(StopBtn);
		
		//UserListP
		
		
		add(ContentTa);
		add(MsgSendTf);
		add(SendBtn);
		add(InfoP);
	}
	
	public void actionProcessor() {
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
