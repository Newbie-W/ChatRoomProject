import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener, MouseListener,MouseMotionListener {
	JPanel CtrlBtnP, PicP, InputP, LoginBtnP;//
	ImageIcon Img, HeadImg;	//, LogoImg
	JLabel BgL, HeadL, RegisterL, ForgetPwdL, UnameL, PwdL, TitleL;	//, LogoL
	JTextField UnameTf;
	JPasswordField PwdF;
	JButton CloseBtn, MinBtn, LoginBtn;
	Toolkit Kit;
	Dimension ScreenSize;
	int x, y, StartX, StartY, EndX, EndY;
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
		CtrlBtnP = new JPanel();
		PicP = new JPanel();
		InputP = new JPanel();
		LoginBtnP = new JPanel();
		Img = new ImageIcon("qq1.png");
		HeadImg = new ImageIcon("1.jpg");
		BgL =  new JLabel(Img);
		HeadL = new JLabel(HeadImg);
		RegisterL = new JLabel("ע���˺�");
		ForgetPwdL = new JLabel("�������룿");
		RegisterL.setFont(new Font("����", 0, 11));
		ForgetPwdL.setFont(new Font("����", 0, 11));
		RegisterL.setForeground(new Color(128, 128, 255));
		ForgetPwdL.setForeground(new Color(128, 128, 255));
		UnameL = new JLabel("�û���");
		PwdL = new JLabel("����");
		TitleL = new JLabel("WeTalk");
		TitleL.setFont(new Font("����", 1, 16));
		TitleL.setForeground(Color.lightGray);
		UnameTf = new JTextField();
		PwdF = new JPasswordField();
		CloseBtn =  new JButton("X");
		MinBtn = new JButton("��");
		LoginBtn = new JButton("��¼");
		Kit = Toolkit.getDefaultToolkit();
		ScreenSize = Kit.getScreenSize();
		x = (ScreenSize.width-getWidth())/2;
		y = ScreenSize.height/2-getHeight()/2;
		setLocation(x, y);
	}
	
	private void setFrameLook() {
		//CtrlBtnP
		CtrlBtnP.setLayout(null);
		CtrlBtnP.setPreferredSize(new Dimension(400, 110));
		CloseBtn.setContentAreaFilled(false);
		CloseBtn.setFocusPainted(false);
		CloseBtn.setForeground(Color.white);
		CloseBtn.setBounds(350, 0, 50, 30);
		MinBtn.setContentAreaFilled(false);
		MinBtn.setFocusPainted(false);
		MinBtn.setForeground(Color.white);
		MinBtn.setBounds(300, 0, 50, 30);
		TitleL.setBounds(10, 0, 100, 30);
		BgL.setBounds(0, 0, 400, 102);
		CtrlBtnP.add(TitleL); CtrlBtnP.add(MinBtn); CtrlBtnP.add(CloseBtn); CtrlBtnP.add(BgL);	/*���˳��ߵ�����رռ��ᱻBgL��ס*/
		
		//PicP
		PicP.setPreferredSize(new Dimension(95, 0));
		PicP.setLayout(null); //new FlowLayout(FlowLayout.RIGHT)
		HeadL.setBounds(15, 35, 70, 70);
		HeadL.setBorder(BorderFactory.createLineBorder(Color.lightGray, 3));
		PicP.add(HeadL);
		
		//InputP
		InputP.setLayout(null);
		UnameL.setBounds(10, 40, 75, 20);
		UnameL.setForeground(new Color(0, 0, 0));
		UnameL.setFont(new Font("����", 0, 16));		//1��Ӵ�
		PwdL.setBounds(10, 75, 55, 20);
		PwdL.setForeground(new Color(0, 0, 0));
		PwdL.setFont(new Font("����", 0, 16));
		UnameTf.setBounds(60, 40, 175, 30);
		PwdF.setBounds(60, 75, 175, 30);
		RegisterL.setBounds(240, 40, 60, 20);
		ForgetPwdL.setBounds(240, 75, 65, 20);
		InputP.add(UnameL); InputP.add(PwdL); InputP.add(UnameTf); InputP.add(PwdF); InputP.add(RegisterL); InputP.add(ForgetPwdL);
		
		//LoginBtnP
		LoginBtnP.setLayout(null);
		LoginBtnP.setPreferredSize(new Dimension(390, 40));
		LoginBtn.setBounds(155, 0, 100, 30);
		LoginBtn.setBackground(Color.WHITE);
		LoginBtnP.add(LoginBtn);
		
		add(CtrlBtnP, BorderLayout.NORTH);
		add(PicP, BorderLayout.WEST);
		add(InputP, BorderLayout.CENTER);
		add(LoginBtnP, BorderLayout.SOUTH);
	}
	
	private void actionProcessor() {
		MinBtn.addActionListener(this);
		CloseBtn.addActionListener(this);
		LoginBtn.addActionListener(this);
		RegisterL.addMouseListener(this);
		CtrlBtnP.addMouseListener(this);
		ForgetPwdL.addMouseListener(this);
		CtrlBtnP.addMouseMotionListener(this);
		CtrlBtnP.addMouseListener(this);
		addMouseMotionListener(this);
		addMouseListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == CloseBtn) {
			System.exit(0);
			con.closeSt();
		} else if (e.getSource() == MinBtn) {
			this.setExtendedState(this.ICONIFIED);
		} else  if (e.getSource() == LoginBtn) {
			String Uname = UnameTf.getText();
			String Pwd = new String(PwdF.getPassword());
			if (con == null) con = new DBCon();		//�Ҽ����if�Ƿ��Ҫ�أ�
			String temp = "select * from UserInfo where �û��� = '"+Uname+"' and ����='"+Pwd+"'";
			System.out.println(temp+" , "+con.getSelect(con.getSt(), temp));
			if (con.getSelect(con.getSt(), temp) == "") {
				JOptionPane.showMessageDialog(null, "��¼ʧ��");
			}else {
				JOptionPane.showMessageDialog(null, "��¼�ɹ�");
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == RegisterL) {
			System.out.println("������ע�ᣬ�ȴ���ת�У���");
		} else if (e.getSource() == ForgetPwdL) {
			System.out.println("�������룿��������ע�᣿����");
		}
		//System.out.println("Test");
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent e) {
		StartX = e.getX();
		StartY = e.getY();
	}

	public void mouseReleased(MouseEvent arg0) {}

	public void mouseDragged(MouseEvent e) {
		EndX = e.getX();
		EndY = e.getY();
		int locX = EndX - StartX + getLocation().x;
		int locY = EndY - StartY + getLocation().y; 
		this.setLocation(locX, locY);
	}
	
	public void mouseMoved(MouseEvent arg0) {}
}
