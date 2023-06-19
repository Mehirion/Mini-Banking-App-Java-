package banking;

import java.sql.Connection;
import java.sql.DriverManager;
public class connection {
    static Connection con;
    public static Connection getConnection() throws RuntimeException {
        try {
            String mysqlJDBCDriver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/bank";
            String user = "root";
            String pass = "admin";
            Class.forName(mysqlJDBCDriver);
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("DB connected!");
        }
        catch (Exception e) {
            throw new RuntimeException("Connection failed", e);
        }
        return con;
    }

}
