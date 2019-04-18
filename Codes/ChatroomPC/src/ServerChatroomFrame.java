import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;

/*ע��֮�������������ݲ���Ϊ��*/
public class ServerChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, maxNumL;
	JTextField portTf, maxNumTf;
	JButton startBtn, stopBtn;
	ServerSocket sSocket;
	//ServerThread SThread;
	//ArrayList<> Clients;
	
	boolean isStart = false;
	public static void main(String[] args) {
		new ServerChatroomFrame();
		//new ChatFrame();
		//new GroupChatroomFrame();
	}
	
	ServerChatroomFrame() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		super.initFrame();
		portL = new JLabel("�˿�");
		maxNumL = new JLabel("�������");
		portTf = new JTextField("8888");
		maxNumTf = new JTextField("30");
		startBtn = new JButton("����");
		stopBtn = new JButton("ֹͣ");
		//stopBtn.setEnabled(false);
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		setTitle("������");
		portL.setBounds(0, 0, 60, 30); portTf.setBounds(65, 0, 120, 30);
		maxNumL.setBounds(0, 35, 60, 30); maxNumTf.setBounds(65, 35, 120, 30);
		startBtn.setBounds(0, 80, 70, 30); stopBtn.setBounds(75, 80, 70, 30);
		super.infoP.add(portL); super.infoP.add(portTf);
		super.infoP.add(maxNumL); super.infoP.add(maxNumTf);
		super.infoP.add(startBtn); super.infoP.add(stopBtn);
		
		sideBarSplitP.setDividerLocation(120);
	}
	
	public void actionProcessor() {
		super.actionProcessor();
		startBtn.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBtn) {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "�������Ѿ�����", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int maxNum;
			int port = Integer.parseInt(portTf.getText());
			maxNum = Integer.parseInt(maxNumTf.getText());
			TestEnv();
			serverStart(maxNum, port);
		}
	}
	
	public void TestEnv() {
		
	}
	
	public void serverStart(int MaxNum, int Port) {
		//Clients = new ArrayList<>();
		try {
			sSocket = new ServerSocket(Port);
			System.out.println("�ɹ�����������Socket");
			isStart = true;
		} catch (BindException e) {
			isStart = false;
		} catch (Exception e) {
			isStart = false;
			e.printStackTrace();
		}
	}
	
	public void serverClose() {
		//if ()
		
	}
	
	public void sendMessage() {
		if (!isStart) {
			JOptionPane.showMessageDialog(this, "��������δ����");
			return ;
		}
		//if (Clients.size() == 0)
		
		String message = msgSendTf.getText();
		//
	}
}
