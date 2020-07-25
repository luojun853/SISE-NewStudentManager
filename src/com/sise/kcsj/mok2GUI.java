package com.sise.kcsj;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class mok2GUI extends JFrame {
	DBHelper db = new DBHelper("root","200920");
	Client cc;
	IOHelper io = new IOHelper();
	Student1 mod1;
	Student3 mod3;
	mok4GUI mod4;
	public mok2GUI() {
		super("student.txt输入输出数据");
		JTextArea area1,area2;
		JScrollPane jsp1,jsp2;
//		创建中间容器：
		JPanel pan0 = new JPanel();
		JPanel pan1 = new JPanel(new BorderLayout());
		JPanel pan2 = new JPanel(new BorderLayout());
		JPanel pan3 = new JPanel(new GridLayout(3,1));
//		创建组件：
		JLabel lab1 = new JLabel("输入格式：考号  姓名  语文  数学  英语");
		JLabel lab2 = new JLabel("每个数据以空格隔开，每份数据以回车隔开");
		JTextField text1=new JTextField(15);
		text1.setText("127.0.0.1");
		JLabel lab3 = new JLabel("学号");
		JLabel lab4 = new JLabel("服务器");
		JTextField text2=new JTextField(15);
		area1 = new JTextArea();
		area1.setEditable(false);
		area1.setText("欢迎使用SISE高考预录数据信息管理系统。。。\n如果是第一次使用此系统建议先点击“导入新生信息”\n祝您使用愉快！！!");
		jsp1=new JScrollPane(area1);
		area2 = new JTextArea();
		jsp2=new JScrollPane(area2);
		JButton but1 = new JButton("连接");
		JButton but2 = new JButton("添加");
		JButton but3 = new JButton("查询");
		JButton but4 = new JButton("意见反馈");
		JButton but5 = new JButton("查询全部");
		JButton but6 = new JButton("导入新生信息");
		JButton but7 = new JButton("学生信息分类");
		JButton but8 = new JButton("高级查询");
		
		lab1.setFont(new Font("宋体",Font.BOLD,20));
		lab2.setFont(new Font("宋体",Font.BOLD,20));
		lab3.setFont(new Font("宋体",Font.BOLD,18));
		lab4.setFont(new Font("宋体",Font.BOLD,18));
		lab2.setForeground(Color.blue);
		text1.setFont(new Font("宋体",Font.BOLD,20));
		text2.setFont(new Font("宋体",Font.BOLD,20));
		area1.setFont(new Font("宋体",Font.BOLD,20));
		area2.setFont(new Font("宋体",Font.BOLD,20));
		but1.setFont(new Font("宋体",Font.BOLD,20));
		but2.setFont(new Font("宋体",Font.BOLD,20));
		but3.setFont(new Font("宋体",Font.BOLD,20));
		but4.setFont(new Font("宋体",Font.BOLD,20));
		but5.setFont(new Font("宋体",Font.BOLD,20));
		but6.setFont(new Font("宋体",Font.BOLD,20));
		but7.setFont(new Font("宋体",Font.BOLD,20));
		but8.setFont(new Font("宋体",Font.BOLD,20));
//		将组建添加到中间容器：
		pan0.add(lab4);
		pan0.add(text1);
		pan0.add(but1);
		pan0.add(lab3);
		pan0.add(text2);
		pan0.add(but3);
		pan0.add(but5);
		pan0.add(but4);
		pan1.add(pan0,"North");
		pan1.add(lab2);
		pan1.add(lab1,"South");
		pan1.add(but8,"East");
		pan2.add(jsp2);
		pan2.add(pan3,"East");
		pan3.add(but2);
		pan3.add(but6);
		pan3.add(but7);
//		将中间容器添加到窗口：
		this.add(pan1,"North");
		this.add(jsp1);
		this.add(pan2,"South");
//		设置窗口大小与可见：
		this.setBounds(500,300,880,550);
		this.setVisible(true);
//		添加组件监听器：
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc=new Client(text1.getText(),3355);
				area1.append("\n连接到反馈服务器成功！！！\n");
			}
		});
		
		but2.addActionListener(new ActionListener() { //模块二添加学生信息
			public void actionPerformed(ActionEvent e) {
				area1.append("\n写入的信息\n"+area2.getText()+"\n");
//				cc.sendMsg(area2.getText());
				String []s = area2.getText().split("\n");
				try {
					for(int i = 0; i <s.length ; i++) {
						String []str = s[i].split(" ");
						db.studentAdd(str[0], str[1], str[2], str[3], str[4]);
						db.Sum();
						io.txtWriter("student1.txt", db.studentQueryAll(), false);
					}
				}catch(SQLException ee) {
					ee.printStackTrace();
				}
				area2.setText("");
			}
		});
		but3.addActionListener(new ActionListener() { //模块二按学号查询
			public void actionPerformed(ActionEvent e) {
				String str = "";
				area1.append("\n查询结果：\n名次\t考号\t\t姓名\t语文\t数学\t英语\t总分\n");
				try {
					str = db.studentQueryBySno(text2.getText()).replace("          ", "\t");
				}catch(SQLException ee) {
					ee.printStackTrace();
				}
				area1.append(str+"\n");
				text2.setText("");
			}
		});
		but4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				area1.append("\n反馈给服务器端的信息：\n");
				area1.append(text2.getText()+"\n");
				cc.sendMsg(text2.getText());
				text2.setText(" ");
			}
		});
		but5.addActionListener(new ActionListener() { //查询所有
			public void actionPerformed(ActionEvent e) {
				String str = "";
				area1.append("\n查询结果：\n名次\t考号\t\t姓名\t语文\t数学\t英语\t总分\n");
				try {
					str = db.studentQueryAll().replace("          ", "\t");
				}catch(SQLException ee) {
					ee.printStackTrace();
				}
				area1.append(str+"\n");
			}
		});
		but6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Student1();
				area1.append("\n新生信息导入到student1文件和数据库成功！\n现在你可以输入相应的学号进行查询\nEnglish文件写入成功！\n");
			}
		});
		but7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Student3();
				area1.append("\n学生信息按地区和科目分类成功！请查看student2文件。\n");
				
			}
		});
		but8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new mok4GUI("模块四查询界面");
			}
		});
//		添加窗口倾听器：
		this.addWindowListener(new WindowAdapter() {
			//设置窗口关闭：
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new mok2GUI();
	}
}

