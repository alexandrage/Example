package Example.board;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.scoreboard.Scoreboard;

public class Boards {
	public static Map<String, Board> boardMap = new ConcurrentHashMap<String, Board>();

	public static Scoreboard setubBoard(String player) {
		Board board = new Board("displayName");
		boardMap.put(player, board);
		return board.getScoreboard();
	}
}