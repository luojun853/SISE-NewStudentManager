package com.sise.kcsj;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class mok4GUI extends JFrame {
	DBHelper db = new DBHelper("root","200920");
	public mok4GUI(String title) {
		super(title);
//		�м������Ĺ�����
		JPanel panel0 = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel();
//		����Ĺ�����
		JLabel lable = new JLabel("�����뿼�����Ż����������в�ѯ��");
		JTextField text2 = new JTextField(15);
		JButton button2 = new JButton("��ѯ");
		JTextArea area = new JTextArea();
		area.setText("����ǵ�һ��ʹ�ô�ϵͳ�����ȵ��������������Ϣ��\nף��ʹ����죡��!");
		JScrollPane jsp = new JScrollPane(area);// �������ı�����ӹ�����
		
		lable.setFont(new Font("����",Font.BOLD,20));
		text2.setFont(new Font("����",Font.BOLD,20));
		button2.setFont(new Font("����",Font.BOLD,20));
		area.setFont(new Font("����",Font.BOLD,20));
		area.setEditable(false);
//		�����ӣ�
		panel0.add(panel2,"South");
		panel2.add(lable);
		panel2.add(text2);
		panel2.add(button2);
//		���ڲ��֣�
		this.add(panel0,"North");
		this.add(jsp);
//		����λ�ô�С��ɼ���
		this.setSize(800,500);
		this.setLocation(500,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		����¼�����
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = "";
				area.append("\n��ѯ�����\n����\t����\t\t����\t����\t��ѧ\tӢ��\t�ܷ�\n");
				try {
					String s1 = db.studentQueryBySno(text2.getText());
					String s2 = db.studentQueryBySname(text2.getText());
					s = (s1 + s2).replace("          ", "\t");
				}catch(SQLException ee) {
					ee.printStackTrace();
				}
				area.append(s+"\n");
				text2.setText("");
			}
		});

	}
	
}
