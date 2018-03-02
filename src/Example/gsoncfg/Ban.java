package Example.gsoncfg;

public class Ban {
	String type;
	String reason;

	Ban(String type, String reason) {
		this.type = type;
		this.reason = reason;
	}

	public String getType() {
		return this.type;
	}

	public String getReason() {
		return this.reason;
	}
}