import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

import javax.swing.*;


public class ServerChatroomFrame extends JFrame implements ActionListener {
	JLabel portL, maxNumL;
	JTextField portTf, maxNumTf;
	JButton startBtn, stopBtn, sendBtn;
	JPanel infoP;
	JTextField msgSendTf;
	JTextArea contentTa;
	Toolkit kit;
	Dimension screenSize;
	int x, y;
	DefaultListModel listModel;
	JList  userList;
	JScrollPane contentScroll, userListScroll;
	JSplitPane sideBarSplitP;
	ServerSocket sSocket;
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
		
		portL = new JLabel("端口");
		maxNumL = new JLabel("最大人数");
		portTf = new JTextField("8888");
		maxNumTf = new JTextField("30");
		startBtn = new JButton("启动");
		stopBtn = new JButton("停止");
		//stopBtn.setEnabled(false);
		infoP = new JPanel();
		msgSendTf = new JTextField("");
		contentTa = new JTextArea();
		contentTa.setEditable(false);
		contentTa.setForeground(Color.BLUE);
		sendBtn = new JButton("发送");
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (screenSize.width - getWidth())/2;
		y = (screenSize.height - getHeight())/2;
		listModel = new DefaultListModel();
		userList = new JList(listModel);
		contentScroll = new JScrollPane(contentTa);
		userListScroll  = new JScrollPane(userList);
		sideBarSplitP = new JSplitPane(JSplitPane.VERTICAL_SPLIT, infoP, userListScroll);
		setLocation(x, y);
	}
	
	public void setFrameLook() {
		setLayout(null);
		contentScroll.setBounds(10, 5, 500, 410);
		msgSendTf.setBounds(10, 420, 415, 30);
		sendBtn.setBounds(430, 420, 80, 30);
		
		infoP.setBounds(515, 5, 185, 150);
		infoP.setLayout(null);
		portL.setBounds(0, 0, 60, 30); portTf.setBounds(65, 0, 120, 30);
		maxNumL.setBounds(0, 35, 60, 30); maxNumTf.setBounds(65, 35, 120, 30);
		startBtn.setBounds(0, 80, 70, 30); stopBtn.setBounds(75, 80, 70, 30);
		infoP.add(portL); infoP.add(portTf);
		infoP.add(maxNumL); infoP.add(maxNumTf);
		infoP.add(startBtn); infoP.add(stopBtn);
		
		sideBarSplitP.setBounds(515, 5, 190, 445);
		sideBarSplitP.setOneTouchExpandable(true);
		sideBarSplitP.setDividerLocation(120);
		
		add(sideBarSplitP);
		add(contentScroll);
		add(msgSendTf);
		add(sendBtn);
	}
	
	public void actionProcessor() {
		startBtn.addActionListener(this);
		msgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentTa.append(msgSendTf.getText()+"\n");
			}
		});
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentTa.append(msgSendTf.getText()+"\n");
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBtn) {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int maxNum;
			int mort = Integer.parseInt(portTf.getText());
			maxNum = Integer.parseInt(maxNumTf.getText());
			//TestEnv();
			//serverStart(MaxNum, Port);
		}
	}
	
}
