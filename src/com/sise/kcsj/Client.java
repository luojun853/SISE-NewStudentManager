package com.sise.kcsj;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	PrintStream out;
	public Client(String ip,int num) {
		try {
//			创建Socket对象，连接到主机的服务器：
			Socket socket=new Socket(ip,num);
			System.out.println("已顺利联接到服务器。");
			out=new PrintStream(socket.getOutputStream());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendMsg(String str) {
		out.println(str);
	}
	public void cClose() {
		out.close();
	}
}
