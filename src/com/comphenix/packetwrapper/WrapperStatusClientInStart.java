package com.comphenix.packetwrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;

public class WrapperStatusClientInStart extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Status.Client.START;
    
    public WrapperStatusClientInStart() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }
    
    public WrapperStatusClientInStart(PacketContainer packet) {
        super(packet, TYPE);
    }
    
    // There are no fields ...
}


