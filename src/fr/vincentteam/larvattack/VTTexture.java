package fr.vincentteam.larvattack;

import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public class VTTexture {
	private Texture tex;
	private float[] color;
	private boolean isTexture;
	private boolean loaded;
	public int width, height;
	
	public VTTexture() {
		loaded = false;
	}
	
	public VTTexture(String img) {
		this();
		loadImage(img);
	}
	
	public VTTexture(float red, float green, float blue, float alpha) {
		this();
		loadColor(red, green, blue, alpha);
	}
	
	public void loadImage(String img) {
		load();
		isTexture = true;
		tex = TextureManager.getTexture(img);
		width = tex.getImageWidth();
		height = tex.getImageHeight();
	}
	
	public void loadColor(float red, float green, float blue, float alpha) {
		load();
		isTexture = false;
		color = new float[] {red, green, blue, alpha};
	}
	
	private void load() {
		tex = null;
		color = null;
		loaded = true;
	}
	
	public void bind() {
		if (!loaded)
			throw new RuntimeException("Texture is not loaded");
		if (isTexture) {
			tex.bind();
		} else {
			glColor4f(color[0], color[1], color[2], color[3]);
		}
	}
}
