package com.sise.kcsj;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends Thread{
	PrintStream out;
	public Client(String ip,int num) {
		try {
//			����Socket�������ӵ������ķ�������
			Socket socket=new Socket(ip,num);
			System.out.println("��˳�����ӵ���������");
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
