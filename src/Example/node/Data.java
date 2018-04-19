package Example.node;

public class Data {

	private String name;
	private int level;
	private int nextNode;
	private int maxLevel;
	private int index;

	public Data(String name, int level, int maxLevel, int nextNode, int index) {
		this.name = name;
		this.level = level;
		this.maxLevel = maxLevel;
		this.nextNode = nextNode;
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public int getLevel() {
		return this.level;
	}

	public int getMaxLevel() {
		return this.maxLevel;
	}

	public int getNext() {
		return this.nextNode;
	}

	public int getIndex() {
		return this.index;
	}
}