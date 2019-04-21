import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class FriendPanel extends JPanel implements ActionListener {
	JButton friendBtn, strangerBtn;
	DBCon con;
	DefaultListModel listModel;
	JList userList;
	JScrollPane scrollUser;
	boolean isFriendPanelOpen = true;
	
	FriendPanel() {
		initPanel();
		setPanelLook();
		actionProcessor();
	}
	public void initPanel() {
		friendBtn = new JButton("▽好友");
		//strangerBtn = new JButton("▽陌生人");
		listModel = new DefaultListModel();
		userList = new JList(listModel);
		scrollUser = new JScrollPane(userList);
		con = new DBCon();
		String temp = "select 用户名 from UserInfo";
		ResultSet rs;
		try {
			rs = con.getSt().executeQuery(temp);
			String s = new String();
			while (rs.next()) {
				s = rs.getString(1);
				listModel.addElement(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void setPanelLook() {
		setLayout(null);
		friendBtn.setContentAreaFilled(false);
		friendBtn.setBounds(5, 5, 250, 25);
		//scrollUser.add(userList);
		scrollUser.setBounds(5, 30, 250, 370);
		add(friendBtn);// add(strangerBtn);
		add(scrollUser);
	}
	public void actionProcessor() {
		friendBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == friendBtn) {
			if (isFriendPanelOpen) {
				scrollUser.setVisible(false);
				isFriendPanelOpen = false;
				friendBtn.setText(new String("I> 好友"));
			} else {
				scrollUser.setVisible(true);
				isFriendPanelOpen = true;
				friendBtn.setText(new String("▽ 好友"));
			}
		}
	}
}
