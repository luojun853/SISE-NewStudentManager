package com.sise.kcsj;

import java.io.*;
import java.sql.SQLException;

public class IOHelper {
    String str = "                                              �ɼ����������             \n" + "���      ����                   ����            ����          ��ѧ          Ӣ��            �ܷ�\n";
    //student�ļ���ͷ
    String []values;
    String lineString;
    int line = 3; //�ڼ��п�ʼ���ļ�
    DBHelper db =new DBHelper("root","200920");;

    //���ļ������ݶ�ȡ��д�����ݿ⣬�����ݿⰴһ��Ҫ��д�����ļ�
    //�����ļ���Ŀ֮���ֵ���������뻹��������涨��ʮ���ո��Է����ַ������и�
    //������ģ���������ķ�������

    public void txtReader(String file){ //��ȡָ��txt���ݲ�д�뵽���ݿ���ͺͰ���������
        try {
//            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            for (int i = 1;i < line ;i++){ //������ȡ�ļ���ǰ����
                bufferedReader.readLine();
            }
            while ((lineString = bufferedReader.readLine()) != null){ //�����п�ʼ��ȡ�ļ�ѧ����Ϣ
                values = lineString.split("          "); //���ļ��еĿո�10�����ָ�ѧ������
                db.studentAdd(values[0],values[1],values[2],values[3],values[4]); //�ָ�õ�����д�����ݿ�������������
            }
            bufferedReader.close();
//            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void txtWriter(String file,String str,Boolean bl) { //ָ��д����ļ������ݣ�GIU�������DBHelper�е�add�������ѧ����ͬʱд�뵽�ļ���true��׷��  false������
        try {
            FileWriter writer = new FileWriter(file, bl);
            writer.write(str); //Ҫд�������
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

