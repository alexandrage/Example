package Example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MySQL implements Database {
	private Connection conn;

	public MySQL(String url, String dbName, String user, String pass) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://" + url + "/" + dbName + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true",
					user, pass);
			conn.createStatement().execute(
					"CREATE TABLE IF NOT EXISTS `users` (`user` varchar(16) PRIMARY KEY,`time` varchar(255) NOT NULL)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(String user, Long time) {
		try {
			PreparedStatement e = conn
					.prepareStatement("INSERT INTO users (user,time) VALUES (?,?) ON DUPLICATE KEY UPDATE time = ?;");
			e.setString(1, user);
			e.setLong(2, time);
			e.setLong(3, time);
			e.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<String> select(String user) {
		try {
			PreparedStatement e = conn.prepareStatement("SELECT * FROM users WHERE user = ?;");
			e.setString(1, user);
			ResultSet r = e.executeQuery();
			ArrayList<String> item = new ArrayList<String>();
			if (r.next()) {
				item.add(r.getString("user"));
				item.add(r.getString("time"));
				e.close();
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}