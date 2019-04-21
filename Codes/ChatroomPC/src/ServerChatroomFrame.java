import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.*;

/*ע��֮�������������ݲ���Ϊ�գ�
 * ����maxNum��portֻ��Ϊ�����������Ǹ��ġ�0���ַ�����
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
					System.out.println("�������ң�"+msgSendTf.getText());
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
				JOptionPane.showMessageDialog(this, "�������Ѿ�����", "����", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int maxNum;
			int port = Integer.parseInt(portTf.getText());
			maxNum = Integer.parseInt(maxNumTf.getText());
			//TestEnv();
			serverStart(maxNum, port);
			if (isStart) {			/*��ֹsSocket�����쳣����ִ�к�������*/
				startBtn.setEnabled(false);
				maxNumTf.setEditable(false);
				portTf.setEditable(false);
				stopBtn.setEnabled(true);
			}
		} else if (e.getSource() == stopBtn) {
			if (!isStart) {
				JOptionPane.showMessageDialog(this, "��������δ����", "����", JOptionPane.ERROR_MESSAGE);
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
			//JOptionPane.showMessageDialog(this, "�ɹ�����������ServerSocket");//��������accept�����������ǰ�棬������������Ч??setVisibleҲ���У��ź��������
			startBtn.setEnabled(false);
			maxNumTf.setEditable(false);
			portTf.setEditable(false);
			stopBtn.setEnabled(true);
			//JOptionPane.showMessageDialog(this, "�ɹ�����������ServerSocket");
			serverService();
			isStart = true;
		} catch (BindException e) {
			isStart = false;
			JOptionPane.showMessageDialog(this, "�˿ں��ѱ�ռ�ã������");
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
				out.writeUTF("���:���Ƿ�����,���ӳɹ���");
				String s;
				//int i=0;
				while (true) {
					s = in.readUTF();
					System.out.println("�յ��ͻ��˵�һ����Ϣ"+s+",end?"+"end\n".equals(s));
					contentTa.append(s);
					out.writeUTF("���Ƿ������������յ�");
					//if (s!=null) break;
					if ("end\n".equals(s)||"end".equals(s)){out.writeUTF("end");contentTa.append("�ر�");break;}
				}
				//out.writeUTF("���:���Ƿ�����,���ӳɹ���");
			} catch (IOException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		//}
	}
	
	public void serverClose() {
		//if (isStart)
		
		JOptionPane.showMessageDialog(this, "�ɹ��رշ�����ServerSocket");
		isStart = false;
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
