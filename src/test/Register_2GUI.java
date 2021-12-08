package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class Register_2GUI {
	protected static final String MSG = null;
	Socket socket;
	JFrame frame;
	String getV;//�û��������֤��
	String VerificationCode;//ϵͳ���ɵ���֤�룬ͨ���ϲ�������ɣ�
	String UserMailbox;
	String PassWord;
	String NickName;
	private JTextField textField;
	protected PrintWriter pastoserver;
	String REG="REG";
	/**
	 * Launch the application.
	 */
	public void starter() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register_2GUI window = new Register_2GUI(VerificationCode,UserMailbox,PassWord,NickName,socket);
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
	public Register_2GUI(String VerificationCode,String UserMailbox,String PassWord,String NickName,Socket socket) {
		this.VerificationCode=VerificationCode;
		this.UserMailbox=UserMailbox;
		this.PassWord=PassWord;
		this.NickName=NickName;
		this.socket=socket;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 312, 160);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(100, 37, 107, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getV=textField.getText();
				if(getV.equals(VerificationCode)) {
					try {
						pastoserver=new PrintWriter(socket.getOutputStream());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					String text=UserMailbox+"/"+PassWord+" "+NickName;
					text=REG+text;
					pastoserver.println(text);
					System.out.println(text);
					pastoserver.flush();
					frame.dispose();
					}
				else {
					textField.setText("");
				}
			}
		});
		btnNewButton.setBounds(100, 68, 107, 23);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("\u9A8C\u8BC1\u7801");
		lblNewLabel.setBounds(54, 40, 36, 15);
		frame.getContentPane().add(lblNewLabel);
	}
}
