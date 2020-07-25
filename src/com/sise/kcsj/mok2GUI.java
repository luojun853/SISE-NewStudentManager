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
		super("student.txt�����������");
		JTextArea area1,area2;
		JScrollPane jsp1,jsp2;
//		�����м�������
		JPanel pan0 = new JPanel();
		JPanel pan1 = new JPanel(new BorderLayout());
		JPanel pan2 = new JPanel(new BorderLayout());
		JPanel pan3 = new JPanel(new GridLayout(3,1));
//		���������
		JLabel lab1 = new JLabel("�����ʽ������  ����  ����  ��ѧ  Ӣ��");
		JLabel lab2 = new JLabel("ÿ�������Կո������ÿ�������Իس�����");
		JTextField text1=new JTextField(15);
		text1.setText("127.0.0.1");
		JLabel lab3 = new JLabel("ѧ��");
		JLabel lab4 = new JLabel("������");
		JTextField text2=new JTextField(15);
		area1 = new JTextArea();
		area1.setEditable(false);
		area1.setText("��ӭʹ��SISE�߿�Ԥ¼������Ϣ����ϵͳ������\n����ǵ�һ��ʹ�ô�ϵͳ�����ȵ��������������Ϣ��\nף��ʹ����죡��!");
		jsp1=new JScrollPane(area1);
		area2 = new JTextArea();
		jsp2=new JScrollPane(area2);
		JButton but1 = new JButton("����");
		JButton but2 = new JButton("���");
		JButton but3 = new JButton("��ѯ");
		JButton but4 = new JButton("�������");
		JButton but5 = new JButton("��ѯȫ��");
		JButton but6 = new JButton("����������Ϣ");
		JButton but7 = new JButton("ѧ����Ϣ����");
		JButton but8 = new JButton("�߼���ѯ");
		
		lab1.setFont(new Font("����",Font.BOLD,20));
		lab2.setFont(new Font("����",Font.BOLD,20));
		lab3.setFont(new Font("����",Font.BOLD,18));
		lab4.setFont(new Font("����",Font.BOLD,18));
		lab2.setForeground(Color.blue);
		text1.setFont(new Font("����",Font.BOLD,20));
		text2.setFont(new Font("����",Font.BOLD,20));
		area1.setFont(new Font("����",Font.BOLD,20));
		area2.setFont(new Font("����",Font.BOLD,20));
		but1.setFont(new Font("����",Font.BOLD,20));
		but2.setFont(new Font("����",Font.BOLD,20));
		but3.setFont(new Font("����",Font.BOLD,20));
		but4.setFont(new Font("����",Font.BOLD,20));
		but5.setFont(new Font("����",Font.BOLD,20));
		but6.setFont(new Font("����",Font.BOLD,20));
		but7.setFont(new Font("����",Font.BOLD,20));
		but8.setFont(new Font("����",Font.BOLD,20));
//		���齨��ӵ��м�������
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
//		���м�������ӵ����ڣ�
		this.add(pan1,"North");
		this.add(jsp1);
		this.add(pan2,"South");
//		���ô��ڴ�С��ɼ���
		this.setBounds(500,300,880,550);
		this.setVisible(true);
//		��������������
		but1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cc=new Client(text1.getText(),3355);
				area1.append("\n���ӵ������������ɹ�������\n");
			}
		});
		
		but2.addActionListener(new ActionListener() { //ģ������ѧ����Ϣ
			public void actionPerformed(ActionEvent e) {
				area1.append("\nд�����Ϣ\n"+area2.getText()+"\n");
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
		but3.addActionListener(new ActionListener() { //ģ�����ѧ�Ų�ѯ
			public void actionPerformed(ActionEvent e) {
				String str = "";
				area1.append("\n��ѯ�����\n����\t����\t\t����\t����\t��ѧ\tӢ��\t�ܷ�\n");
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
				area1.append("\n�������������˵���Ϣ��\n");
				area1.append(text2.getText()+"\n");
				cc.sendMsg(text2.getText());
				text2.setText(" ");
			}
		});
		but5.addActionListener(new ActionListener() { //��ѯ����
			public void actionPerformed(ActionEvent e) {
				String str = "";
				area1.append("\n��ѯ�����\n����\t����\t\t����\t����\t��ѧ\tӢ��\t�ܷ�\n");
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
				area1.append("\n������Ϣ���뵽student1�ļ������ݿ�ɹ���\n���������������Ӧ��ѧ�Ž��в�ѯ\nEnglish�ļ�д��ɹ���\n");
			}
		});
		but7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Student3();
				area1.append("\nѧ����Ϣ�������Ϳ�Ŀ����ɹ�����鿴student2�ļ���\n");
				
			}
		});
		but8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new mok4GUI("ģ���Ĳ�ѯ����");
			}
		});
//		��Ӵ�����������
		this.addWindowListener(new WindowAdapter() {
			//���ô��ڹرգ�
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new mok2GUI();
	}
}

