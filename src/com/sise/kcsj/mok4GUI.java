package com.sise.kcsj;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;

public class mok4GUI extends JFrame {
	DBHelper db = new DBHelper("root","200920");
	public mok4GUI(String title) {
		super(title);
//		中间容器的构建：
		JPanel panel0 = new JPanel(new BorderLayout());
		JPanel panel2 = new JPanel();
//		组件的构建：
		JLabel lable = new JLabel("请输入考生考号或者姓名进行查询：");
		JTextField text2 = new JTextField(15);
		JButton button2 = new JButton("查询");
		JTextArea area = new JTextArea();
		area.setText("如果是第一次使用此系统建议先点击“导入新生信息”\n祝您使用愉快！！!");
		JScrollPane jsp = new JScrollPane(area);// 给多行文本域添加滚动条
		
		lable.setFont(new Font("宋体",Font.BOLD,20));
		text2.setFont(new Font("宋体",Font.BOLD,20));
		button2.setFont(new Font("宋体",Font.BOLD,20));
		area.setFont(new Font("宋体",Font.BOLD,20));
		area.setEditable(false);
//		组件添加：
		panel0.add(panel2,"South");
		panel2.add(lable);
		panel2.add(text2);
		panel2.add(button2);
//		窗口布局：
		this.add(panel0,"North");
		this.add(jsp);
//		窗口位置大小与可见：
		this.setSize(800,500);
		this.setLocation(500,300);
		this.setVisible(true);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//		组件事件处理：
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = "";
				area.append("\n查询结果：\n名次\t考号\t\t姓名\t语文\t数学\t英语\t总分\n");
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
