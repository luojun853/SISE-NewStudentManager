package com.sise.kcsj;

import java.sql.SQLException;

public class Student1 {
	DBHelper db = new DBHelper("root","200920");
    IOHelper io = new IOHelper();

    //ģ��һ������һ���͵ڶ���������¼���ļ��������ݿ�Ϊ����ͺͰ���������������ݴ���student1��English�ļ���

    public Student1(){
        try {
            io.txtReader("newStudent1.txt"); //��ȡ��һ��ѧ�����������ݿ�
            io.txtReader("newStudent2.txt"); //��ȡ�ڶ���ѧ�����������ݿ�
            db.Sum(); //���������������ݽ��з������
            io.txtWriter("student1.txt",io.str+db.studentQueryAll(),false); //�������ߵ���˳���ڸ�д��student�ļ�
            io.txtWriter("English.txt",io.str+db.dbQuery("SELECT * FROM student WHERE english >100 AND sum > 300 ORDER BY english"),false); //English�ļ�д��
            db.dbCloser();
            System.out.println("�����ļ�����ɹ���English�ļ�д��ɹ���");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
