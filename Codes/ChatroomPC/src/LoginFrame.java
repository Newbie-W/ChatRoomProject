import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener, MouseListener,MouseMotionListener {
	JPanel ctrlBtnP, picP, inputP, loginBtnP;//
	ImageIcon img, headImg;	//, LogoImg
	JLabel bgL, headL, registerL, forgetPwdL, unameL, pwdL, titleL;	//, LogoL
	JTextField unameTf;
	JPasswordField pwdF;
	JButton closeBtn, minBtn, loginBtn;
	Toolkit kit;
	Dimension screenSize;
	int x, y, startX, startY, endX, endY;
	DBCon con; 
	LoginFrame(User UserInfo) {
		System.out.println(UserInfo.getIdentity());
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	private void initFrame() {
		setSize(400, 300);
		setTitle("�����ҵ�¼");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
		ctrlBtnP = new JPanel();
		picP = new JPanel();
		inputP = new JPanel();
		loginBtnP = new JPanel();
		img = new ImageIcon("qq1.png");
		headImg = new ImageIcon("1.jpg");
		bgL =  new JLabel(img);
		headL = new JLabel(headImg);
		registerL = new JLabel("ע���˺�");
		forgetPwdL = new JLabel("�������룿");
		registerL.setFont(new Font("����", 0, 11));
		forgetPwdL.setFont(new Font("����", 0, 11));
		registerL.setForeground(new Color(128, 128, 255));
		forgetPwdL.setForeground(new Color(128, 128, 255));
		unameL = new JLabel("�û���");
		pwdL = new JLabel("����");
		titleL = new JLabel("WeTalk");
		titleL.setFont(new Font("����", 1, 16));
		titleL.setForeground(Color.lightGray);
		unameTf = new JTextField();
		pwdF = new JPasswordField();
		closeBtn =  new JButton("X");
		minBtn = new JButton("��");
		loginBtn = new JButton("��¼");
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (screenSize.width-getWidth())/2;
		y = screenSize.height/2-getHeight()/2;
		setLocation(x, y);
	}
	
	private void setFrameLook() {
		//ctrlBtnP
		ctrlBtnP.setLayout(null);
		ctrlBtnP.setPreferredSize(new Dimension(400, 110));
		closeBtn.setContentAreaFilled(false);
		closeBtn.setFocusPainted(false);
		closeBtn.setForeground(Color.white);
		closeBtn.setBounds(350, 0, 50, 30);
		minBtn.setContentAreaFilled(false);
		minBtn.setFocusPainted(false);
		minBtn.setForeground(Color.white);
		minBtn.setBounds(300, 0, 50, 30);
		titleL.setBounds(10, 0, 100, 30);
		bgL.setBounds(0, 0, 400, 102);
		ctrlBtnP.add(titleL); ctrlBtnP.add(minBtn); ctrlBtnP.add(closeBtn); ctrlBtnP.add(bgL);	/*���˳��ߵ�����رռ��ᱻbgL��ס*/
		
		//picP
		picP.setPreferredSize(new Dimension(95, 0));
		picP.setLayout(null); //new FlowLayout(FlowLayout.RIGHT)
		headL.setBounds(15, 35, 70, 70);
		headL.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
		picP.add(headL);
		
		//inputP
		inputP.setLayout(null);
		unameL.setBounds(10, 40, 75, 20);
		unameL.setForeground(new Color(0, 0, 0));
		unameL.setFont(new Font("����", 0, 16));		//1��Ӵ�
		pwdL.setBounds(10, 75, 55, 20);
		pwdL.setForeground(new Color(0, 0, 0));
		pwdL.setFont(new Font("����", 0, 16));
		unameTf.setBounds(60, 40, 175, 30);
		pwdF.setBounds(60, 75, 175, 30);
		registerL.setBounds(240, 40, 60, 20);
		forgetPwdL.setBounds(240, 75, 65, 20);
		inputP.add(unameL); inputP.add(pwdL); inputP.add(unameTf); inputP.add(pwdF); inputP.add(registerL); inputP.add(forgetPwdL);
		
		//loginBtnP
		loginBtnP.setLayout(null);
		loginBtnP.setPreferredSize(new Dimension(390, 40));
		loginBtn.setBounds(155, 0, 100, 30);
		loginBtn.setBackground(Color.WHITE);
		loginBtnP.add(loginBtn);
		
		add(ctrlBtnP, BorderLayout.NORTH);
		add(picP, BorderLayout.WEST);
		add(inputP, BorderLayout.CENTER);
		add(loginBtnP, BorderLayout.SOUTH);
	}
	
	private void actionProcessor() {
		minBtn.addActionListener(this);
		closeBtn.addActionListener(this);
		loginBtn.addActionListener(this);
		registerL.addMouseListener(this);
		ctrlBtnP.addMouseListener(this);
		forgetPwdL.addMouseListener(this);
		ctrlBtnP.addMouseMotionListener(this);
		ctrlBtnP.addMouseListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeBtn) {
			System.exit(0);
			con.closeSt();
		} else if (e.getSource() == minBtn) {
			this.setExtendedState(this.ICONIFIED);
		} else  if (e.getSource() == loginBtn) {
			String uname = unameTf.getText();
			String pwd = new String(pwdF.getPassword());
			String temp;
			if (con == null || !con.isConStart()) 		//�п���con���ر��ˣ������󻹴��ڣ����Ի�Ҫ�ж�״̬
				con = new DBCon();
			//System.out.println(pwd+",pwd==null?"+ (pwd == null)+","+("".equals(pwd.trim())) );
			if (pwd == null || "".equals(pwd.trim())) {
				temp = "select * from UserInfo where �û��� = '"+uname.trim()+"' and ���� is null";
			} else {
				temp = "select * from UserInfo where �û��� = '"+uname.trim()+"' and ����='"+pwd.trim()+"'";
			}
			System.out.println(temp+" , "+con.getSelect(con.getSt(), temp));
			if (con.getSelect(con.getSt(), temp).equals("")) {
				JOptionPane.showMessageDialog(null, "��¼ʧ��");
				//con.closeSt();	��¼ʧ����Ҫ���������˺ŵ�¼�������ڴ˾͹ر�
			}else {
				JOptionPane.showMessageDialog(null, "��¼�ɹ�");
				con.closeSt();
				this.setVisible(false);
				new UserMainFrame(new User(uname, pwd));
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == registerL) {
			System.out.println("������ע�ᣬ�ȴ���ת�У���");
		} else if (e.getSource() == forgetPwdL) {
			System.out.println("�������룿��������ע�᣿����");
		}
		//System.out.println("Test");
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent e) {
		startX = e.getX();
		startY = e.getY();
	}

	public void mouseReleased(MouseEvent arg0) {}

	public void mouseDragged(MouseEvent e) {
		endX = e.getX();
		endY = e.getY();
		int locX = endX - startX + getLocation().x;
		int locY = endY - startY + getLocation().y; 
		this.setLocation(locX, locY);
	}
	
	public void mouseMoved(MouseEvent arg0) {}
}
