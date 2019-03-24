package Example.board;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Board {
	private ScoreboardManager manager;
	private Scoreboard board;
	private Objective objective;
	private Map<Integer, String> score;

	public Board(String displayName) {
		this.manager = Bukkit.getScoreboardManager();
		this.board = manager.getNewScoreboard();
		this.objective = board.registerNewObjective("test", "dummy", displayName);
		this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.score = new HashMap<Integer, String>();
	}

	public Scoreboard getScoreboard() {
		return board;
	}

	public void setDisplayName(String name) {
		this.objective.setDisplayName(name);
	}
	
	public void insertScore(String name, int index) {
		if(score.containsKey(index)) {
			this.resetScores(score.get(index));
		}
		score.put(index, name);
		Score score = objective.getScore(name);
		score.setScore(index);
	}

	public void resetScores(String name) {
		this.board.resetScores(name);
	}
}