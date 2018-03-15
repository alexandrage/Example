package Example.chatser;

import org.bukkit.plugin.Plugin;

import com.comphenix.packetwrapper.WrapperPlayServerChat;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers.ChatType;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.gson.Gson;

public class PacketServerChat extends PacketAdapter {
	private static Gson gson = new Gson();
	public PacketServerChat(Plugin plugin, PacketType[] type) {
		super(plugin, type);
	}

	public void onPacketSending(PacketEvent event) {
		WrapperPlayServerChat packet = new WrapperPlayServerChat(event.getPacket());
		WrappedChatComponent wchat = packet.getMessage();
		ChatType ctype = packet.getChatType();
		if (ctype == ChatType.SYSTEM) {
			if (wchat != null) {
				BaseComponent bs = gson.fromJson(wchat.getJson(), BaseComponent.class);
			}
		}
	}
}