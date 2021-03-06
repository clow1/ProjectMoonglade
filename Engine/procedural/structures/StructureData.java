package procedural.structures;

import map.Material;
import map.prop.Props;

public class StructureData {
	
	private byte flags;		// uuuuuEBH (E = envtile, B = buildings, H = heights)
	private int width, height, length;
	
	private float[][] terrain;
	private int[][] envTiles;
	private CompTileData[][][] buildingTiles; // ################sssssssswwwwwwww (w = wallflags, s = specialflags
	
	
	public StructureData(int width, int height, int length, byte flags) {
		this.width = width;
		this.height = height;
		this.length = length;
		this.flags = flags;
		
		if ((flags & 0x01) != 0)
			terrain = new float[width][length];
		
		if ((flags & 0x02) != 0)
			buildingTiles = new CompTileData[width][height][length];
			
		if ((flags & 0x04) != 0)
			envTiles = new int[width][length];

	}

	public int getLength() {
		return length;
	}
	
	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean hasTerrain() {
		return ((flags & 0x01) != 0);
	}
	
	public boolean hasBuildingTiles() {
		return ((flags & 0x02) != 0);
	}
	
	public boolean hasEnvTiles() {
		return ((flags & 0x04) != 0);
	}

	public float getTerrain(int i, int j) {
		return terrain[i][j];
	}
	
	public CompTileData getBuildingTile(int i, int j, int k) {
		return buildingTiles[i][j][k];
	}
	
	public Props getProp(int i, int j) {
		return Props.values()[envTiles[i][j]];
	}

	public void setHeight(int i, int j, float height) {
		this.terrain[i][j] = height;
	}

	public void setEnvTile(int i, int j, int id) {
		this.envTiles[i][j] = id;
	}

	public void setBuildingTile(int i, int j, int k, int[] mats, int tileWalls, byte[] tileFlags) {
		this.buildingTiles[i][j][k] = new CompTileData(mats, tileWalls, tileFlags);
	}
}

class CompTileData {
	private byte walls;
	private byte[] flags;
	private int[] materials;
	
	public CompTileData(int[] materials, int walls, byte[] flags) {
		this.materials = materials;
		this.walls = (byte) walls;
		this.flags = flags;
	}
	
	public Material[] getMaterials() {
		final Material[] mats = Material.values();
		final Material[] materials = new Material[7];
		materials[0] = mats[this.materials[0]];
		materials[1] = mats[this.materials[1]];
		materials[2] = mats[this.materials[2]];
		materials[3] = mats[this.materials[3]];
		materials[4] = mats[this.materials[4]];
		materials[5] = mats[this.materials[5]];
		materials[6] = mats[this.materials[6]];
		
		return materials;
	}
	
	public byte getWalls() {
		return walls;
	}
	
	public byte[] getFlags() {
		return flags;
	}
}
