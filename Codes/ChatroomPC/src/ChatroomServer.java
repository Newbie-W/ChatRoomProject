import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;
import javax.swing.*;

public class ChatroomServer extends ChatFrame implements ActionListener {
	JLabel PortL, MaxNumL;
	JTextField PortTf, MaxNumTf;
	JButton StartBtn, StopBtn;
	ServerSocket SSocket;
	//ServerThread SThread;
	//ArrayList<> Clients;
	
	boolean isStart = false;
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new ChatroomServer();
		//new ChatFrame();
		//new GroupChatroomFrame();
	}
	
	ChatroomServer() {
		initFrame();
		setFrameLook();
		actionProcessor();
		setVisible(true);
		validate();
	}
	
	public void initFrame() {
		super.initFrame();
		PortL = new JLabel("端口");
		MaxNumL = new JLabel("最大人数");
		PortTf = new JTextField("8888");
		MaxNumTf = new JTextField("30");
		StartBtn = new JButton("启动");
		StopBtn = new JButton("停止");
		//StopBtn.setEnabled(false);
		
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		PortL.setBounds(0, 0, 60, 30); PortTf.setBounds(65, 0, 120, 30);
		MaxNumL.setBounds(0, 35, 60, 30); MaxNumTf.setBounds(65, 35, 120, 30);
		StartBtn.setBounds(0, 80, 70, 30); StopBtn.setBounds(75, 80, 70, 30);
		InfoP.add(PortL); InfoP.add(PortTf);
		InfoP.add(MaxNumL); InfoP.add(MaxNumTf);
		InfoP.add(StartBtn); InfoP.add(StopBtn);
		
		SideBarSplitP.setDividerLocation(120);
	}
	
	public void actionProcessor() {
		super.actionProcessor();
		StartBtn.addActionListener(this);
		System.out.println("this is chatserver");
	}
	
	public void actionPerformed(ActionEvent e) {
		System.out.println("事件响应"+e.getActionCommand()+PortTf.getText());
		if (e.getSource() == StartBtn) {
			System.out.println("成功建立服务器Socket"+PortTf.getText());
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int MaxNum;
			int Port = Integer.parseInt(PortL.getText());
			MaxNum = Integer.parseInt(MaxNumTf.getText());
			TestEnv();
			serverStart(MaxNum, Port);
		}
	}
	
	public void TestEnv() {
		
	}
	
	public void serverStart(int MaxNum, int Port) {
		//Clients = new ArrayList<>();
		try {
			SSocket = new ServerSocket(Port);
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
		
		String Message = MsgSendTf.getText();
		//
	}
}
