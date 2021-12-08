package test;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class FindPassword {

	JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	String VerificationCode;
	Socket socket=null;
	/**
	 * Launch the application.
	 */
	public void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI window = new RegisterGUI(socket);
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
	public FindPassword(Socket socket) {
		initialize();
		this.socket=socket;
		Code b=new Code();
		VerificationCode=b.getRV();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 384, 244);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u6CE8\u518C");
		lblNewLabel.setBounds(176, 10, 54, 15);
		frame.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(98, 45, 197, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(98, 90, 197, 21);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("您绑定的邮箱");
		lblNewLabel_1.setBounds(10, 48, 78, 15);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("新密码");
		lblNewLabel_2.setBounds(34, 93, 54, 15);
		frame.getContentPane().add(lblNewLabel_2);
		
		JButton btnNewButton = new JButton("\u9A8C\u8BC1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String UserMailbox=textField.getText();
				String PassWord=textField_1.getText();
				
				Register_3GUI a=new Register_3GUI(VerificationCode,UserMailbox,PassWord,socket);
				a.frame.setVisible(true);
				//��֤�뷢��
				sendMail a1=new sendMail(UserMailbox,VerificationCode);
				try {
					a1.send();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		});
		btnNewButton.setBounds(137, 135, 93, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u6211\u5DF2\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_1.setBounds(137, 168, 93, 23);
		frame.getContentPane().add(btnNewButton_1);
	}
}
