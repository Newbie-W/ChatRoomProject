import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserMainFrame extends JFrame {
	JPanel InfoP, FriendP, GroupP;
	JTabbedPane ListTabbedPane;
	JButton SettingBtn, SearchBtn, ExitBtn;
	String Uname;
	ImageIcon HeadImg;
	JLabel HeadL, UnameL, SignL;
	UserMainFrame() {
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
		InfoP = new JPanel();
		FriendP = new JPanel();
		GroupP = new JPanel();
		ListTabbedPane = new JTabbedPane();
		SettingBtn = new JButton("����");
		SearchBtn = new JButton("����");
		ExitBtn = new JButton("�˳�");
		Uname = "TestUsername";
		HeadImg = new ImageIcon("1.jpg");
		HeadL = new JLabel(HeadImg);
		UnameL = new JLabel(Uname);
		SignL = new JLabel("<html>��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo��ǩDemo</html>");
	}
	
	public void setFrameLook() {
		//setLayout(null);
		//InfoP
		InfoP.setLayout(null);
		InfoP.setPreferredSize(new Dimension(250, 100));
		HeadL.setBounds(5, 5, 70, 70);
		HeadL.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
		UnameL.setBounds(90, 5, 100, 20);
		SignL.setBounds(80, 25, 180, 75);	//10, 90, 240, 60
		SignL.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		InfoP.add(HeadL);
		InfoP.add(UnameL);
		InfoP.add(SignL);
		
		//ListTabbedPane
		ListTabbedPane.addTab("����", null, FriendP, "�����б�");
		ListTabbedPane.addTab("Ⱥ��", null, GroupP, "Ⱥ���б�");
		
		//FriendP
		
		//GroupP
		
		add(InfoP, BorderLayout.NORTH);
		add(ListTabbedPane, BorderLayout.CENTER);
	}
	
	public void actionProcessor() {
		
	}
}
