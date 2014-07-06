package fr.vincentteam.larvattack;

import java.util.ArrayList;
import java.util.HashMap;

import fr.vincentteam.larvattack.entity.Entity;

import static org.lwjgl.opengl.GL11.*;

public class WorldRenderer {
	private World world;
	private HashMap<String, VTTexture> textures;
	private VTTexture bgTex, guiTex;
	
	public WorldRenderer(World world) {
		this.world = world;
		textures = new HashMap<String, VTTexture>();
	}
	
	public void draw() {
		render();
	}
	
	public void render() {
		glEnable(GL_TEXTURE_2D);
		bgTex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); glVertex2f(0, Larvattack.main.sheight);
		glTexCoord2f(640 / 2 / 512f, 0); glVertex2f(Larvattack.main.swidth, Larvattack.main.sheight);
		glTexCoord2f(640 / 2 / 512f, 480 / 2 / 256f); glVertex2f(Larvattack.main.swidth, 0);
		glTexCoord2f(0, 480 / 2 / 256f); glVertex2f(0, 0);
		glEnd();
		float posx, posy;
		for (Platform platform : new ArrayList<Platform>(world.getPlatforms())) {
			if (platform == null)
				continue;
			glPushMatrix();
			glTranslatef(platform.position.x, platform.position.y, 0);
			guiTex.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glColor3f(1, 1, 1);
			if (platform instanceof Bar) {
				glBegin(GL_QUADS);
				glTexCoord2f(0, 0); glVertex2f(0, 5);
				glTexCoord2f(7f / 64f, 0); glVertex2f(7, 5);
				glTexCoord2f(7f / 64f, 5f / 64f); glVertex2f(7, 0);
				glTexCoord2f(0, 5f / 64f); glVertex2f(0, 0);
				glEnd();
				glTranslatef(7, 0, 0);
				for (int i = 0; i < ((Bar) platform).size; i++) {
					glBegin(GL_QUADS);
					glTexCoord2f(7f / 64f, 0); glVertex2f(0, 5);
					glTexCoord2f(13f / 64f, 0); glVertex2f(6, 5);
					glTexCoord2f(13f / 64f, 5f / 64f); glVertex2f(6, 0);
					glTexCoord2f(7f / 64f, 5f / 64f); glVertex2f(0, 0);
					glEnd();
					glTranslatef(6, 0, 0);
				}
				glBegin(GL_QUADS);
				glTexCoord2f(13f / 64f, 0); glVertex2f(0, 5);
				glTexCoord2f(20f / 64f, 0); glVertex2f(7, 5);
				glTexCoord2f(20f / 64f, 5f / 64f); glVertex2f(7, 0);
				glTexCoord2f(13f / 64f, 5f / 64f); glVertex2f(0, 0);
				glEnd();
			} else if (platform instanceof Ladder) {
				for (int i = 0; i < ((Ladder) platform).height; i++) {
					glBegin(GL_QUADS);
					glTexCoord2f(42 / 64f, 0); glVertex2f(0, 9);
					glTexCoord2f((42 + 16) / 64f, 0); glVertex2f(16, 9);
					glTexCoord2f((42 + 16) / 64f, 9 / 64f); glVertex2f(16, 0);
					glTexCoord2f(42 / 64f, 9 / 64f); glVertex2f(0, 0);
					glEnd();
					glTranslatef(0, 9, 0);
				}
				glBegin(GL_QUADS);
				glTexCoord2f(42 / 64f, 8 / 64f); glVertex2f(0, 1);
				glTexCoord2f((42 + 16) / 64f, 8 / 64f); glVertex2f(16, 1);
				glTexCoord2f((42 + 16) / 64f, 9 / 64f); glVertex2f(16, 0);
				glTexCoord2f(42 / 64f, 9 / 64f); glVertex2f(0, 0);
				glEnd();
			}
			glPopMatrix();
		}
		for (Entity entity : new ArrayList<Entity>(world.getEntities())) {
			if (entity == null)
				continue;
			glPushMatrix();
			glTranslatef(entity.position.x, entity.position.y, 0);
			if (entity.headRotation) {
				glScalef(-1, 1, 1);
			}
			getTexture(entity.getId()).bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glColor3f(1, 1, 1);
			posx = 0.25f * (float) entity.getState();
			glBegin(GL_QUADS);
			glTexCoord2f(posx, 0); glVertex2f(-4, 16);
			glTexCoord2f(posx + 0.25f, 0); glVertex2f(4, 16);
			glTexCoord2f(posx + 0.25f, 1); glVertex2f(4, 0);
			glTexCoord2f(posx, 1); glVertex2f(-4, 0);
			glEnd();
			glPopMatrix();
		}
		for (Effect effect : new ArrayList<Effect>(world.getEffects())) {
			if (effect == null)
				continue;
			glPushMatrix();
			glTranslatef(effect.position.x, effect.position.y, 0);
			if (effect.direction) {
				glScalef(-1, 1, 1);
			}
			guiTex.bind();
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glColor3f(1, 1, 1);
			posx = effect.imagePosition.x + effect.imageWidth * effect.getState();
			posy = effect.imagePosition.y;
			glBegin(GL_QUADS);
			glTexCoord2f(posx / guiTex.width, posy / guiTex.height); glVertex2f(-effect.imageWidth / 2, effect.imageHeight);
			glTexCoord2f((posx + effect.imageWidth) / guiTex.width, posy / guiTex.height); glVertex2f(effect.imageWidth / 2, effect.imageHeight);
			glTexCoord2f((posx + effect.imageWidth) / guiTex.width, (posy + effect.imageHeight) / guiTex.height); glVertex2f(effect.imageWidth / 2, 0);
			glTexCoord2f(posx / guiTex.width, (posy + effect.imageHeight) / guiTex.height); glVertex2f(-effect.imageWidth / 2, 0);
			glEnd();
			glPopMatrix();
		}
		glDisable(GL_TEXTURE_2D);
	}
	
	public void graphicsEnabling() {
		bgTex = new VTTexture("res/worlds/" + world.getLevelName() + "/bg.png");
		guiTex = new VTTexture("res/gui.png");
	}
	
	public VTTexture getTexture(String id) {
		if (!textures.containsKey(id))
			textures.put(id, new VTTexture("res/sprites/" + id + ".png"));
		return textures.get(id);
	}
}
