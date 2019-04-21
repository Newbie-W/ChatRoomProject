import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class UserMainFrame extends JFrame implements ActionListener {
	JPanel infoP, groupP;
	FriendPanel  friendP;
	JTabbedPane listTabbedPane;
	JButton settingBtn, searchBtn, exitBtn, groupChatBtn;
	String uname;
	ImageIcon headImg;
	JLabel headL, unameL, signL;
	UserMainFrame(User user) {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setTitle("�б�");
		setSize(280, 580);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		infoP = new JPanel();
		friendP = new FriendPanel();
		groupP = new JPanel();
		listTabbedPane = new JTabbedPane();
		groupChatBtn = new JButton("����Ⱥ��");
		//settingBtn = new JButton("����");
		//searchBtn = new JButton("����");
		//exitBtn = new JButton("�˳�");
		uname = "TestUsername";
		headImg = new ImageIcon("1.jpg");
		headL = new JLabel(headImg);
		unameL = new JLabel(uname);
		signL = new JLabel("<html>��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo</html>");
		//signL = new JLabel(User);
	}
	
	public void setFrameLook() {
		//setLayout(null);
		//infoP
		infoP.setLayout(null);
		infoP.setPreferredSize(new Dimension(250, 100));
		headL.setBounds(5, 5, 70, 70);
		headL.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
		unameL.setBounds(90, 5, 100, 20);
		signL.setBounds(80, 25, 180, 75);	//10, 90, 240, 60
		signL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		infoP.add(headL);
		infoP.add(unameL);
		infoP.add(signL);
		
		//listTabbedPane
		listTabbedPane.addTab("����", null, friendP, "�����б�");
		listTabbedPane.addTab("Ⱥ��", null, groupP, "Ⱥ���б�");
		
		//groupP
		groupP.add(groupChatBtn);
		
		add(infoP, BorderLayout.NORTH);
		add(listTabbedPane, BorderLayout.CENTER);
	}
	
	public void actionProcessor() {
		groupChatBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == groupChatBtn) {
			new GroupChatroomFrame();
		}
	}
}
