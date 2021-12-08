package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.TextArea;
import java.awt.TextField;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientGUI {
	Socket socket;
	private JFrame frame;
	Scanner keyboradscanner =null;
	PrintWriter pastoserver=null;
	static TextField textField ;
	static TextField textField_1 ;
	static TextArea textArea;
	static TextArea textArea2;
	Button button;
	Scanner inscanner=null;
	String MSG="MSG";
	String ONL="ONL";
	String PRI="PRI";//...........................................................
	String MYN="MYN";//...........................................................
	private JButton btnNewButton;
	public static String Myname=" "; //客户端需要知道自己是谁
	PrivateChatGUI[] pri=new PrivateChatGUI[5];//创建私聊窗口数组
	private int count=0;//...........................................................
	public boolean PrivateChatGUIflag=false;//...........................................................
	public void Starter() throws IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI(socket);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		Scanner inscanner=new Scanner(socket.getInputStream());
		while(true) {
			String indata=inscanner.nextLine();
			if(indata!=null) {
				String flag=indata.substring(0,3);
				indata=indata.substring(3);
				if(flag.equals(MSG)) {
						
					showMsg(indata);
				
				}
				if(flag.equals(ONL)) {
					
					updateOnlineList(indata);
				}
				if(flag.equals(MYN))//获取自己的名字
				{
					Myname=indata;
				}
				if(flag.equals(PRI))//创建私聊窗口
				{
					int i;
					i=indata.indexOf("/");
					String firstname=indata.substring(0,i);
					String secondname=indata.substring(i+1);
					if(Myname.equals(firstname))
					{
						PrivateChatGUI pri=new PrivateChatGUI(socket,firstname,secondname);//创建私聊窗口
						pri.Starter();
						System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaa");//检查错误
					}
				}
					
			}
		}
	}

	
	public ClientGUI(Socket socket) throws IOException {
		this.socket=socket;
		initialize();
		}

	

	public void initialize() {
		frame = new JFrame();
		frame.getContentPane().setEnabled(false);
		frame.setBounds(100, 100, 634, 449);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		textArea = new TextArea();
		textArea.setSelectionEnd(1);
		textArea.setBackground(Color.WHITE);
		textArea.setEditable(false);
		textArea.setBounds(155, 29, 425, 257);
		frame.getContentPane().add(textArea);
		
		textField = new TextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER&& (e.isControlDown()))
				{
					try {
						pastoserver=new PrintWriter(socket.getOutputStream());
						String text=textField.getText();
						text=MSG+text;
						pastoserver.println(text);
						pastoserver.flush();
						//textArea.append(text+"\n");
						textField.setText("");
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		textField.setBounds(166, 322, 302, 21);
		frame.getContentPane().add(textField);
		
		JLabel lblNewLabel = new JLabel("\u5728\u7EBF\u6210\u5458");
		lblNewLabel.setBounds(35, 10, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u4E3B\u804A\u5929\u7A97\u53E3");
		lblNewLabel_1.setBounds(323, 10, 92, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		textArea2 = new TextArea();
		textArea2.setEditable(false);
		textArea2.setBounds(10, 29, 123, 257);
		frame.getContentPane().add(textArea2);
		
		btnNewButton = new JButton("发送");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pastoserver=new PrintWriter(socket.getOutputStream());
					String text=textField.getText();
					text=MSG+text;
					pastoserver.println(text);
					pastoserver.flush();
					//textArea.append(text+"\n");
					textField.setText("");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(483, 320, 97, 23);
		frame.getContentPane().add(btnNewButton);
		
		TextField textField_1 = new TextField();//私聊对象的文本框
		textField_1.setBounds(23, 322, 111, 21);
		frame.getContentPane().add(textField_1);
		JButton btnNewButton_1 = new JButton("私聊");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) //点击私聊按钮，向服务端发送特定消息
			{
				try {
					pastoserver=new PrintWriter(socket.getOutputStream());
					String text=textField_1.getText();
					text=PRI+text;
					pastoserver.println(text);
					pastoserver.flush();
					//textArea.append(text+"\n");
					textField.setText("");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(49, 347, 65, 21);
		frame.getContentPane().add(btnNewButton_1);
	}
	

	void showMsg(String msg) {
		textArea.append(msg+"\n");
	}
	void updateOnlineList(String indata) {
		textArea2.setText("");
		int i=0;
		String s="";
		while(i<indata.length()) {
			if(indata.charAt(i)=='/') {
				textArea2.append(s);
				textArea2.append("\n");
				s="";
			}
			else {
				s=s+indata.charAt(i);
				
			}
			i++;}
	textArea2.append(s);
	textArea2.append("\n");
		}
}