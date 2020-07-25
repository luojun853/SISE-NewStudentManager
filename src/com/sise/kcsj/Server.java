package com.sise.kcsj;

import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		BufferedReader buff;
		try {
//			创建服务器：
			// 创建ServerSocket对象，定义窗口号：
			ServerSocket ss=new ServerSocket(3355);
			// 检测客户端连接：
			Socket socket=ss.accept();
			// 包装客户端输入的数据：
			buff=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(true) {
				String[] str = buff.readLine().split("\n");
				System.out.println("客户端给予的反馈：");
				for(int i=0;i<str.length;i++) {
					System.out.println(str[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}