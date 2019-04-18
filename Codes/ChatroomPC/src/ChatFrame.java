import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChatFrame extends JFrame implements ActionListener {
	JPanel infoP;
	JTextField msgSendTf;
	JTextArea contentTa;
	JButton sendBtn;
	Toolkit kit;
	Dimension screenSize;
	int x, y;
	DefaultListModel listModel;
	JList  userList;
	JScrollPane contentScroll, userListScroll;
	JSplitPane sideBarSplitP;
	ChatFrame() {
		/*initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();*/
	}
	
	public void initFrame() {
		setSize(720, 495);
		//setTitle("ÈºÁÄ");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		infoP = new JPanel();
		msgSendTf = new JTextField();
		contentTa = new JTextArea();
		contentTa.setEditable(false);
		contentTa.setForeground(Color.BLUE);
		sendBtn = new JButton("·¢ËÍ");
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
		
		
		sideBarSplitP.setBounds(515, 5, 190, 445);
		sideBarSplitP.setDividerLocation(150);
		sideBarSplitP.setOneTouchExpandable(true);
		
		add(sideBarSplitP);
		add(contentScroll);
		add(msgSendTf);
		add(sendBtn);
	}
	
	public void actionProcessor() {
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
		System.out.println("hello");
		/*if (e.getSource()==msgSendTf || e.getSource()==sendBtn) {
			contentTa.setText(msgSendTf.getText());
			//send()
		}*/
	}
}
