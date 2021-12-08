

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.Vector;
import java.io.*;
public class ServerDemo {
	String MSG="MSG";
	String ONL="ONL";
	String LOG="LOG";
	String REG="REG";
	String FIN="FIN";
	String PRI="PRI";//...........................
	String MYN="MYN";//.............................
	String PRC="PRC";//.............................
	String OnlineListTxt ="OnlineList.txt";
	String str2="";
	String NULL="                                                                                                                                                                         ";
	String NickName;
	String UserMailbox;
	String PassWord;
	int i;
	private BufferedReader inscanner;
	private PrintWriter out;
	private static Vector<Socket> socketOnline = new Vector<Socket>(10, 5);
	class ThreadServer extends Thread{
		Socket socket;
		ThreadServer(){};
		ThreadServer(Socket socket){
			this.socket=socket;
		}
		public void run() {			
			
			System.out.println(socket.getInetAddress()+"scuessly connect!");//输出连接的客户端的地址
			
			
			try {
				inscanner=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			while(true) {
				String indata="";
				try {
					indata=inscanner.readLine();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
					if(indata!=null) {
						String flag=indata.substring(0,3);
						indata=indata.substring(3);
						//inscanner.close();
						if(flag.equals(LOG)) {
							int i=indata.indexOf("/");
							boolean f = false;
							UserMailbox=indata.substring(0,i);
							PassWord=indata.substring(i+1);
							
							try {
							  f=isLogin(UserMailbox,PassWord);
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							PrintWriter out = null;
							try {+
								out=new PrintWriter(socket.getOutputStream());
							} catch (IOException e) {
								e.printStackTrace();
							}
							//System.out.println("13122222222");
							if(f) {
									out.println("true");
									out.flush();	
									break;
							}else {
									out.println("false");
									out.flush();	
							}
						}
						
						if(flag.equals(REG)) {
							System.out.println(indata);
							int i,j;
							i=indata.indexOf("/");
							j=indata.indexOf(" ");
							String usermailbox=indata.substring(0,i);
							String password=indata.substring(i+1,j);
							String nickname=indata.substring(j+1);
							System.out.println(usermailbox);
							System.out.println(password);
							System.out.println(nickname);
							File file=new File("User.txt");
							File file1=new File("UserNickName.txt");
							boolean IsExists=false;
							/*String data;
							BufferedReader read = null;
							try {
								read = new BufferedReader(new FileReader(file));
							} catch (FileNotFoundException e) {
								
								e.printStackTrace();
							}
							
							/*try {
								while((data=read.readLine())!=null) {
									if(data.contains(UserMailbox)) {
										IsExists=true;
										break;
									}
								}
								read.close();
							} catch (IOException e) {
								
								e.printStackTrace();
							}*/
							if(!IsExists) {
								FileWriter writer=null;
								FileWriter writer1=null;
								try {
									writer = new FileWriter(file, true);
									String content ="\n"+usermailbox+" "+password;
									writer1 = new FileWriter(file1, true);
									String content1 ="\n"+usermailbox+"/"+nickname;
									writer.write(content);
									writer1.write(content1);
									writer.close();
									writer1.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
							
						
					}
						
						
						
				    if(flag.equals(FIN)) {                      //忘记密码部分
							System.out.println(indata);
							int i,j;
							i=indata.indexOf("/");
							String usermailbox=indata.substring(0,i);
							String password=indata.substring(i+1);
							System.out.println(usermailbox);
							System.out.println(password);
							File file=new File("User.txt");
							boolean IsExists=false;
							String data;               //删去之前的密码
							String formaldata=null;
							BufferedReader read = null;
							BufferedWriter out=null;
							try {
								read = new BufferedReader(new FileReader(file));
								out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
							} catch (FileNotFoundException e) {
								
								e.printStackTrace();
							}
							try {
								while((data=read.readLine())!=null) {
									if(data.contains(usermailbox)) {
									}
									else
									{
										System.out.println(data);
										formaldata+=data+"\n";
									}
								}
								read.close();
							} catch (IOException e) {
								e.printStackTrace();
							}                
							try {
						        out.write(formaldata);
						        out.close();
						    } catch (IOException e) {
						        e.printStackTrace();
						    }                           //删除结束
							if(!IsExists) {
								FileWriter writer=null;
								try {
									writer = new FileWriter(file, true);
									String content ="\n"+usermailbox+" "+password;
									writer.write(content);
									writer.flush();
									writer.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
								
							}
							
						
					}//忘记密码结束
				    
					}
				}
							
			try {
				NickName=getNickName(UserMailbox);//已获取自己的名字
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			System.out.println(NickName);
			
			
			socketOnline.add(socket); 
			try {
				sendNickname(NickName,socket);
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}//将客户端自己的名字返回回去
			FileWriter writer;
			try {
				writer = new FileWriter(OnlineListTxt, true);
				String content =NickName+"/";
				writer.write(content);
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			try {	
			while(true) {
				//i=socketOnline.indexOf(socket);
				File file = new File(OnlineListTxt);
			    FileInputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			    
					int length;
					length = inputStream.available();
					byte bytes[] = new byte[length];
				    inputStream.read(bytes);
				    inputStream.close();
				    String str =new String(bytes);
				    //str=ONL+str;
				    if(!str2.equals(str))
				    sendAll(ONL+str);
				    str2=str;
			String indata1="";
			indata1=inscanner.readLine();
				if(indata1!=null) {                //登录之后
					String flag=indata1.substring(0,3);
					indata1=indata1.substring(3);
					//inscanner.close();
					if(flag.equals(MSG)) {
						System.out.println(NickName+indata1);
						indata1=MSG+NickName+":"+indata1;
						
							sendAll(indata1);
						
					}
					if(flag.equals(PRI))//私聊创建
					{
						String indata2=" ";//可能有问题
						String indata3=" ";
						indata2=PRI+NickName+"/"+indata1;
						indata3=PRI+indata1+"/"+NickName;
						sendAll(indata2);
						sendAll(indata3);
					}
					if(flag.equals(PRC))//私聊消息
					{
						int i,j;
						i=indata1.indexOf("/");
						j=indata1.indexOf(" ");
						String sender=indata1.substring(0,i);
						String receiver=indata1.substring(i+1,j);
						String content=indata1.substring(j+1);
						String indata2=PRC+sender+"/"+receiver+" "+NickName+":"+content;
						sendAll(indata2);
					}
				}
				//
				
				
			   
			}
			}catch(IOException e) {
				i=socketOnline.indexOf(socket);
				socketOnline.remove(i);
				String de=NickName+"/";
				try {
					BufferedReader read =new BufferedReader(new FileReader(OnlineListTxt));
					String s;
					String sb ="";
					while((s=read.readLine())!=null) {
						sb=sb+s;
					}
					System.out.println(sb);
					sb=sb.replace(de,"");
					read.close();
					System.out.println(sb);
					
					File file=new File(OnlineListTxt);
					PrintWriter out1=new PrintWriter(file);
					out1.print(NULL);
					out1.close();
					
					file=new File(OnlineListTxt);
					PrintWriter out=new PrintWriter(file);
					out.print(sb);
					out.close();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				
				}
			}
		}
			
	
	public void sendNickname(String NickName,Socket socket)throws IOException//返回用户名
	{
		PrintWriter out;
		String outdata=MYN+NickName;
		out=new PrintWriter(socket.getOutputStream());
		out.println(outdata);
		out.flush();
	}
	public void sendAll(String outdata) throws IOException{
	
		PrintWriter out;
		for(int i=0;i<socketOnline.size();i++) {
			Socket sendSocket=(Socket)socketOnline.elementAt(i);
			out=new PrintWriter(sendSocket.getOutputStream());
			out.println(outdata);
			out.flush();
		}
	}
	
	public String getNickName(String userMailbox) throws IOException {
		File file=new File("UserNickName.txt");
		String data;
		BufferedReader read =new BufferedReader(new FileReader(file));
		while((data=read.readLine())!=null) {
			if(data.contains(UserMailbox)) {
				int i=data.indexOf("/");
				//System.out.println(i);
				data=data.substring(i+1);
				read.close();
				return data;
				
			}
				}
		read.close();
		return data;
		}
	
	public boolean isLogin(String UserMailbox,String PasserWord) throws IOException {
		File file=new File("User.txt");
		String data;
		BufferedReader read =new BufferedReader(new FileReader(file));
		while((data=read.readLine())!=null) {
			if(data.contains(UserMailbox)) {
				int i=data.indexOf(" ");
				data=data.substring(i+1);
				
				if(data.equals(PasserWord)) {
					read.close();
					return true;}
			}
				
			}
		read.close();
		return false;
		}
	
	
	public static void  main(String[] args) {
		//test();
		Socket socket=null;
		try {
			ServerSocket serverSocket = new ServerSocket(6666);
			while(true) {
				socket=serverSocket.accept();
				ServerDemo a=new ServerDemo();
				Thread a1=a.new ThreadServer(socket);
				a1.start();
				
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
		} 
		finally {
			try {
				socket.close();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}

}
