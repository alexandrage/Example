package Example.gsoncfg;

import java.util.HashMap;
import java.util.Map;

public class Bans {
	private Map<String, Ban> ban = new HashMap<String, Ban>();

	public Ban getBan(String name) {
		return ban.get(name);
	}
}