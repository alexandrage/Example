package Example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQL {
	private Connection conn;
	private Statement statmt;
	private PreparedStatement preparedStatement = null;

	public MySQL(String url, String dbName, String user, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + url + "/" + dbName + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
					user, pass);
			statmt = conn.createStatement();
			statmt.execute(
					"CREATE TABLE IF NOT EXISTS `users` (`user` varchar(16) PRIMARY KEY,`pass` varchar(32) NOT NULL,`ip` varchar(16) NOT NULL,`time` varchar(32) NOT NULL)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(String user, String pass, String ip, Long time) {
		try {
			PreparedStatement e = conn.prepareStatement(
					"INSERT INTO users (user,pass,ip,time) VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE pass = ?, ip = ?, time = ?;");
			e.setString(1, user);
			e.setString(2, pass);
			e.setString(3, ip);
			e.setLong(4, time);
			e.setString(5, pass);
			e.setString(6, ip);
			e.setLong(7, time);
			e.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> select(String user) {
		try {
			preparedStatement = conn.prepareStatement("SELECT user,pass,ip,time FROM users WHERE user = ?;");
			preparedStatement.setString(1, user);
			ResultSet e = preparedStatement.executeQuery();
			ArrayList<String> item = new ArrayList<String>();
			if (e.next()) {
				item.add(e.getString("user"));
				item.add(e.getString("pass"));
				item.add(e.getString("ip"));
				item.add(e.getString("time"));
				e.close();
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}