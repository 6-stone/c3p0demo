//ʹ��ǰȥ����������Ӧ��c3p0��jar��

package com.zww.server;  
  
import java.beans.PropertyVetoException;  
import java.sql.Connection;  
import java.sql.SQLException;  
import com.mchange.v2.c3p0.ComboPooledDataSource;  
  
public final class ConnectionManager {  
    //ʹ�õ���ģʽ�������ݿ����ӳ�  
    private static ConnectionManager instance;  
    private static ComboPooledDataSource dataSource;  
  
    private ConnectionManager() throws SQLException, PropertyVetoException {  
        dataSource = new ComboPooledDataSource();  
  
        dataSource.setUser("root");     //�û���  
        dataSource.setPassword("123"); //����  
        dataSource.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/zww");//���ݿ��ַ  
        dataSource.setDriverClass("com.mysql.jdbc.Driver");  
        dataSource.setInitialPoolSize(5); //��ʼ��������  
        dataSource.setMinPoolSize(1);//��С������  
        dataSource.setMaxPoolSize(10);//���������  
        dataSource.setMaxStatements(50);//��ȴ�ʱ�䣬��λ����  
        dataSource.setMaxIdleTime(60);//������ʱ��  
    }  
  
    public static final ConnectionManager getInstance() {  
        if (instance == null) {  
            try {  
                instance = new ConnectionManager();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return instance;  
    }  
  
    public synchronized final Connection getConnection() {  
        Connection conn = null;  
        try {  
            conn = dataSource.getConnection();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
}  