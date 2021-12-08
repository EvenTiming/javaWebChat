2021/9/14

上学期学习了java，期末时做了一个多人在线聊天室。抽时间来总结一下开发学习历程。类似的文章在csdn上已经有了很多，自己最终做出的作品也不是很出色，刚好利用这个机会改进一下。在总结时我会把每个问题参考的博客链接放在下面。

暑假的时间由于项目需求，接触了点区块链和IPFS里面的知识，尝试下能不能将两个结合起来，做一个区块链多人在线聊天室。

今天算是开了一个坑，希望能填满。

### 1.测试链接

​	既然要做一个网络多人的聊天室，那就不能仅限于在本地运行程序。聊天室应该是基于C/S模式的应用程序，这里我们使用腾讯云提供的学生机，1核2G的配置足够我们做这个项目。

[腾讯云学生机网址]: https://cloud.baidu.com/campaign/campus-2018/index.html?track=cp:nsem|pf:pc|pp:nsem-huodong-qihangxiaoyuan|pu:qihangxiaoyuan-tongyongci|ci:qhxuesheng|kw:2112014&amp;renqun_youhua=2850304

​	当然租用完学生机器之后还需要配置java环境，安装数据库等操作，这里就不再详述。有时间补充一下或者放几个参考链接。

​	像我们平时使用的qq微信等即时通信工具，我们发送的消息都是需要将消息发送的到服务器上，再由服务器进行转发。我们做的基本的聊天室也是一样，消息的发送需要服务器来进行中转。

​	与服务器进行信息交互的第一步便是与服务器连接，也就使用到了socket（套接字），socket是什么工作原理这些并不需要我们了解太多，只需要知道这是本地与服务器沟通的桥梁就行了。而java已经将sokcet相关的方法封装了起来，项目使用到的有java.net.Socket/，我们学会调用即可。

​	首先是本地客户端的测试链接程序

```java
package client;
import java.io.IOException;
import java.net.Socket;
public class ServerConnect {
    public static void main(String[] args) {
        Socket socket=null;
        try {
         // 实例化socket对象，这里使用ip地址和端口号进行连接，java.net.Socket还有其他方法
            socket = new Socket("hostname",9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
         System.out.println(socket);
        //连接成功输出true
        System.out.println(socket.isConnected());
    }
}
```

java.net.Socket里封装了很多方法。代码里使用ip地址和端口号进行连接。要确保服务器的端口号是打开的。

服务器端测试连接程序

```java
package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static void main(String[] args) {
        Socket socekt=null;
        try {
            //实例化serverSocekt对象，确保这里的端口数和客户端的端口相同
            ServerSocket serverSocket=new ServerSocket(9999);
            //监听
            while(true){
              //如果连接成功，跳出循环
              socekt =serverSocket.accept();
              if(socekt.isConnected()){
                  break;
              }
            }
            System.out.println(socket);
            //输出已连接的客户端的ip地址
            System.out.println(socekt.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
```

serverSocekt在连接后会返回一个socket，客户端和本地都是通过socket与彼此进行交互，两个socket是配对的。对客户端或者服务器来说，两者的交互都是对socekt操作。

连接成功客户端会输出 socket的值和true，服务器端会输出socket和连接的客户端的ip地址。

到这里说明连接成功。

### 2.发送信息

接下来我们尝试在本地给服务器端发送给一条信息，java.net.Socekt里已经已经封装了getIntputStream和getOutputStream两个方法，可以使用字节流进行交互。

客户端代码：

```java
package client;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerConnect {
    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("1.15.226.19",9999);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(socket);
        System.out.println(socket.isConnected());
        try {
            //这是发送的第一个信息
            OutputStream out= socket.getOutputStream();
            String str="hello this is a message";
            //将字符串转化为字节流
            byte[] mes=str.getBytes(StandardCharsets.UTF_8);
            //发送消息
            out.write(mes);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

服务器端代码：

```java
//package Server;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static void main(String[] args) {
        Socket socekt = null;
        try {
            ServerSocket serverSocket=new ServerSocket(9999);
            while(true){
              socekt =serverSocket.accept();
              if(socekt.isConnected()){
                  break;
              }
            }
            System.out.println(socekt);
            System.out.println(socekt.getInetAddress());
            //定义输入流
            InputStream in=socekt.getInputStream();
            //接收消息
            byte[] mes=new byte[1024];
            int len=in.read(mes);
            //输出消息
            System.out.println(new String(mes,0,len));
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

在这里主要采用了最原始的InputStream和OutPutStream来进行数据交互，后面开发时，我们会使用其他的对文件进行操作的对象。

实验结果：![image-20210915162830367](C:\Users\CIAge\AppData\Roaming\Typora\typora-user-images\image-20210915162830367.png)

值得注意的是，我们现在是直接将发送的消息打印在服务器上，实际应用时我们应该将他转发给其他用户，并加入些多线程的特性 ...

到这里仅仅实现了服务器与本地之间的信息交互，虽然功能基础简单，但是这是后面更复杂功能实现的基础。

3.聊天界面的简单实现

用idea自带的GUI form生成代码有问题，还有个需要付费的插件。。迫于无奈用回windowsbuilder。



