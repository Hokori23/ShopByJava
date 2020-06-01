package DBConnection;




import java.sql.Connection;
import java.sql.DriverManager;


public class
DB {
    //MySQL 版本 5.1
    private static final String Driver = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://101.201.239.229:3306/java?useSSL=false";
    private static final String USER = "java";
    private static final String PASSWORD = "jz6MHi3J6rM26X7M";
    private Connection conn = null;

    public DB() throws Exception {
        try {
            // 注册 JDBC 驱动
            Class.forName(Driver);

            // 打开链接
            this.conn = (Connection) DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            // 处理 Class.forName 错误
            throw e;
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void close() throws Exception {
                if (this.conn != null) {
                    try {
                        this.conn.close();
                    } catch (Exception e) {
                        throw e;
                    }
        }
    }
}
