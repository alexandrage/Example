package Example.node;

public class SkillData {
	private int level = 0;

	public int getLevel() {
		return this.level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void upLevel() {
		this.level++;
	}
}