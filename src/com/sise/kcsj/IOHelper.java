package com.sise.kcsj;

import java.io.*;
import java.sql.SQLException;

public class IOHelper {
    String str = "                                              成绩汇总排序表             \n" + "序号      考号                   姓名            语文          数学          英语            总分\n";
    //student文件表头
    String []values;
    String lineString;
    int line = 3; //第几行开始读文件
    DBHelper db =new DBHelper("root","200920");;

    //将文件的内容读取并写入数据库，从数据库按一定要求写出到文件
    //所有文件项目之间的值不管是输入还是输出都规定空十个空格以方便字符串的切割
    //在其他模块调用这里的方法即可

    public void txtReader(String file){ //读取指定txt内容并写入到数据库求和和按分数排序
        try {
//            FileReader reader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            for (int i = 1;i < line ;i++){ //跳过读取文件的前两行
                bufferedReader.readLine();
            }
            while ((lineString = bufferedReader.readLine()) != null){ //第三行开始读取文件学生信息
                values = lineString.split("          "); //以文件中的空格（10个）分割学生数据
                db.studentAdd(values[0],values[1],values[2],values[3],values[4]); //分割好的数据写入数据库进行求和与排序
            }
            bufferedReader.close();
//            reader.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void txtWriter(String file,String str,Boolean bl) { //指定写入的文件与内容，GIU可以配合DBHelper中的add方法添加学生的同时写入到文件，true：追加  false：覆盖
        try {
            FileWriter writer = new FileWriter(file, bl);
            writer.write(str); //要写入的内容
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

