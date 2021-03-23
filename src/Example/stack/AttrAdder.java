package Example.stack;

import java.util.UUID;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.inventory.EquipmentSlot;

public class AttrAdder {
	private AttributeModifier modifier;
	private Attribute attribute;

	public static AttrAdder inst(Attribute attribute, String name, double amount, Operation operation) {
		return new AttrAdder(attribute, name, amount, operation);
	}

	public AttrAdder(Attribute attribute, String name, double amount, Operation operation) {
		this.attribute = attribute;
		this.modifier = new AttributeModifier(name, amount, operation);
	}

	public AttrAdder(Attribute attribute, String name, double amount, Operation operation, EquipmentSlot slot) {
		this.attribute = attribute;
		this.modifier = new AttributeModifier(UUID.fromString(name), name, amount, operation, slot);
	}

	public Attribute getAttribute() {
		return this.attribute;
	}

	public AttributeModifier getAttributeModifier() {
		return this.modifier;
	}
}