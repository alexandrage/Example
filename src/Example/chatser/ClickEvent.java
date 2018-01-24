package Example.chatser;

public class ClickEvent {
	public ClickEvent(Action action, String value) {
		this.action = action;
		this.value = value;
	}

	private Action action;
	private String value;

	public Action getAction() {
		return this.action;
	}

	public String getValue() {
		return this.value;
	}

	public enum Action {
		open_url, open_file, run_command, suggest_command, change_page;
	}
}