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
	int x, y;	//改int则报错，但别的java文件没有此问题
	String name="";	//管理员帐户名，备用
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
		setTitle("管理员界面");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		welcomeL = new JLabel("欢迎管理员"+name+"进入,请选择下列表进行管理");
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
			manFrame.setTitle(title + "表");
		}
	}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}

	public void mousePressed(MouseEvent arg0) {}

	public void mouseReleased(MouseEvent arg0) {}
}
