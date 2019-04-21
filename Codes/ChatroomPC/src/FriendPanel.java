import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class FriendPanel extends JPanel implements ActionListener, ListSelectionListener, MouseListener {
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
		friendBtn.setFocusPainted(false);
		friendBtn.setBounds(5, 5, 250, 25);
		//scrollUser.add(userList);
		scrollUser.setBounds(5, 30, 250, 370);
		add(friendBtn);// add(strangerBtn);
		add(scrollUser);
	}
	public void actionProcessor() {
		friendBtn.addActionListener(this);
		userList.addListSelectionListener(this);
		userList.addMouseListener(this);
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

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == userList) {
			System.out.println("1你选择了List中的"+userList.getSelectedIndex());
			//为何不管点几次（1,2次）都显示两条
			//而且多选，显示的不是新点选的，而是最起始的一条
			/*PrivateChatroomFrame privateChat = new PrivateChatroomFrame();
			privateChat.setTitle(""+e.getLastIndex());*/
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			String title = (String)listModel.get(((JList)(e.getSource())).getSelectedIndex());
			System.out.println(""+ title);
			PrivateChatroomFrame privateChat = new PrivateChatroomFrame();
			privateChat.setTitle(""+title);
		}
	}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}
	
	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}
}
