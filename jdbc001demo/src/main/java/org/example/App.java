package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws Exception {
//        System.out.println( "Hello World!" );
        // todo: 1. 注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // todo: 2. 获取连接对象
//        Connection conn = DriverManager.getConnection("jdbc:mysql://openEulerDocker:3306/my01db?useSSL=false", "root", "my-root-123");
        Connection conn = DriverManager.getConnection("jdbc:mysql://openEulerDocker:3306/my01db", "root", "my-root-123");

        // todo: 3. 获取执行SQL语句的对象
        PreparedStatement pstmt = conn.prepareStatement("select * from student");

        // todo: 4. 执行SQL语句
        ResultSet rs = pstmt.executeQuery();

        // todo：5. 处理结果
        while(rs.next()){
            System.out.println(rs.getInt(1)+"\t"+rs.getString(2)+"\t"
                    +rs.getString(3)+"\t"+rs.getInt(4));
        }
        // todo: 6. 释放资源
        rs.close();
        pstmt.close();
        conn.close();
    }
}