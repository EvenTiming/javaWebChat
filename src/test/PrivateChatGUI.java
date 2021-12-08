package test;

import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.TextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class PrivateChatGUI{

	private JFrame frame;
	public Socket socket;
	public String myname;
	public String hisname;
	static TextArea textArea;
	static TextArea textArea_1;
	PrintWriter pastoserver=null;
	String PRC="PRC";
	/**
	 * Launch the application.
	 */
	public void Starter() throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrivateChatGUI window = new PrivateChatGUI(socket,myname,hisname);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Scanner inscanner=new Scanner(socket.getInputStream());//接收信息
		while(true) {
			String indata=inscanner.nextLine();
			if(indata!=null) {
				String flag=indata.substring(0,3);
				indata=indata.substring(3);
				if(flag.equals(PRC))//接收到私聊消息
				{
					int i,j;
					i=indata.indexOf("/");
					j=indata.indexOf(" ");
					String sender=indata.substring(0,i);
					String receiver=indata.substring(i+1,j);
					String content=indata.substring(j+1);
					if(sender.equals(myname)&&receiver.equals(hisname))
					{
						showPrc(content);
					}
					if(sender.equals(hisname)&&receiver.equals(myname))
					{
						showPrc(content);
					}
				}
			}
		}
	}

	/**
	 * Create the application.
	 */
	public PrivateChatGUI(Socket socket,String myname,String hisname) throws IOException//构造函数
	{
		this.socket=socket;
		this.myname=myname;
		this.hisname=hisname;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 601, 407);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textArea = new TextArea();
		textArea.setBounds(143, 33, 434, 267);
		frame.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("私聊窗口");
		lblNewLabel.setBounds(330, 10, 58, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textArea_1 = new TextArea();
		textArea_1.setBackground(new Color(245, 245, 220));
		textArea_1.setBounds(10, 33, 104, 270);
		frame.getContentPane().add(textArea_1);
		textArea_1.setText(hisname);//私聊对象说明
		
		TextField textField = new TextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER&& (e.isControlDown()))//快捷键
				{
					try {
						pastoserver=new PrintWriter(socket.getOutputStream());
						String text=textField.getText();
						text=PRC+myname+"/"+hisname+" "+text;
						pastoserver.println(text);
						pastoserver.flush();
						textField.setText("");
					} catch (IOException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		textField.setBounds(143, 318, 302, 21);
		frame.getContentPane().add(textField);
		
		JButton btnNewButton = new JButton("发送");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) //发送私聊信息给服务端
			{
				try {
					pastoserver=new PrintWriter(socket.getOutputStream());
					String text=textField.getText();
					text=PRC+myname+"/"+hisname+" "+text;
					pastoserver.println(text);
					pastoserver.flush();
					textField.setText("");
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(469, 316, 97, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("私聊对象");
		lblNewLabel_1.setBounds(38, 10, 58, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton_1 = new JButton("返回主聊天室");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(10, 316, 107, 23);
		frame.getContentPane().add(btnNewButton_1);
		
	}
	void showPrc(String Prc) //将消息发到文字框内
	{
		textArea.append(Prc+"\n");
		
	}
}
