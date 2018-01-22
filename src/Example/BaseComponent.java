package Example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseComponent {
	BaseComponent() {
	}

	BaseComponent(String text) {
		this.text = text;
	}

	private String text;
	private ChatColor color;
	private Boolean bold;
	private Boolean italic;
	private Boolean underlined;
	private Boolean strikethrough;
	private Boolean obfuscated;
	private String insertion;
	private ClickEvent clickEvent;
	private HoverEvent hoverEvent;
	private BaseComponent[] extra;

	public void setText(String text) {
		this.text = text;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public void setBold(Boolean bold) {
		this.bold = bold;
	}

	public void setItalic(Boolean italic) {
		this.italic = italic;
	}

	public void setUnderlined(Boolean underlined) {
		this.underlined = underlined;
	}

	public void setStrikethrough(Boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public void setObfuscated(Boolean obfuscated) {
		this.obfuscated = obfuscated;
	}

	public void setInsertion(String insertion) {
		this.insertion = insertion;
	}

	public void setClickEvent(ClickEvent clickEvent) {
		this.clickEvent = clickEvent;
	}

	public void setHoverEvent(HoverEvent hoverEvent) {
		this.hoverEvent = hoverEvent;
	}
	
	public void setExtra(BaseComponent[] extra) {
		this.extra = extra;
	}
	
	public String getText() {
		return this.text;
	}
	
	public ChatColor getColor() {
		return this.color;
	}
	
	public Boolean setBold() {
		return this.bold;
	}
	
	public Boolean getItalic() {
		return this.italic;
	}
	
	public Boolean getUnderlined() {
		return this.underlined;
	}
	
	public Boolean getStrikethrough() {
		return this.strikethrough;
	}
	
	public Boolean setObfuscated() {
		return this.obfuscated;
	}
	
	public String getInsertion() {
		return this.insertion;
	}
	
	public ClickEvent getClickEvent() {
		return this.clickEvent;
	}
	
	public HoverEvent getHoverEvent() {
		return this.hoverEvent;
	}
	
	public BaseComponent[] getExtra() {
		return this.extra;
	}

	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(this);
	}
}