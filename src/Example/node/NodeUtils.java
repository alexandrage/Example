package Example.node;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class NodeUtils {
	public static Map<Player, Map<String, SkillData>> skills = new HashMap<Player, Map<String, SkillData>>();

	public static void setupGui(Player p) {
		SkillGui sgui = new SkillGui("Skill");
		Node<Data> root = add(p, "Skill 1", 10, 5, 18);
		Node<Data> node11 = root.addChild(add(p, "Skill 2", 20, 10, 10));
		node11.addChild(add(p, "Skill 5", 30, 20, 11));
		Node<Data> node12 = root.addChild(add(p, "Skill 3", 20, 10, 19));
		node12.addChild(add(p, "Skill 6", 30, 20, 20));
		Node<Data> node13 = root.addChild(add(p, "Skill 4", 20, 10, 28));
		node13.addChild(add(p, "Skill 7", 30, 20, 29));
		sgui.setItem(root);
		p.openInventory(sgui.getInventory());
	}

	public static Node<Data> add(Player p, String name, int next, int max, int index) {
		int lvl = 0;
		SkillData sdata = skills.get(p).get(name);
		if (sdata != null) {
			lvl = skills.get(p).get(name).getLevel();
		}
		return new Node<Data>(new Data(name, lvl, next, max, index));
	}

	public static void clickAction(InventoryClickEvent e) {
		if (e.getClickedInventory() == null) {
			return;
		}
		if (e.getInventory().getHolder() instanceof SkillGui) {
			e.setCancelled(true);
			Data data = ((SkillGui) e.getClickedInventory().getHolder()).getData(e.getSlot());
			if (data == null) {
				return;
			}
			String name = data.getName();
			int max = data.getMaxLevel();
			Player p = (Player) e.getViewers().get(0);
			Map<String, SkillData> skill = skills.get(p);
			if (skill.get(name) == null) {
				skill.put(name, new SkillData());
			}
			if (skill.get(name).getLevel() < max) {
				skill.get(name).upLevel();
			}
			setupGui(p);
		}
	}
}