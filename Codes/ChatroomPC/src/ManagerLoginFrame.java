import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ManagerLoginFrame extends JFrame implements ActionListener {
	JPanel inputP, loginBtnP;
	ImageIcon bgImg;
	JLabel bgL, unameL, pwdL;
	JTextField unameTf;
	JPasswordField pwdF;
	JButton loginBtn;
	Toolkit kit;
	Dimension screenSize;
	int x, y;
	DBCon con;
	
	ManagerLoginFrame(User UserInfo) {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(400, 300);
		setTitle("聊天室管理登录");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		inputP = new JPanel();
		loginBtnP = new JPanel();
		bgImg = new ImageIcon("ManagerBg.jpg");
		bgL = new JLabel(bgImg);
		unameL = new JLabel("账户名");
		pwdL = new JLabel("密码");
		unameTf = new JTextField();
		pwdF = new JPasswordField();
		loginBtn = new JButton("登录");
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (screenSize.width - getWidth()) / 2;
		y = (screenSize.height - getHeight()) / 2;
		setLocation(x,y);
	}
	
	public void setFrameLook() {
		bgL.setBounds(0,0,400,102);
		
		inputP.setLayout(null);
		unameL.setBounds(60, 40, 55, 20);
		unameL.setForeground(new Color(0, 0, 0));
		unameL.setFont(new Font("楷体", 0, 16));
		pwdL.setBounds(60, 75, 55, 20);
		pwdL.setForeground(new Color(0, 0, 0));
		pwdL.setFont(new Font("楷体", 0, 16));
		unameTf.setBounds(130, 40, 175, 30);
		pwdF.setBounds(130, 75, 175, 30);
		inputP.add(unameL); inputP.add(pwdL); inputP.add(unameTf); inputP.add(pwdF);
		
		loginBtnP.setLayout(null);
		loginBtnP.setPreferredSize(new Dimension(390, 40));
		loginBtn.setBounds(155, 0, 100, 30);
		loginBtn.setBackground(Color.WHITE);
		loginBtnP.add(loginBtn);
		
		add(bgL, BorderLayout.NORTH);
		add(inputP, BorderLayout.CENTER);
		add(loginBtnP, BorderLayout.SOUTH);
	}
	
	public void actionProcessor() {
		loginBtn.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loginBtn) {
			String uname = unameTf.getText();
			String pwd = new String(pwdF.getPassword());
			String temp = "";
			if (con==null || !con.isConStart()) {
				con = new DBCon();
			}
			if (pwd == null || "".equals(pwd.trim())) {
				temp = "select * from UserInfo where 身份='管理员' and 用户名 = '"+uname.trim()+"' and 密码 is null";
			} else {
				temp = "select * from UserInfo where 用户名 = '"+uname.trim()+"' and 密码='"+pwd.trim()+"'";//身份='管理员' and
			}
			System.out.println(temp+" , "+con.getSelect(con.getSt(), temp));
			if (con.getSelect(con.getSt(), temp).equals("")) {
				JOptionPane.showMessageDialog(null, "登录失败");
				con.closeSt();
			}else {
				JOptionPane.showMessageDialog(null, "登录成功");
				con.closeSt();
				this.setVisible(false);
				new ManagerMainFrame(new User(uname, pwd));
			}
		}
	}
}
