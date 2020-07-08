package gl.res;

import org.joml.Vector3f;

public class PropModel {
	private Vector3f bounds;
	private MeshData[] models;
	private float positionVariance, scaleVariance;
	
	public PropModel(int subModels, Vector3f bounds) {
		this.models = new MeshData[subModels];
		this.bounds = bounds;
	}
	
	public void addSubModel(byte flags, float[] vertices, float[] uvs, float[] normals, int[] indices) {
		for(int i = 0; i < models.length; i++) {
			if (models[i] == null) {
				models[i] = new MeshData(flags, vertices, uvs, normals, indices);
			}
		}
	}
	
	public int getNumSubmodels() {
		return models.length;
	}
	
	public byte getFlags(int i) {
		return models[i].flags;
	}

	public float[] getVertices(int i) {
		return models[i].vertices;
	}

	public float[] getUvs(int i) {
		return models[i].uvs;
	}

	public float[] getNormals(int i) {
		return models[i].normals;
	}

	public int[] getIndices(int i) {
		return models[i].indices;
	}

	public void setPositionVariance(float positionVariance) {
		this.positionVariance = positionVariance;
	}
	
	public void setScaleVariance(float scaleVariance) {
		this.scaleVariance = scaleVariance;
	}
	
	public float getPositionVariance() {
		return positionVariance;
	}
	
	public float getScaleVariance() {
		return scaleVariance;
	}

	public Vector3f getBounds() {
		return bounds;
	}

	public Model toOpenGLModel() {
		
		MeshData sm = models[0];
		final Model model = Model.create();
		model.bind();
		model.createAttribute(0, sm.vertices, 3);
		model.createAttribute(1, sm.uvs, 2);
		model.createAttribute(2, sm.normals, 3);
		model.createIndexBuffer(sm.indices);
		model.unbind();
		return model;
	}

	public PropModel copyAndShiftTexture(float dtx, float dty) {
		PropModel newPropModel = new PropModel(this.getNumSubmodels(), this.bounds);
		for(int i = 0; i < models.length; i++) {
			if (models[i] != null) {
				float[] uvs = new float[models[i].uvs.length];
				for(int j = 0; j < models[i].uvs.length; j += 2) {
					uvs[j] = models[i].uvs[j] + dtx;
					uvs[j+1] = models[i].uvs[j+1] + dty;
				}
				newPropModel.addSubModel(models[i].flags, models[i].vertices, uvs, models[i].normals, models[i].indices);
			}
		}
		
		return newPropModel;
	}

	public PropModel copy(PropModel propModel) {
		PropModel newPropModel = new PropModel(this.getNumSubmodels(), this.bounds);
		for(int i = 0; i < models.length; i++) {
			if (models[i] != null) {
				newPropModel.addSubModel(models[i].flags, models[i].vertices, models[i].uvs, models[i].normals, models[i].indices);
			}
		}
		
		return newPropModel;
	}
}
