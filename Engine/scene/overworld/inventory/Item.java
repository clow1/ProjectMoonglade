package scene.overworld.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import map.Material;
import scene.entity.EntityData;

public class Item {
	private static List<ItemData> items = new ArrayList<ItemData>();
	private static Map<String, Integer> nameMap = new HashMap<String, Integer>();
	
	public final static int AIR = 0, AXE = 3, SPADE = 1, TROWEL = 2;
	
	public static void init() {
		if (items.size() != 0) return;
		addItem("air", 0, 0, Material.NONE);
		addItem("spade", 1, 1, ItemAction.DIG);
		addItem("trowel", 2, 1, ItemAction.DIG);
		addItem("axe", 0, 1, ItemAction.CHOP);
		
		addItem("stone", 1, 0, Material.STONE_WALL);
		addItem("stick", 9, 0, Material.STICK);
		addItem("stick_bundle", 2, 0, Material.STICK_BUNDLE);
		addItem("vine", 8, 0, Material.NONE);
		addItem("plant_fibers", 6, 0, Material.NONE);
		addItem("rope", 5, 0, Material.NONE);
		
		addItem("door", 4, 0, "DoorEntity");
		addItem("forge", 11, 0, "ForgeEntity");
		addItem("reclaimed_metal", 10, 0, Material.NONE);
		
		addItem("red_paint", 1, 1, ItemAction.PAINT);
		addItem("orange_paint", 2, 1, ItemAction.PAINT);
		addItem("yellow_paint", 3, 1, ItemAction.PAINT);
		addItem("green_paint", 4, 1, ItemAction.PAINT);
		addItem("cyan_paint", 5, 1, ItemAction.PAINT);
		addItem("blue_paint", 6, 1, ItemAction.PAINT);
		addItem("indigo_paint", 7, 1, ItemAction.PAINT);
		addItem("violet_paint", 8, 1, ItemAction.PAINT);
		addItem("paint_scraper", 15, 1, ItemAction.PAINT);
		
		addItem("dark_grey_paint", 9, 1, ItemAction.PAINT);
		addItem("light_grey_paint", 10, 1, ItemAction.PAINT);
		addItem("silver_paint", 11, 1, ItemAction.PAINT);

		addItem("forest_green_paint", 12, 1, ItemAction.PAINT);
		
		addItem("health", 0, 0, ItemAction.EAT);
		
		initMaterialItems();
	}

	private static void initMaterialItems() {
		for (Material m : Material.values()) {
			if (m.getDropName() == null) {
				addItem(m.getName(), m);
				m.setDrop(m.getName());
			}
		}
	}
	
	private static void addItem(String name, int tx, int ty, Material material) {
		int id = items.size();
		items.add(new ItemData(id, name, tx, ty, material));
		nameMap.put(name, id);
	}

	private static void addItem(String name, int tx, int ty, ItemAction action) {
		int id = items.size();
		items.add(new ItemData(id, name, tx, ty, action));
		nameMap.put(name, id);
	}

	private static void addItem(String name, int tx, int ty, String entityName) {
		int id = items.size();
		items.add(new ItemData(id, name, tx, ty, EntityData.getId(entityName)));
		nameMap.put(name, id);
	}
	
	public static void addItem(String name, Material material) {
		int id = items.size();
		ItemData itemData = new ItemData(id, name, material.getTX(), material.getTY(), material);
		itemData.useMaterialTexture(true);
		items.add(itemData);
		nameMap.put(name, id);
	}

	public static ItemData get(int id) {
		return items.get(id);
	}

	public static ItemData get(String name) {
		return get(getId(name));
	}

	public static int getId(String name) {
		Integer id = nameMap.get(name);
		return (id == null) ? 0 : id;
	}

	public static Set<String> names() {
		return nameMap.keySet();
	}
}
