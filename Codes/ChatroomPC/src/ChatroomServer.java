import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;

public class ChatroomServer extends ChatFrame implements ActionListener {
	JLabel portL, maxNumL;
	JTextField portTf, maxNumTf;
	JButton startBtn, stopBtn;
	ServerSocket sSocket;
	//ServerThread SThread;
	//ArrayList<> Clients;
	
	boolean isStart = false;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new ChatroomServer();
		//new ChatFrame();
		//new GroupChatroomFrame();
	}
	
	public void nitFrame() {
		//super.initFrame();
		portL = new JLabel("端口");
		maxNumL = new JLabel("最大人数");
		portTf = new JTextField("8888");
		maxNumTf = new JTextField("30");
		startBtn = new JButton("启动");
		stopBtn = new JButton("停止");
		//stopBtn.setEnabled(false);
		
	}
	
	public void etFrameLook() {
		//super.setFrameLook();
		portL.setBounds(0, 0, 60, 30); portTf.setBounds(65, 0, 120, 30);
		maxNumL.setBounds(0, 35, 60, 30); maxNumTf.setBounds(65, 35, 120, 30);
		startBtn.setBounds(0, 80, 70, 30); stopBtn.setBounds(75, 80, 70, 30);
		super.infoP.add(portL); super.infoP.add(portTf);
		super.infoP.add(maxNumL); super.infoP.add(maxNumTf);
		super.infoP.add(startBtn); super.infoP.add(stopBtn);
		
		sideBarSplitP.setDividerLocation(120);
	}
	
	public void ctionProcessor() {
		//super.actionProcessor();
		startBtn.addActionListener(this);
		System.out.println("this is chatserver");
	}
	
	ChatroomServer() {
		nitFrame();
		etFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("事件响应"+e.getSource()+portTf.getText());
		if (e.getSource() == startBtn) {
			System.out.println("成功建立服务器Socket"+portTf.getText());
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int maxNum;
			int port = Integer.parseInt(portL.getText());
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
			System.out.println("成功建立服务器Socket");
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
			JOptionPane.showMessageDialog(this, "服务器尚未启动");
			return ;
		}
		//if (Clients.size() == 0)
		
		String message = msgSendTf.getText();
		//
	}
}
