package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/*注意之后完善输入内容不能为空；
 * 还有maxNum、port只能为正整数（不是字符串）（临时可不解决）
 * 启动一次连接，不连接客户端，再关闭后启动，则启动报错（怀疑是线程未关闭，循环还在运行问题）。
 * 终止线程运行问题
 * */
public class ServerChatroomFrame extends ChatFrame implements ActionListener {
	JLabel portL, maxNumL;
	JTextField portTf, maxNumTf;
	JButton startBtn, stopBtn;
	ServerSocket sSocket;
	Socket cSocket;
	//DataInputStream in;
	//DataOutputStream out;
	//ServerThread sThread;
	ClientThread msgThread;
	ArrayList<ClientThread> clients;
	
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
		stopBtn.setEnabled(false);
		msgSendTf.setEditable(false);
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
		
		sendBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
			int port = Integer.parseInt(portTf.getText());
			int maxNum = Integer.parseInt(maxNumTf.getText());
			if (port <= 2048 || maxNum<1) {
				JOptionPane.showMessageDialog(this, "数据设置不正确，端口要>2048，最大容纳量至少>=1");
				//return了，也会运行后面代码？？
			} else {
				serverStart(maxNum, port);
				if (isStart) {			/*防止sSocket建立异常，还执行后续操作*/
					startBtn.setEnabled(false);
					maxNumTf.setEditable(false);
					portTf.setEditable(false);
					stopBtn.setEnabled(true);
					msgSendTf.setEditable(true);
				}
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
			msgSendTf.setEditable(false);
		}
	}
	
	public void serverStart(final int maxNum, final int port) {
		clients = new ArrayList<ClientThread>();
		try {
			Thread t = new Thread(new Runnable() {
				public void run() {
					try {
						sSocket = new ServerSocket(port);
					} catch (IOException e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
					
					while(true) {
						try {
							if ( isStart==true && sSocket!=null && !sSocket.isClosed()) //有用吗？这样写合理吗？
								cSocket = sSocket.accept();
							else break;
						} catch (IOException e) {
							e.printStackTrace();
						}
						if ( isClientsNumMax(cSocket, maxNum) ) {
							continue;
						} else {
							ClientThread client = new ClientThread(cSocket);
							client.start();
							clients.add(client);
						}
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
	
	public boolean isClientsNumMax(Socket socket, int max) {
		if (clients.size() >= max) {
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.writeUTF("Server:无法连接！服务器爆满\n");
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	/*public void serverService(final Socket cSocket) {
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
				
				//*BufferedReader reader = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
				PrintWriter writer = new PrintWriter(cSocket.getOutputStream());
				String message = reader.readLine();
				writer.println(message);  
		        writer.flush();//*
				
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		//}
	}*/
	
	public void closeConnect() {
		
		for (int i=clients.size()-1; i>=0; i--) {
			try {
				clients.get(i).outStream.close();
				clients.get(i).inStream.close();
				clients.get(i).socket.close();
				//clients.get(i).  终止线程运行
				clients.remove(i);
				
				System.out.println("循环中 Still start?"+isStart);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (sSocket != null || isStart==true)
			try {
				sSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		isStart = false;
	}
	
	public void serverClose() {
		if (isStart) {
			System.out.println("start?"+isStart);
			closeConnect();
			JOptionPane.showMessageDialog(this, "成功关闭服务器ServerSocket");
		}
	}
	
	public void sendMessage() {
		if (!isStart) {
			JOptionPane.showMessageDialog(this, "服务器尚未启动");
			return ;
		}
		if (clients.size() == 0) {
			JOptionPane.showMessageDialog(this, "暂无用户连接");
			return ;
		}
		
		String message = msgSendTf.getText();
		//
		sendMessageToAll();
		contentTa.append(message);
	}
	
	public void sendMessageToAll() {
		String message = msgSendTf.getText();
		for (int i = clients.size()-1; i >= 0; i--) {
			try {
				clients.get(i).getOut().writeUTF( encapsulateMsg(message) );
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
	
	public void sendMessageToAll(String s) {
		for (int i = clients.size()-1; i >= 0; i--) {
			try {
				clients.get(i).getOut().writeUTF(s);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
	
	public void sendMessageToPerson(String sender, String receiver) {
		String message = msgSendTf.getText();
		for (int i = clients.size()-1; i>=0; i--) {
			try {
				if(sender.equals(clients.get(i).getUser().getUname()) || receiver.equals(clients.get(i).getUser().getUname()))
					clients.get(i).getOut().writeUTF(message);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
	
	public void sendMessageToPerson(String sender, String receiver,String s) {
		for (int i = clients.size()-1; i>=0; i--) {
			try {
				//System.out.println(name+"equals"+clients.get(i).getUser().getUname()+"?"+name.equals(clients.get(i).getUser().getUname()));
				if(sender.equals(clients.get(i).getUser().getUname()) || receiver.equals(clients.get(i).getUser().getUname()))
					clients.get(i).getOut().writeUTF(s);
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
	
	public String encapsulateMsg(String s) {		//设定信息格式    命令（如果是关闭这一类的则，只是CLOSE）@@@发送者@@@接收信息者@@@信息内容
		//还需进一步修订
		/*发送信息
		 * 关闭连接： 		CLOSE
		 * 私聊			PLOGIN@@@发送人@@@接收人@@@IP
		 * 登录： 			LOGIN@@@用户名@@@IP@@@PORT
		 * 发送群聊信息 ： 	MESSAGE@@@发送人@@@ALL@@@信息内容
		 * 发送私聊信息：  	MESSAGE@@@发送人@@@接收人@@@信息内容
		 */
		String msg = "";
		if ("CLOSE".equals(s)) {
			msg = "CLOSE";
			System.out.println("服务器打算关闭");
		} else if ("".equals(s)) {
			
		} else if ("HELLO".equals(s)) {
			System.out.println("欢迎");
		} else {
			msg = "MESSAGE" + "@@@" + "Server"+ "@@@ALL@@@" + s;
		}
		//System.out.println("------encapsulate："+msg);
		return msg;
	}
	
	public class ClientThread extends Thread {
		Socket socket;
		DataInputStream inStream;
		DataOutputStream outStream;
		User user;
		
		ClientThread(Socket cSocket) {
			socket = cSocket;
			user = new User();
			user.changeOnlineState();
			System.out.println(user.getOnlineState());
			try {
				inStream = new DataInputStream(socket.getInputStream());
				outStream = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		public void run() {
			try {
				outStream.writeUTF("Server:你好,我是服务器,连接成功。");
				String s;
				while (true) {
					s = inStream.readUTF();
					s = decapsulateMsg(s);
					//System.out.println("收到客户端的一条消息:"+s);
					if (s!=null) contentTa.append(s+"\n");
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

		public void deliverMsg() {
			
		}
		
		public String decapsulateMsg(String s) {
			String msg = s;
			String sender = "";
			String receiver = "";
			String message = "";
			String orderMsg[] = msg.split("@@@");
			int length = orderMsg.length;
			String orderName = orderMsg[0];
			//System.out.println("-----"+orderName+"  "+s);
			if (length >= 2)
				sender = orderMsg[1];
			if (length >= 3) 
				receiver = orderMsg[2];
			if (length >= 4)
				message = orderMsg[3];
			if ("CLOSE".equals(orderName)) {
				msg = "CLOSE";
				System.out.println("关闭连接");
				user.changeOnlineState();
			} else if ("LOGIN".equals(orderName)) {
				sender = orderMsg[1];
				receiver = orderMsg[2];
				message = orderMsg[3];			//此命令中，receiver为ip，message为port
				getUser().setUname(sender);
				getUser().setIP(""+receiver);
				sendMessageToAll("Server:新用户上线"+getUser().getUname());
			} else if ("MESSAGE".equals(orderName)) {
				sender = orderMsg[1];
				receiver = orderMsg[2];
				message = orderMsg[3];
				//System.out.println("-----"+sender+ " " + receiver + " " + message + "-----");
				if ( "ALL".equals(receiver) ) {
					sendMessageToAll(s);
					msg = sender+" (To ALL) : "+message;
				} else {
					sendMessageToPerson(sender, receiver, s);
					msg = sender+" (To "+ receiver +") : "+message;
				}
				/*本条备用，目前不会被执行到
				 else if ("Server".equals(sender)) {
					System.out.println("sender:Server");
					msg = sender+" (To ALL) : "+message;
				} else if ("Server".equals(receiver))  {	
					System.out.println("sendtoALL");
					msg = s;
					sendMessageToAll(s);
				}*/
			}
			System.out.println(msg);
			return msg;
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
