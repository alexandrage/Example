package Example.event;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import Example.Main;
import Example.chatser.PacketServerChat;

public class Packet {
	public void hack(Main plugin) {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketServerChat(plugin, new PacketType[] { PacketType.Play.Server.CHAT }));
	}
}