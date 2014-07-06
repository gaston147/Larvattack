package fr.vincentteam.larvattack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import static org.lwjgl.opengl.GL11.*;

public class TextureManager {
	private static HashMap<String, Texture> textures = new HashMap<String, Texture>();
	
	public static Texture getTexture(String address) {
		address = new File(address).getAbsolutePath();
		if(!textures.containsKey(address))
			textures.put(address, loadTexture(address));
		return textures.get(address);
	}

	private static Texture loadTexture(String address) {
		try {
			glEnable(GL_TEXTURE_2D);
			Texture tex = TextureLoader.getTexture("PNG", new FileInputStream(address));
			glDisable(GL_TEXTURE_2D);
			return tex;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
