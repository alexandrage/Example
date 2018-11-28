package Example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class SQLite implements Database {
	private Connection conn;

	public SQLite(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite://" + url);
			conn.createStatement().execute(
					"CREATE TABLE IF NOT EXISTS `users` (`user` varchar(16) PRIMARY KEY,`time` varchar(255) NOT NULL)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(String user, Long time) {
		try {
			PreparedStatement e = conn.prepareStatement("INSERT OR REPLACE INTO users (user,time) VALUES (?,?);");
			e.setString(1, user);
			e.setLong(2, time);
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