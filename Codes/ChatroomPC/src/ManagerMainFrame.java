import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ManagerMainFrame extends JFrame implements ListSelectionListener, MouseListener {
	//JPanel inputP, searchResultP, btnP, nameP, pwdP;
	JLabel welcomeL;
	//JTextField tableTf, unameTf, pwdTf, identityTf;
	//JButton insertBtn, changeBtn, deleteBtn, searchBtn;
	JList tableList;
	JScrollPane listScroll;
	Toolkit kit;
	Dimension screenSize;
	int x, y;	//��int�򱨴������java�ļ�û�д�����
	String name="";	//����Ա�ʻ���������
	String[] tableListString = new String[10];
	
	ManagerMainFrame(User UserInfo) {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		setSize(400, 500);
		setTitle("����Ա����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		welcomeL = new JLabel("��ӭ����Ա"+name+"����,��ѡ�����б���й���");
		tableListString[0] = "UserInfo";
		tableList = new JList(tableListString);
		listScroll = new JScrollPane(tableList);
		kit = Toolkit.getDefaultToolkit();
		screenSize = kit.getScreenSize();
		x = (int)(screenSize.getWidth() - getWidth()) / 2;
		y = (int)(screenSize.getHeight() - getHeight()) / 2;
		setLocation(x, y);
	}
	
	public void setFrameLook() {
		add(welcomeL, BorderLayout.NORTH);
		add(listScroll, BorderLayout.CENTER);
	}
	
	public void actionProcessor() {
		tableList.addListSelectionListener(this);
		tableList.addMouseListener(this);
	}

	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == tableList) {
			System.out.println("tableList "+ tableList.getSelectedIndex());
			//new ManagerManageTableFrame();
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			this.setVisible(false);
			//System.out.println(""+( (JList)(e.getSource()) ).getSelectedValuesList());
			String title = ( (JList)(e.getSource()) ).getSelectedValuesList().toString();
			ManagerManageTableFrame manFrame = new ManagerManageTableFrame();
			manFrame.setTitle(title + "��");
		}
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
}
