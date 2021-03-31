package Example.stack;

import java.nio.ByteBuffer;
import java.util.UUID;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

public class UUIDItemTagType implements PersistentDataType<byte[], UUID> {
	@Override
	public Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public Class<UUID> getComplexType() {
		return UUID.class;
	}

	@Override
	public byte[] toPrimitive(UUID uuid, PersistentDataAdapterContext value) {
		ByteBuffer buffer = ByteBuffer.allocate(16);
		buffer.putLong(uuid.getLeastSignificantBits());
		buffer.putLong(uuid.getMostSignificantBits());
		return buffer.array();
	}

	@Override
	public UUID fromPrimitive(byte[] bytes, PersistentDataAdapterContext value) {
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		long leastBits = buffer.getLong();
		long mostBits = buffer.getLong();
		return new UUID(mostBits, leastBits);
	}
}