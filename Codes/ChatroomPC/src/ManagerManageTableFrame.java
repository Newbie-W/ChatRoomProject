import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ManagerManageTableFrame extends JFrame implements ActionListener {
	JPanel inputP, searchResultP, btnP, nameP, pwdP;
	JLabel tableNameL, unameL, pwdL, identityL;
	JTextField unameTf, pwdTf, identityTf;
	JTextArea resultTa;
	JScrollPane scrollResult;
	JButton insertBtn, changeBtn, deleteBtn, searchBtn;
	Toolkit kit;
	Dimension screenSize;
	int x, y;
	DBCon con;
	ManagerManageTableFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(500, 400);
		setTitle("管理表");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		inputP = new JPanel();
		searchResultP = new JPanel();
		btnP = new JPanel();
		nameP = new JPanel();
		pwdP = new JPanel();
		tableNameL = new JLabel("UserInfo");
		unameL = new JLabel("用户名");
		pwdL = new JLabel("密码");
		identityL = new JLabel("身份");
		unameTf = new JTextField();
		pwdTf = new JTextField();
		identityTf = new JTextField();
		resultTa = new JTextArea();
		scrollResult = new JScrollPane(resultTa);
		insertBtn = new JButton("增加");
		changeBtn = new JButton("修改");
		deleteBtn = new JButton("删除");
		searchBtn = new JButton("查找");
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (int)(screenSize.getWidth() - getWidth()) / 2;
		y = (int)(screenSize.getHeight() - getHeight()) / 2;
		setLocation(x, y);
	}
	
	public void setFrameLook() {
		setLayout(null);
		inputP.setBounds(10, 10, 240, 200);
		inputP.setLayout(null);
		unameL.setBounds(20, 60, 50, 25); unameTf.setBounds(100, 60, 120, 25);
		pwdL.setBounds(20, 90, 50, 25); pwdTf.setBounds(100, 90, 120, 25);
		identityL.setBounds(20, 120, 50, 25); identityTf.setBounds(100, 120, 120, 25);	
		
		btnP.setBounds(20, 210, 210, 70);
		btnP.setLayout(new GridLayout(2, 2, 10, 10));
		
		tableNameL.setBounds(250, 0, 60, 25);
		scrollResult.setLayout(null);
		scrollResult.setBounds(250, 30, 215, 300);
		inputP.add(unameL); inputP.add(unameTf); inputP.add(pwdL); inputP.add(pwdTf); inputP.add(identityL); inputP.add(identityTf);
		btnP.add(insertBtn); btnP.add(changeBtn); btnP.add(deleteBtn); btnP.add(searchBtn);
		add(inputP); add(btnP); add(tableNameL); add(scrollResult);
	}
	
	public void actionProcessor() {
		insertBtn.addActionListener(this);
		changeBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		searchBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String uname = unameTf.getText();	//后来改为the first attribute
		String pwd = pwdTf.getText();	//the second attribute
		String identity = identityTf.getText();	//the third attribute
		if (e.getSource() == insertBtn) {
			
		} else if (e.getSource() == changeBtn) {
			
		} else if (e.getSource() == deleteBtn) {
			
		} else if (e.getSource() == searchBtn) {
			
		}
	}
	
}
