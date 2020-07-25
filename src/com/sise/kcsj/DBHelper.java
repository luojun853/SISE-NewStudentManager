package com.sise.kcsj;

import java.sql.*;

public class DBHelper {
    String url;
    int change; //（可选不用管这个）executeUpdate方法返回修改的条目数
    Connection conn;
    Statement stmt;
    PreparedStatement ps;
    ResultSet rs;

    //除了dbUpdate和dbQuery方法之外，其余有关数据库的查询的方法自带分数排序
    //在其他模块调用这里的方法即可

    public DBHelper(String user, String pwd) {   //连接数据库的用户名，密码（使用的是新驱动，如果无法连接数据库请改回旧的驱动）
        try {
            url = "jdbc:mysql://localhost:3306/kcsj?user="+user+"&password="+pwd+"&serverTimezone=Asia/Shanghai";
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection(url);
            this.stmt = conn.createStatement();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int dbUpdate(String sql) throws SQLException {  //数据库修改
        this.change = stmt.executeUpdate(sql);
        return change;
    }

    public String dbQuery(String sql) throws SQLException{  //数据库查询
        this.rs = stmt.executeQuery(sql);
        String str = this.valuesToString(rs);
        return str;
    }

    public int studentAdd(String no,String name,String ch,String mt,String eng) throws SQLException {  //添加学生信息到数据库，GUI事件可以调用这个方法实现模块二
        String add = "replace into student (sno,sname,chinese,math,english) values (?,?,?,?,?)";
        ps = conn.prepareStatement(add);
        ps.setString(1,no);
        ps.setString(2,name);
        ps.setString(3,ch);
        ps.setString(4,mt);
        ps.setString(5,eng);
        this.change = ps.executeUpdate();
        return change;
    }

    public String studentQueryBySno(String no)throws SQLException{  //通过学号查询学生信息，GUI事件可以调用这个方法实现模块二
        String query = "select sno,sname,chinese,math,english,sum from student where sno = '" + no + "' order by sum DESC";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public String studentQueryBySname(String name)throws SQLException{  //通过姓名查询学生信息，GUI事件可以调用这个方法实现模块四
        String query = "select sno,sname,chinese,math,english,sum from student where sname = '" + name + "' order by sum DESC";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public String studentQueryAll()throws SQLException{  //查询所有学生成绩，返回的字符串可用于写入student（除模块三）文件和在GUI显示结果
        String query = "select sno,sname,chinese,math,english,sum from student order by sum DESC ";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public int Sum()throws SQLException{ //分数求和
        String sum = "update student set student.sum = chinese + math + english";
        ps = conn.prepareStatement(sum);
        this.change = ps.executeUpdate();
        return change;
    }

    public void sumSubjects()throws SQLException{ //模块三分数求和
        String s1 = "update student1 set sum = chinese + math + english + history + geogra WHERE history is not null and geogra is not null";
        String s2 = "update student1 set sum = chinese + math + english +physics WHERE physics is not null";
        String s3 = "update student1 set sum = chinese + math + english +art WHERE art is not NULL";
        PreparedStatement ps1 = conn.prepareStatement(s1);
        PreparedStatement ps2 = conn.prepareStatement(s2);
        PreparedStatement ps3 = conn.prepareStatement(s3);
        ps1.executeUpdate() ;
        ps2.executeUpdate() ;
        ps3.executeUpdate();
    }

    public String valuesToString(ResultSet rs)throws SQLException{ //遍历查询结果为字符串
        String str = "";
        int i = 1; //序号
        while (rs.next()){
            int sno = rs.getInt("sno");
            String sname = rs.getString("sname");
            float chinese = rs.getFloat("chinese");
            float math = rs.getFloat("math");
            float english = rs.getFloat("english");
            float sum = rs.getFloat("sum");
            str += i++ +"          "+sno+"          "+sname+"          "+chinese+"          "+math+"          "+english+"          "+sum + "\n";
            //空格说明见IOHelper
        }
        return str;
    }

    public String[] studentGetAddress() throws SQLException{ //模块三获取新生生源地地区
        String str = "";
        String address;
        String []addresses = new String[0];
        String sql = "SELECT address FROM student1 GROUP BY address";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();
        while (rs.next()){
            address = rs.getString("address");
            str += address+"\n";
            addresses = str.split("\n");
        }
        return addresses;
    }

    public String studentQueryBySubject() throws SQLException{ //模块三分科
        String []addresses = this.studentGetAddress();
        String str = "";
        for (int i = 0;i <addresses.length; i++){
            str += addresses[i]+"地区：\n";
            String sql1 = "select * from (select * from student1 where address = ?) as tmp where history is not null and geogra is not null order by sum DESC";
            String sql2 = "select * from (select * from student1 where address = ?) as tmp where physics is not null order by sum DESC";
            String sql3 = "select * from (select * from student1 where address = ?) as tmp where art is not null order by sum DESC";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps1.setString(1,addresses[i]);
            ps2.setString(1,addresses[i]);
            ps3.setString(1,addresses[i]);
            ResultSet rs1 = ps1.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            ResultSet rs3 = ps3.executeQuery();
            str += "文科\n考号 姓名 年龄 性别 中学名称 语文 数学 英语 历史 地理 总分\n";
            while (rs1.next()){
                int sno = rs1.getInt("sno");
                String sname = rs1.getString("sname");
                String sex = rs1.getString("sex");
                int age = rs1.getInt("age");
                String school = rs1.getString("school");
                float chinese = rs1.getFloat("chinese");
                float math = rs1.getFloat("math");
                float english = rs1.getFloat("english");
                float history = rs1.getFloat("history");
                float geogra = rs1.getFloat("geogra");
                float sum = rs1.getFloat("sum");
                str += sno+"          "+sname+"          "+age+"          "+sex+"          "+school+"          "+chinese+"          "+math+"          "+english+"          "+history+"          "+geogra+"          "+sum + "\n";
            }
            str += "理科\n考号 姓名 年龄 性别 中学名称 语文 数学 英语 物理 总分\n";
            while (rs2.next()){
                int sno = rs2.getInt("sno");
                String sname = rs2.getString("sname");
                String sex = rs2.getString("sex");
                int age = rs2.getInt("age");
                String school = rs2.getString("school");
                float chinese = rs2.getFloat("chinese");
                float math = rs2.getFloat("math");
                float english = rs2.getFloat("english");
                float physics = rs2.getFloat("physics");
                float sum = rs2.getFloat("sum");
                str += sno+"          "+sname+"          "+age+"          "+sex+"          "+school+"          "+chinese+"          "+math+"          "+english+"          "+physics+"          "+sum + "\n";
            }
            str += "艺术\n考号 姓名 年龄 性别 中学名称 语文 数学 英语 美术 总分\n";
            while (rs3.next()){
                int sno = rs3.getInt("sno");
                String sname = rs3.getString("sname");
                String sex = rs3.getString("sex");
                int age = rs3.getInt("age");
                String school = rs3.getString("school");
                float chinese = rs3.getFloat("chinese");
                float math = rs3.getFloat("math");
                float english = rs3.getFloat("english");
                float sum = rs3.getFloat("sum");
                float art = rs3.getFloat("art");
                str += sno+"          "+sname+"          "+age+"          "+sex+"          "+school+"          "+chinese+"          "+math+"          "+english+"          "+art+"          "+sum + "\n";
            }
        }
        return str;
    }

    public void dbCloser()throws SQLException{
        rs.close();
        ps.close();
        stmt.close();
        conn.close();
    }

}
