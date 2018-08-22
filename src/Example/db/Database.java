package Example.db;

import java.util.ArrayList;

public interface Database {
	public void insert(String user, Long time);

	public ArrayList<String> select(String user);
}