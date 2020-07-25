package com.sise.kcsj;

import java.sql.*;

public class DBHelper {
    String url;
    int change; //����ѡ���ù������executeUpdate���������޸ĵ���Ŀ��
    Connection conn;
    Statement stmt;
    PreparedStatement ps;
    ResultSet rs;

    //����dbUpdate��dbQuery����֮�⣬�����й����ݿ�Ĳ�ѯ�ķ����Դ���������
    //������ģ���������ķ�������

    public DBHelper(String user, String pwd) {   //�������ݿ���û��������루ʹ�õ���������������޷��������ݿ���Ļؾɵ�������
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

    public int dbUpdate(String sql) throws SQLException {  //���ݿ��޸�
        this.change = stmt.executeUpdate(sql);
        return change;
    }

    public String dbQuery(String sql) throws SQLException{  //���ݿ��ѯ
        this.rs = stmt.executeQuery(sql);
        String str = this.valuesToString(rs);
        return str;
    }

    public int studentAdd(String no,String name,String ch,String mt,String eng) throws SQLException {  //���ѧ����Ϣ�����ݿ⣬GUI�¼����Ե����������ʵ��ģ���
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

    public String studentQueryBySno(String no)throws SQLException{  //ͨ��ѧ�Ų�ѯѧ����Ϣ��GUI�¼����Ե����������ʵ��ģ���
        String query = "select sno,sname,chinese,math,english,sum from student where sno = '" + no + "' order by sum DESC";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public String studentQueryBySname(String name)throws SQLException{  //ͨ��������ѯѧ����Ϣ��GUI�¼����Ե����������ʵ��ģ����
        String query = "select sno,sname,chinese,math,english,sum from student where sname = '" + name + "' order by sum DESC";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public String studentQueryAll()throws SQLException{  //��ѯ����ѧ���ɼ������ص��ַ���������д��student����ģ�������ļ�����GUI��ʾ���
        String query = "select sno,sname,chinese,math,english,sum from student order by sum DESC ";
        ps = conn.prepareStatement(query);
        rs = ps.executeQuery();
        String str = this.valuesToString(rs);
        return str;
    }

    public int Sum()throws SQLException{ //�������
        String sum = "update student set student.sum = chinese + math + english";
        ps = conn.prepareStatement(sum);
        this.change = ps.executeUpdate();
        return change;
    }

    public void sumSubjects()throws SQLException{ //ģ�����������
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

    public String valuesToString(ResultSet rs)throws SQLException{ //������ѯ���Ϊ�ַ���
        String str = "";
        int i = 1; //���
        while (rs.next()){
            int sno = rs.getInt("sno");
            String sname = rs.getString("sname");
            float chinese = rs.getFloat("chinese");
            float math = rs.getFloat("math");
            float english = rs.getFloat("english");
            float sum = rs.getFloat("sum");
            str += i++ +"          "+sno+"          "+sname+"          "+chinese+"          "+math+"          "+english+"          "+sum + "\n";
            //�ո�˵����IOHelper
        }
        return str;
    }

    public String[] studentGetAddress() throws SQLException{ //ģ������ȡ������Դ�ص���
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

    public String studentQueryBySubject() throws SQLException{ //ģ�����ֿ�
        String []addresses = this.studentGetAddress();
        String str = "";
        for (int i = 0;i <addresses.length; i++){
            str += addresses[i]+"������\n";
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
            str += "�Ŀ�\n���� ���� ���� �Ա� ��ѧ���� ���� ��ѧ Ӣ�� ��ʷ ���� �ܷ�\n";
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
            str += "���\n���� ���� ���� �Ա� ��ѧ���� ���� ��ѧ Ӣ�� ���� �ܷ�\n";
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
            str += "����\n���� ���� ���� �Ա� ��ѧ���� ���� ��ѧ Ӣ�� ���� �ܷ�\n";
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
