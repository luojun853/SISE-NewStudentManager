package com.sise.kcsj;

import java.sql.SQLException;

public class Student3 {
	DBHelper db = new DBHelper("root","200920");
    IOHelper io = new IOHelper();

    //ģ����

    public Student3(){
        try {
            db.sumSubjects();
            io.txtWriter("student2.txt",db.studentQueryBySubject(),false);
            db.dbCloser();
            System.out.println("ѧ����Ϣ����ɹ���");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
