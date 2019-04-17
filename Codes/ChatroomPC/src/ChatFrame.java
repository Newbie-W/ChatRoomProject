import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatFrame extends JFrame implements ActionListener {
	JPanel InfoP;
	JTextField MsgSendTf;
	JTextArea ContentTa;
	JButton SendBtn;
	Toolkit Kit;
	Dimension ScreenSize;
	int x, y;
	DefaultListModel ListModel;
	JList  UserList;
	JScrollPane ContentScroll, UserListScroll;
	JSplitPane SideBarSplitP;
	ChatFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(720, 495);
		setTitle("ÈºÁÄ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		InfoP = new JPanel();
		MsgSendTf = new JTextField("11");
		ContentTa = new JTextArea();
		ContentTa.setEditable(false);
		ContentTa.setForeground(Color.BLUE);
		SendBtn = new JButton("·¢ËÍ");
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
		
		
		SideBarSplitP.setBounds(515, 5, 190, 445);
		SideBarSplitP.setDividerLocation(150);
		SideBarSplitP.setOneTouchExpandable(true);
		
		add(SideBarSplitP);
		add(ContentScroll);
		add(MsgSendTf);
		add(SendBtn);
	}
	
	public void actionProcessor() {
		System.out.println("hello?");
		MsgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("hello"+MsgSendTf.getText());
				ContentTa.setText(MsgSendTf.getText());
			}
		});
		SendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("hello"+MsgSendTf.getText());
				ContentTa.setText(MsgSendTf.getText());
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("hello");
		/*if (e.getSource()==MsgSendTf || e.getSource()==SendBtn) {
			ContentTa.setText(MsgSendTf.getText());
			//send()
		}*/
	}
}
