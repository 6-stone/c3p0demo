/*
测试说明，使用数据库连接池和不使用连接池的对比
结果：使用连接池时，只在第一次初始化时，比较耗时，
完成初始化之后，使用连接池进行数据库操作明显比
不使用连接池花费的时间少，效率高。
*/


package com.zww.server;  
  
import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
  
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;  
  
  
public class ConnectionTest {  
  
    public static void main(String[] args) throws SQLException {  
        System.out.println("使用连接池................................");  
        for (int i = 0; i < 20; i++) {  
            long beginTime = System.currentTimeMillis();  
            Connection conn = ConnectionManager.getInstance().getConnection();  
            try {  
                PreparedStatement pstmt = conn.prepareStatement("select * from event");  
                ResultSet rs = pstmt.executeQuery();  
                while (rs.next()) {  
                     // do nothing...  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
  
            long endTime = System.currentTimeMillis();  
            System.out.println("第" + (i + 1) + "次执行花费时间为:" + (endTime - beginTime));  
        }  
  
        System.out.println("不使用连接池................................");  
        for (int i = 0; i < 20; i++) {  
            long beginTime = System.currentTimeMillis();  
            MysqlDataSource mds = new MysqlDataSource();  
            mds.setURL("jdbc:mysql://localhost:3306/zww");  
            mds.setUser("root");  
            mds.setPassword("123456");  
            Connection conn = mds.getConnection();  
            try {  
                PreparedStatement pstmt = conn.prepareStatement("select * from event");  
                ResultSet rs = pstmt.executeQuery();  
                while (rs.next()) {  
                                    // do nothing...  
                }  
            } catch (SQLException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    conn.close();  
                } catch (SQLException e) {  
                    e.printStackTrace();  
                }  
            }  
            long endTime = System.currentTimeMillis();  
            System.out.println("第" + (i + 1) + "次执行花费时间为:"  
                                + (endTime - beginTime));  
        }  
  
    }  

