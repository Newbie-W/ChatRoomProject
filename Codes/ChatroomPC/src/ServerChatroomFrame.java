import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

import javax.swing.*;


public class ServerChatroomFrame extends JFrame implements ActionListener {
	JLabel PortL, MaxNumL;
	JTextField PortTf, MaxNumTf;
	JButton StartBtn, StopBtn, SendBtn;
	JPanel InfoP;
	JTextField MsgSendTf;
	JTextArea ContentTa;
	Toolkit Kit;
	Dimension ScreenSize;
	int x, y;
	DefaultListModel ListModel;
	JList  UserList;
	JScrollPane ContentScroll, UserListScroll;
	JSplitPane SideBarSplitP;
	ServerSocket SSocket;
	boolean isStart = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new ServerChatroomFrame();
	}

	ServerChatroomFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(720, 495);
		setTitle("群聊");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		PortL = new JLabel("端口");
		MaxNumL = new JLabel("最大人数");
		PortTf = new JTextField("8888");
		MaxNumTf = new JTextField("30");
		StartBtn = new JButton("启动");
		StopBtn = new JButton("停止");
		//StopBtn.setEnabled(false);
		InfoP = new JPanel();
		MsgSendTf = new JTextField("");
		ContentTa = new JTextArea();
		ContentTa.setEditable(false);
		ContentTa.setForeground(Color.BLUE);
		SendBtn = new JButton("发送");
		Kit = Toolkit.getDefaultToolkit();
		ScreenSize = Kit.getScreenSize();
		x = (ScreenSize.width - getWidth())/2;
		y = (ScreenSize.height - getHeight())/2;
		ListModel = new DefaultListModel();
		UserList = new JList(ListModel);
		ContentScroll = new JScrollPane(ContentTa);
		UserListScroll  = new JScrollPane(UserList);
		SideBarSplitP = new JSplitPane(JSplitPane.VERTICAL_SPLIT, InfoP, UserListScroll);
		setLocation(x, y);
	}
	
	public void setFrameLook() {
		setLayout(null);
		ContentScroll.setBounds(10, 5, 500, 410);
		MsgSendTf.setBounds(10, 420, 415, 30);
		SendBtn.setBounds(430, 420, 80, 30);
		
		InfoP.setBounds(515, 5, 185, 150);
		InfoP.setLayout(null);
		PortL.setBounds(0, 0, 60, 30); PortTf.setBounds(65, 0, 120, 30);
		MaxNumL.setBounds(0, 35, 60, 30); MaxNumTf.setBounds(65, 35, 120, 30);
		StartBtn.setBounds(0, 80, 70, 30); StopBtn.setBounds(75, 80, 70, 30);
		InfoP.add(PortL); InfoP.add(PortTf);
		InfoP.add(MaxNumL); InfoP.add(MaxNumTf);
		InfoP.add(StartBtn); InfoP.add(StopBtn);
		
		SideBarSplitP.setBounds(515, 5, 190, 445);
		SideBarSplitP.setOneTouchExpandable(true);
		SideBarSplitP.setDividerLocation(120);
		
		add(SideBarSplitP);
		add(ContentScroll);
		add(MsgSendTf);
		add(SendBtn);
	}
	
	public void actionProcessor() {
		StartBtn.addActionListener(this);
		MsgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentTa.append(MsgSendTf.getText()+"\n");
			}
		});
		SendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContentTa.append(MsgSendTf.getText()+"\n");
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == StartBtn) {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int MaxNum;
			int Port = Integer.parseInt(PortTf.getText());
			MaxNum = Integer.parseInt(MaxNumTf.getText());
			//TestEnv();
			//serverStart(MaxNum, Port);
		}
	}
	
}
