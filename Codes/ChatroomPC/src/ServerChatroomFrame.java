import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

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
	//ServerThread sThread;
	MessageThread msgThread;
	ArrayList<MessageThread> clients;
	
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
				/*try {
					//System.out.println("服务器我："+msgSendTf.getText());
					String content = msgSendTf.getText();
					contentTa.append("Server:" + content);//+"\n"
					out.writeUTF("Server:" + content);
				} catch (IOException e1) {
					System.out.println(e1.getMessage());
					e1.printStackTrace();
				}*/
				sendMessage();
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
			//testEnv();
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
			serverClose();
			stopBtn.setEnabled(false);
			startBtn.setEnabled(true);
			maxNumTf.setEditable(true);
			portTf.setEditable(true);
		}
	}
	
	public void TestEnv() {
		
	}
	
	public void serverStart(int maxNum, final int  port) {
		clients = new ArrayList<MessageThread>();
		try {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						//System.out.println("server----");
						sSocket = new ServerSocket(port);
					} catch (IOException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					
					while(true) {
						try {
							cSocket = sSocket.accept();
						} catch (IOException e) {
							e.printStackTrace();
						}
						JOptionPane.showMessageDialog(null , "一位用户已连接");
						//serverService(cSocket);
						MessageThread client = new MessageThread(cSocket);
						client.start();
						//out = client.getOut();
						clients.add(client);
						System.out.println("clients.size=" + clients.size());
					}	
				}
			});
			t.setDaemon(true);
			t.start();
			startBtn.setEnabled(false);
			maxNumTf.setEditable(false);
			portTf.setEditable(false);
			stopBtn.setEnabled(true);
			isStart = true;
			
		}  catch (Exception e) {
			isStart = false;
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void serverService(final Socket cSocket) {
		//while (true) {
			try {
				new Thread(new Runnable(){

					public void run() {
						try {
							//System.out.println("运行serverService中");
							in = new DataInputStream(cSocket.getInputStream());
							out = new DataOutputStream(cSocket.getOutputStream());
							out.writeUTF("你好:我是服务器,连接成功。");
							String s;
							while (true) {
								s = in.readUTF();
								//System.out.println("收到客户端的一条消息"+s+",end?"+"end\n".equals(s));
								if (s!=null) contentTa.append(s+"\n");
								//out.writeUTF("我是服务器，我已收到");
								//if (s!=null) break;
								if ("end\n".equals(s)||"end".equals(s)) {
									out.writeUTF("end");
									contentTa.append("关闭\n");
									break;
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					 }
				}).start();
				
				/*BufferedReader reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(cSocket.getOutputStream());
				String message = reader.readLine();
				writer.println(message);  
		        writer.flush();*/
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		//}
	}
	
	public void closeConnect() {
		try {
			out.close();
			in.close();
			cSocket.close();
			sSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void serverClose() {
		//if (isStart)
		
		JOptionPane.showMessageDialog(this, "成功关闭服务器ServerSocket");
		closeConnect();
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
		sendMessageToAll();
		contentTa.append(message);
	}
	
	public void sendMessageToAll() {
		if (!isStart) {
			JOptionPane.showMessageDialog(this, "服务器尚未启动");
			return ;
		}
		//if (Clients.size() == 0)
		
		String message = msgSendTf.getText();
		for (int i = clients.size()-1; i >= 0; i--) {
			try {
				clients.get(i).getOut().writeUTF("Server:" + message);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
	
	public class MessageThread extends Thread {
		Socket socket;
		DataInputStream inStream;
		DataOutputStream outStream;
		User user;
		
		MessageThread(Socket cSocket) {
			socket = cSocket;
			try {
				inStream = new DataInputStream(socket.getInputStream());
				outStream = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			/**/for (int i = clients.size()-1; i >= 0; i--) {
				System.out.println("clients.size=" + clients.size());
				try {
					clients.get(i).getOut().writeUTF("Server:连接成功，新用户上线");
				} catch (IOException e) {
					e.printStackTrace();
				};
			}
		}

		public void run() {
			try {
				
				outStream.writeUTF("Server:你好:我是服务器,连接成功。");
				System.out.println("服务器接收消息 在运行中");
				String s;
				while (true) {
					s = inStream.readUTF();
					System.out.println("收到客户端的一条消息"+s+",end?"+"end\n".equals(s));
					if (s!=null) contentTa.append(s+"\n");
					//out.writeUTF("我是服务器，我已收到");
					//if (s!=null) break;
					if ("end\n".equals(s)||"end".equals(s)) {
						outStream.writeUTF("end");
						contentTa.append("关闭\n");
						break;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public DataInputStream getIn() {
			return inStream;
		}

		public DataOutputStream getOut() {
			return outStream;
		}
		
		public User getUser() {
			return user;
		}
	}
}
