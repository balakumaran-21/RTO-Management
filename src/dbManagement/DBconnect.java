package dbManagement;

import java.sql.*;

public class DBconnect {
	public static Connection connectDB() throws SQLException {
		String url = "jdbc:mysql://localhost:3306/RTOManagement";
		String userName = "root";
		String password = "root";
		return DriverManager.getConnection(url,userName,password);
	}
}
