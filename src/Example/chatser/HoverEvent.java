package Example.chatser;

public class HoverEvent {
	public HoverEvent(Action action, BaseComponent value) {
		this.action = action;
		this.value = value;
	}

	private Action action;
	private final BaseComponent value;

	public Action getAction() {
		return this.action;
	}

	public BaseComponent getValue() {
		return this.value;
	}

	public enum Action {
		show_text, show_achievement, show_item, show_entity;
	}
}