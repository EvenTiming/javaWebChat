package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.awt.event.ActionEvent;

public class LoginGUI {
	Socket socket;
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	String UserMailbox;
	String PassWord;
	String LOG="LOG";
	protected PrintWriter pastoserver;
	/**
	 * Launch the application.
	 */
	public  void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI window = new LoginGUI(socket);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LoginGUI(Socket socket) {
		this.socket=socket;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 161);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(61, 26, 317, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u8D26\u53F7");
		lblNewLabel.setBounds(22, 29, 29, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(61, 58, 317, 21);
		frame.getContentPane().add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("\u5BC6\u7801");
		lblNewLabel_1.setBounds(22, 61, 29, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u6B22\u8FCE");
		lblNewLabel_2.setBounds(181, 0, 54, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserMailbox=textField.getText();
				PassWord=textField_1.getText();
				
				try {
					pastoserver=new PrintWriter(socket.getOutputStream());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String text=UserMailbox+"/"+PassWord;
				text=LOG+text;
				pastoserver.println(text);//发送text的内容给服务器
				pastoserver.flush();
				
				Scanner inscanner = null;
				try {
					inscanner = new Scanner(socket.getInputStream());//接收服务器返回的消息
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				String indata;
				while(true) {
					indata=inscanner.nextLine();
					if(indata!=null) {
							break;
					}
				}
				if(indata.equals("true")) {
					frame.dispose();
					ChatFrame.LoginFlag=true;
				}
				else {
					textField_1.setText("");
				}
			}
		});
		btnNewButton.setBounds(61, 89, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI a=new RegisterGUI(socket);
				a.frame.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(164, 89, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("\u5FD8\u8BB0\u5BC6\u7801");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				FindPassword a=new FindPassword(socket);
				a.frame.setVisible(true);
			}
		});
		btnNewButton_2.setBounds(267, 89, 93, 23);
		frame.getContentPane().add(btnNewButton_2);
	}
}
