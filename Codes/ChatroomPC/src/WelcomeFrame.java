import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class WelcomeFrame extends JFrame implements ActionListener {
	JPanel ChoiceP, ContentP;
	JLabel WelcomeL;
	ButtonGroup BGp;
	JRadioButton ManB, UsrB;
	Toolkit Kit;
	Dimension ScreenSize;
	User UserInfo;
	int x, y;
	WelcomeFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	private void initFrame() {
		setSize(400,300);
		setTitle("聊天室客户端");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		UserInfo = new User();
		ChoiceP = new JPanel();
		ContentP = new JPanel();
		WelcomeL = new JLabel("<html><body><h3 color='blue'>WELCOME!<br>Please choose your identity to login!<br></h3></body></html>");
		BGp = new ButtonGroup();
		ManB = new JRadioButton("管理员");
		UsrB = new JRadioButton("用户");
		BGp.add(ManB);
		BGp.add(UsrB);
		Kit = Toolkit.getDefaultToolkit();
		ScreenSize = Kit.getScreenSize();
		x = (ScreenSize.width-getWidth())/2;
		y = ScreenSize.height/2-getHeight()/2;
		setLocation(x, y);
	}
	
	private void setFrameLook() {
		ChoiceP.setLayout(new GridLayout(3,1));
		ChoiceP.add(ManB);
		ChoiceP.add(UsrB);
		add(ChoiceP, BorderLayout.NORTH);
		ContentP.add(WelcomeL);
		add(ContentP, BorderLayout.CENTER);
	}
	
	private void actionProcessor() {
		ManB.addActionListener(this);
		UsrB.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ManB) {
			this.setVisible(false);
			UserInfo.setIdentity("管理员");
			LoginFrame LoginF = new LoginFrame(UserInfo);
		}else if (e.getSource() == UsrB) {
			this.setVisible(false);
			UserInfo.setIdentity("用户");
			LoginFrame LoginF = new LoginFrame(UserInfo);
		}
	}
}
