package Example.board;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Strings;

public class ScoreboardRun extends BukkitRunnable {
	private Board board;
	private List<String> list = new ArrayList<String>();
	private int index;

	public ScoreboardRun(Board board) {
		this.board = board;
		setup("localhost      ");
	}

	@Override
	public void run() {
		if (index == list.size()) {
			index = 0;
		}
		this.board.insertScore(list.get(index), 0);
		this.board.setDisplayName(list.get(index));
		index++;
	}
	
	private void setup(String string) {
		String string2 =  Strings.repeat(" ", string.length());
		char[] ch = string.toCharArray();
		int count = ch.length+1;
		for(int i = 0; i<ch.length; i++) {
			for(int x = count; x>i;x--) {
				String s = string2.substring(count-x+i)+ch[i]+string2.substring(0, count-x);
				list.add(string.substring(0,i)+s);
			}
		}
	}
}