package com.sise.kcsj;

import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		BufferedReader buff;
		try {
//			������������
			// ����ServerSocket���󣬶��崰�ںţ�
			ServerSocket ss=new ServerSocket(3355);
			// ���ͻ������ӣ�
			Socket socket=ss.accept();
			// ��װ�ͻ�����������ݣ�
			buff=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				String[] str = buff.readLine().split("\n");
				System.out.println("�ͻ��˸���ķ�����");
				for(int i=0;i<str.length;i++) {
					System.out.println(str[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}