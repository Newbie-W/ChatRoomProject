package chat;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.*;

/*ע��֮�������������ݲ���Ϊ�գ�
 * ����maxNum��portֻ��Ϊ�������������ַ���������ʱ�ɲ������
 * ����һ�����ӣ������ӿͻ��ˣ��ٹرպ��������������������������߳�δ�رգ�ѭ�������������⣩��
 * ��ֹ�߳���������
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
		portL = new JLabel("�˿�");
		maxNumL = new JLabel("�������");
		portTf = new JTextField("8888");
		maxNumTf = new JTextField("30");
		startBtn = new JButton("����");
		stopBtn = new JButton("ֹͣ");
		stopBtn.setEnabled(false);
		msgSendTf.setEditable(false);
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
					//System.out.println("�������ң�"+msgSendTf.getText());
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
				JOptionPane.showMessageDialog(this, "�������Ѿ�����", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int port = Integer.parseInt(portTf.getText());
			int maxNum = Integer.parseInt(maxNumTf.getText());
			if (port <= 2048 || maxNum<1) {
				JOptionPane.showMessageDialog(this, "�������ò���ȷ���˿�Ҫ>2048���������������>=1");
				//return�ˣ�Ҳ�����к�����룿��
			} else {
				serverStart(maxNum, port);
				if (isStart) {			/*��ֹsSocket�����쳣����ִ�к�������*/
					startBtn.setEnabled(false);
					maxNumTf.setEditable(false);
					portTf.setEditable(false);
					stopBtn.setEnabled(true);
					msgSendTf.setEditable(true);
				}
			}
		} else if (e.getSource() == stopBtn) {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "��������δ����", "����", JOptionPane.ERROR_MESSAGE);
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
							if ( isStart==true && sSocket!=null && !sSocket.isClosed()) //����������д������
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
				out.writeUTF("Server:�޷����ӣ�����������\n");
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
							//System.out.println("����serverService��");
							in = new DataInputStream(cSocket.getInputStream());
							out = new DataOutputStream(cSocket.getOutputStream());
							out.writeUTF("���:���Ƿ�����,���ӳɹ���");
							String s;
							while (true) {
								s = in.readUTF();
								//System.out.println("�յ��ͻ��˵�һ����Ϣ"+s+",end?"+"end\n".equals(s));
								if (s!=null) contentTa.append(s+"\n");
								//out.writeUTF("���Ƿ������������յ�");
								//if (s!=null) break;
								if ("end\n".equals(s)||"end".equals(s)) {
									out.writeUTF("end");
									contentTa.append("�ر�\n");
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
				//clients.get(i).  ��ֹ�߳�����
				clients.remove(i);
				
				System.out.println("ѭ���� Still start?"+isStart);
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
			JOptionPane.showMessageDialog(this, "�ɹ��رշ�����ServerSocket");
		}
	}
	
	public void sendMessage() {
		if (!isStart) {
			JOptionPane.showMessageDialog(this, "��������δ����");
			return ;
		}
		if (clients.size() == 0) {
			JOptionPane.showMessageDialog(this, "�����û�����");
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
	
	public String encapsulateMsg(String s) {		//�趨��Ϣ��ʽ    �������ǹر���һ�����ֻ��CLOSE��@@@������@@@������Ϣ��@@@��Ϣ����
		//�����һ���޶�
		/*������Ϣ
		 * �ر����ӣ� 		CLOSE
		 * ˽��			PLOGIN@@@������@@@������@@@IP
		 * ��¼�� 			LOGIN@@@�û���@@@IP@@@PORT
		 * ����Ⱥ����Ϣ �� 	MESSAGE@@@������@@@ALL@@@��Ϣ����
		 * ����˽����Ϣ��  	MESSAGE@@@������@@@������@@@��Ϣ����
		 */
		String msg = "";
		if ("CLOSE".equals(s)) {
			msg = "CLOSE";
			System.out.println("����������ر�");
		} else if ("".equals(s)) {
			
		} else if ("HELLO".equals(s)) {
			System.out.println("��ӭ");
		} else {
			msg = "MESSAGE" + "@@@" + "Server"+ "@@@ALL@@@" + s;
		}
		//System.out.println("------encapsulate��"+msg);
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
				outStream.writeUTF("Server:���,���Ƿ�����,���ӳɹ���");
				String s;
				while (true) {
					s = inStream.readUTF();
					s = decapsulateMsg(s);
					//System.out.println("�յ��ͻ��˵�һ����Ϣ:"+s);
					if (s!=null) contentTa.append(s+"\n");
					//if (s!=null) break;
					if ("end\n".equals(s)||"end".equals(s)) {
						outStream.writeUTF("end");
						contentTa.append("�ر�\n");
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
				System.out.println("�ر�����");
				user.changeOnlineState();
			} else if ("LOGIN".equals(orderName)) {
				sender = orderMsg[1];
				receiver = orderMsg[2];
				message = orderMsg[3];			//�������У�receiverΪip��messageΪport
				getUser().setUname(sender);
				getUser().setIP(""+receiver);
				sendMessageToAll("Server:���û�����"+getUser().getUname());
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
				/*�������ã�Ŀǰ���ᱻִ�е�
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