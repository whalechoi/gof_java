import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
	private static String url = "jdbc:mysql://localhost:3306/keshe?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf8";

	public Connection getConnection() throws SQLException, Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(url, "whale", "123456");
		return conn;
	}
}
