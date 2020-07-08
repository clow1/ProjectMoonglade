package scene.overworld.inventory;

import map.Chunk;
import map.Material;
import map.tile.Tile;

public class ItemData {
	private final int tx, ty;
	private final Material material;
	private int entity = -1;
	private String name;
	private ItemAction action = ItemAction.NONE;
	public int id;
	private boolean usingMaterialTexture;
	
	ItemData(int id, String name, int tx, int ty, Material m) {
		this.tx = tx;
		this.ty = ty;
		this.id = id;
		this.material = m;
		this.name = name;
	}
	
	ItemData(int id, String name, int tx, int ty, int entity) {
		this.tx = tx;
		this.ty = ty;
		this.id = id;
		this.entity = entity;
		this.material = Material.NONE;
		this.name = name;
	}
	
	ItemData(int id, String name, int tx, int ty, ItemAction action) {
		this.tx = tx;
		this.ty = ty;
		this.id = id;
		this.action = action;
		this.material = Material.NONE;
		this.name = name;
	}
	
	public int getTX() {
		return tx;
	}
	
	public int getTY() {
		return ty;
	}

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public int getEntityId() {
		return entity;
	}

	public boolean doAction(Tile tile, int facingIndex, Chunk chunk) {
		return action.doAction(this.id, facingIndex, tile, chunk);
	}

	public void useMaterialTexture(boolean b) {
		this.usingMaterialTexture = b;
	}
	
	public boolean isUsingMaterialTexture() {
		return usingMaterialTexture;
	}
}
