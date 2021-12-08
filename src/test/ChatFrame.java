package test;

import java.io.IOException;
import java.net.Socket;
 
public class ChatFrame {
	
	protected static boolean LoginFlag=false;

	public static void main(String[] args) {
		ClientGUI c = null;
		LoginGUI a=null;
		String VerificationCode;
		Socket socket=null;
		
		try {
			socket=new Socket("1.15.226.19",6666);
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	
		a=new LoginGUI(socket);
		a.start();
		
		while(!LoginFlag){
			System.out.print("");
		}
	
		try {
			c=new ClientGUI(socket);
			c.Starter();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		try {
			c.socket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

}
