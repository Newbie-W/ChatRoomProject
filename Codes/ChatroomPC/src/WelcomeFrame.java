import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WelcomeFrame extends JFrame implements ActionListener {
	JPanel choiceP, contentP;
	JLabel welcomeL;
	ButtonGroup bGp;
	JRadioButton manB, usrB;
	Toolkit kit;
	Dimension screenSize;
	User userInfo;
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
		userInfo = new User();
		choiceP = new JPanel();
		contentP = new JPanel();
		welcomeL = new JLabel("<html><body><h3 color='blue'>WELCOME!<br>Please choose your identity to login!<br></h3></body></html>");
		bGp = new ButtonGroup();
		manB = new JRadioButton("管理员");
		usrB = new JRadioButton("用户");
		bGp.add(manB);
		bGp.add(usrB);
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (screenSize.width-getWidth())/2;
		y = screenSize.height/2-getHeight()/2;
		setLocation(x, y);
	}
	
	private void setFrameLook() {
		choiceP.setLayout(new GridLayout(3,1));
		choiceP.add(manB);
		choiceP.add(usrB);
		add(choiceP, BorderLayout.NORTH);
		contentP.add(welcomeL);
		add(contentP, BorderLayout.CENTER);
	}
	
	private void actionProcessor() {
		manB.addActionListener(this);
		usrB.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == manB) {
			this.setVisible(false);
			userInfo.setIdentity("管理员");
			ManagerLoginFrame loginF = new ManagerLoginFrame(userInfo);
			//UserLoginFrame loginF = new UserLoginFrame(userInfo);
		}else if (e.getSource() == usrB) {
			this.setVisible(false);
			userInfo.setIdentity("用户");
			UserLoginFrame loginF = new UserLoginFrame(userInfo);
		}
	}
}
