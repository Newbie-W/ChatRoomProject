import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

/*注意之后完善输入内容不能为空；
 * 还有maxNum、port只能为正整数（不是负的、0、字符串）
 * */
public class ServerChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, maxNumL;
	JTextField portTf, maxNumTf;
	JButton startBtn, stopBtn;
	ServerSocket sSocket;
	Socket cSocket;
	DataInputStream in;
	DataOutputStream out;
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
		portL = new JLabel("端口");
		maxNumL = new JLabel("最大人数");
		portTf = new JTextField("8888");
		maxNumTf = new JTextField("30");
		startBtn = new JButton("启动");
		stopBtn = new JButton("停止");
		//stopBtn.setEnabled(false);
	}
	
	public void setFrameLook() {
		super.setFrameLook();
		setTitle("服务器");
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
		stopBtn.addActionListener(this);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				if (isStart) {
					serverClose();
				}
				System.exit(0);
			}
		});
		msgSendTf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//contentTa.append(msgSendTf.getText()+"\n");
				try {
					System.out.println("服务器我："+msgSendTf.getText());
					out.writeUTF(msgSendTf.getText()+"\n");
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}
				msgSendTf.setText("");
				
			}
		});
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startBtn) {
			if (isStart) {
				JOptionPane.showMessageDialog(this, "服务器已经启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int maxNum;
			int port = Integer.parseInt(portTf.getText());
			maxNum = Integer.parseInt(maxNumTf.getText());
			//TestEnv();
			serverStart(maxNum, port);
			if (isStart) {			/*防止sSocket建立异常，还执行后续操作*/
				startBtn.setEnabled(false);
				maxNumTf.setEditable(false);
				portTf.setEditable(false);
				stopBtn.setEnabled(true);
			}
		} else if (e.getSource() == stopBtn) {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "服务器尚未启动", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			//serverClose();
			stopBtn.setEnabled(false);
			startBtn.setEnabled(true);
			maxNumTf.setEditable(true);
			portTf.setEditable(true);
		}
	}
	
	public void TestEnv() {
		
	}
	
	public void serverStart(int maxNum, int port) {
		//Clients = new ArrayList<>();
		try {
			sSocket = new ServerSocket(port);
			//JOptionPane.showMessageDialog(this, "成功建立服务器ServerSocket");//后续加上accept阻塞，本句放前面，后面内容则无效??setVisible也不行，放后面则可以
			startBtn.setEnabled(false);
			maxNumTf.setEditable(false);
			portTf.setEditable(false);
			stopBtn.setEnabled(true);
			//JOptionPane.showMessageDialog(this, "成功建立服务器ServerSocket");
			serverService();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			JOptionPane.showMessageDialog(this, "端口号已被占用，请更改");
			System.out.println(e.getMessage());
		} catch (Exception e) {
			isStart = false;
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void serverService() {
		//while (true) {
			try {
				cSocket = sSocket.accept();
				in = new DataInputStream(cSocket.getInputStream());
				out = new DataOutputStream(cSocket.getOutputStream());
				/*BufferedReader reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(cSocket.getOutputStream());
				String message = reader.readLine();
				writer.println(message);  
		        writer.flush();*/
				out.writeUTF("你好:我是服务器,连接成功。");
				String s;
				//int i=0;
				while (true) {
					s = in.readUTF();
					System.out.println("收到客户端的一条消息"+s+",end?"+"end\n".equals(s));
					contentTa.append(s);
					out.writeUTF("我是服务器，我已收到");
					//if (s!=null) break;
					if ("end\n".equals(s)||"end".equals(s)){out.writeUTF("end");contentTa.append("关闭");break;}
				}
				//out.writeUTF("你好:我是服务器,连接成功。");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		//}
	}
	
	public void serverClose() {
		//if (isStart)
		
		JOptionPane.showMessageDialog(this, "成功关闭服务器ServerSocket");
		isStart = false;
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
