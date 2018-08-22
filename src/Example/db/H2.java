package Example.db;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class H2 implements Database {
	private Connection conn;
	private Statement statmt;
	private PreparedStatement preparedStatement = null;

	public void loadDriver(String u) {
		try {
			u = new File(u).getParent();
			URLClassLoader loader = (URLClassLoader) getClass().getClassLoader();
			Method m = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
			m.setAccessible(true);
			m.invoke(getClass().getClassLoader(), new File(u + "/h2.jar").toURI().toURL());
			Class.forName("org.h2.Driver", true, loader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public H2(String url) {
		loadDriver(url);
		try {
			Class.forName("org.h2.Driver");
			conn = DriverManager.getConnection("jdbc:h2://" + url + ";mode=MySQL", "sa", "");
			statmt = conn.createStatement();
			statmt.execute(
					"CREATE TABLE IF NOT EXISTS `users` (`user` varchar(255) PRIMARY KEY,`time` varchar(255) NOT NULL)");
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