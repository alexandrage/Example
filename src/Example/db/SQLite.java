package Example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SQLite implements Database {
	private Connection conn;
	private Statement statmt;
	private PreparedStatement preparedStatement = null;

	public SQLite(String url) {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite://" + url);
			statmt = conn.createStatement();
			statmt.execute(
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
			preparedStatement = conn.prepareStatement("SELECT * FROM users WHERE user = ?;");
			preparedStatement.setString(1, user);
			ResultSet e = preparedStatement.executeQuery();
			ArrayList<String> item = new ArrayList<String>();
			if (e.next()) {
				item.add(e.getString("user"));
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