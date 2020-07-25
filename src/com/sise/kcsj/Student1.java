package com.sise.kcsj;

import java.sql.SQLException;

public class Student1 {
	DBHelper db = new DBHelper("root","200920");
    IOHelper io = new IOHelper();

    //模块一：将第一批和第二批新生记录从文件导入数据库为了求和和按分数排序，最后将数据传到student1和English文件中

    public Student1(){
        try {
            io.txtReader("newStudent1.txt"); //读取第一批学生名单到数据库
            io.txtReader("newStudent2.txt"); //读取第二批学生名单到数据库
            db.Sum(); //将读到的所有数据进行分数求和
            io.txtWriter("student1.txt",io.str+db.studentQueryAll(),false); //按分数高到低顺序掩盖写入student文件
            io.txtWriter("English.txt",io.str+db.dbQuery("SELECT * FROM student WHERE english >100 AND sum > 300 ORDER BY english"),false); //English文件写入
            db.dbCloser();
            System.out.println("新生文件导入成功！English文件写入成功！");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
