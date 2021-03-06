package io.terrain;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joml.Vector3f;

import core.Application;
import dev.Console;
import map.Chunk;
import map.Enviroment;
import scene.entity.Entity;
import scene.entity.EntityHandler;

public class ChunkStreamer {
	
	// String of region file, and chunk
	private Map<String, Map<Integer, Chunk>> loadList = new TreeMap<String, Map<Integer, Chunk>>();
	private Map<String, Map<Integer, Chunk>> saveList = new TreeMap<String, Map<Integer, Chunk>>();
	
	//private List<Chunk> genList = new LinkedList<Chunk>();
	
	public static Thread saveThread = null, loadThread = null;
	
	private boolean closed = false;
	
	private ChunkCallback callback;
	private Enviroment enviroment;

	public ChunkStreamer(String worldName, Enviroment enviroment) {
		this.enviroment = enviroment;
		callback = new ChunkCallback();
	}
	
	public void update() {
		if (!loadList.keySet().isEmpty() && loadThread == null) {
			load();
		}
		
		if (!saveList.keySet().isEmpty() && saveThread == null) {
			save(Application.scene.getCamera().getPosition());
		}
	}
	
	private void save(Vector3f cameraPos) {
		String filename = saveList.keySet().iterator().next();
		saveThread = new Thread(new RegionSaver(callback, filename, saveList.get(filename)));
		saveThread.start();
		saveList.remove(filename);
	}
	
	private void load() {
		String filename =loadList.keySet().iterator().next();
		
		Map<Integer, Chunk> chunks = loadList.get(filename);
		loadThread = new Thread(new RegionLoader(callback, filename, chunks, enviroment));
		
		loadThread.start();
		loadList.remove(filename);
	}
	
	public void queueForSaving(Chunk chunk) {
		if (closed) return;
		if (chunk.editFlags == 0x0) {
			chunk.cleanUp();
			return;
		}
		
		int regionX = Math.floorDiv(chunk.dataX, 16);
		int regionY = 0;
		int regionZ = Math.floorDiv(chunk.dataZ, 16);
		
		int id = RegionSaver.getOffset(chunk.dataX, 0, chunk.dataZ);
		
		String filename = RegionSaver.getFilename(regionX, regionY, regionZ);
		
		Map<Integer, Chunk> map = saveList.get(filename);
		
		if (map == null) {
			map = new TreeMap<Integer, Chunk>();//ConcurrentSkipListMap
			saveList.put(filename, map);
		}
		
		map.put(id, chunk);
	}
	
	public void queueForLoading(Chunk chunk) {
		if (closed) return;
		int regionX = Math.floorDiv(chunk.dataX, 16);
		int regionY = 0;
		int regionZ = Math.floorDiv(chunk.dataZ, 16);
		
		int id = RegionSaver.getOffset(chunk.dataX, 0, chunk.dataZ);
		
		String filename = RegionSaver.getFilename(regionX, regionY, regionZ);
		
		Map<Integer, Chunk> map = loadList.get(filename);
		
		if (map == null) {
			map = new TreeMap<Integer, Chunk>();
			loadList.put(filename, map);
		};
		
		
		map.put(id, chunk);
	}

	public boolean isFinished() {
		return saveList.keySet().isEmpty();
	}

	public void close() {
		closed = true;
	}
}

class ChunkCallback implements ChunkCallbackInterface {
	
	@Override
	public void saveCallback() {
		ChunkStreamer.saveThread = null;
	}
	
	@Override
	public void loadCallback(List<Entity> entities) {
		ChunkStreamer.loadThread = null;
		for(Entity entity : entities) {
			EntityHandler.addEntity(entity);
		}
	}
}
