import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;

public class GroupChatroomFrame extends JFrame implements ActionListener {
	
	JSplitPane SideBarSplitP; 
	JPanel InfoP;//ContentP, , MsgSendP
	JLabel PortL, IpL, NameL, HistoryMsgL;		//���ã�HistoryMsg��Ϊ�����������¼���
	JTextArea ContentTa;
	JTextField MsgSendTf, PortTf, HostIpTf, NameTf;
	JButton ConnectBtn, StopBtn, SendBtn;
	Toolkit Kit;
	Dimension ScreenSize;
	int x, y;
	Map<String, User> OnlineUsers;
	DefaultListModel ListModel;
	JList UserList;
	JScrollPane ContentScroll, UserListScroll;
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
		setSize(720, 495);
		setTitle("Ⱥ��");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//ContentP = new JPanel();
		//MsgSendP = new JPanel();
		InfoP = new JPanel();
		PortL = new JLabel("�˿�");
		IpL =new JLabel("������IP");
		NameL = new JLabel("����");
		ContentTa = new JTextArea();
		ContentTa.setEditable(false);
		ContentTa.setForeground(Color.BLUE);
		MsgSendTf = new JTextField();
		PortTf = new JTextField("8080");
		HostIpTf = new JTextField("127.0.0.1");
		NameTf = new JTextField();
		ConnectBtn =new JButton("����");
		StopBtn = new JButton("ֹͣ");
		SendBtn = new JButton("����");
		Kit = Toolkit.getDefaultToolkit();
		ScreenSize = Kit.getScreenSize();
		x = (ScreenSize.width - getWidth())/2;
		y = (ScreenSize.height - getHeight())/2;
		ListModel = new DefaultListModel();
		UserList = new JList(ListModel);
		ContentScroll = new JScrollPane(ContentTa);
		UserListScroll = new JScrollPane(UserList);
		SideBarSplitP = new JSplitPane(JSplitPane.VERTICAL_SPLIT, InfoP, UserListScroll);
		OnlineUsers = new HashMap<String, User>();
		isConnected = false;
		setLocation(x, y);
	}
	
	public void setFrameLook() {
		setLayout(null);
		
		ContentScroll.setBounds(10, 5, 500, 410);
		MsgSendTf.setBounds(10, 420, 415, 30);
		SendBtn.setBounds(430, 420, 80, 30);
		
		//InfoP
		InfoP.setBounds(515, 5, 185, 150);
		InfoP.setLayout(null);
		PortL.setBounds(0, 0, 60, 30); PortTf.setBounds(65, 0, 120, 30);
		IpL.setBounds(0, 35, 60, 30); HostIpTf.setBounds(65, 35, 120, 30);
		NameL.setBounds(0, 70, 60, 30); NameTf.setBounds(65, 70, 120, 30);
		ConnectBtn.setBounds(0, 105, 70, 30); StopBtn.setBounds(75, 105, 70, 30);
		InfoP.add(PortL); InfoP.add(PortTf);
		InfoP.add(IpL); InfoP.add(HostIpTf);
		InfoP.add(NameL); InfoP.add(NameTf);
		InfoP.add(ConnectBtn); InfoP.add(StopBtn);
		
		//SideBarP
		SideBarSplitP.setBounds(515, 5, 190, 445);
		SideBarSplitP.setDividerLocation(150);
		SideBarSplitP.setOneTouchExpandable(true);
		
		add(SideBarSplitP);
		add(ContentScroll);
		add(MsgSendTf);
		add(SendBtn);
		//add(InfoP);
	}
	
	public void actionProcessor() {
		
	}

	public void actionPerformed(ActionEvent e) {
		
	}
}
