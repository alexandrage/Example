package Example;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Packet {
	public void hack(Main plugin) {
		ProtocolManager manager = ProtocolLibrary.getProtocolManager();
		manager.addPacketListener(new PacketServerChat(plugin, new PacketType[] { PacketType.Play.Server.CHAT }));
	}
}