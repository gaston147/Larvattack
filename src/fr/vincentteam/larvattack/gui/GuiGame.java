package fr.vincentteam.larvattack.gui;

import org.lwjgl.input.Keyboard;

import fr.vincentteam.larvattack.FontRenderer;
import fr.vincentteam.larvattack.Larvattack;
import fr.vincentteam.larvattack.Player;
import fr.vincentteam.larvattack.VTTexture;
import fr.vincentteam.larvattack.World;
import fr.vincentteam.larvattack.WorldRenderer;
import fr.vincentteam.larvattack.geometry.Vector2f;
import fr.vincentteam.larvattack.gui.GuiEnd.GameEnd;

import static org.lwjgl.opengl.GL11.*;

public class GuiGame extends GuiScreen {
	private GuiScreen parent;
	private World world, prevWorld;
	private WorldRenderer worldRenderer;
	private boolean isNew;
	private VTTexture guiTex;
	private Vector2f fontPosition;
	
	public GuiGame(GuiScreen parent) {
		this.parent = parent;
		fontPosition = new Vector2f();
		
		Larvattack.main.world = new World(String.valueOf((int) (Math.random() * 2)));
		Larvattack.main.player = new Player();
		Larvattack.main.player.position = Larvattack.main.world.getStartPoint();
		Larvattack.main.world.spawnEntity(Larvattack.main.player);
	}
	
	public void update() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Larvattack.main.display(parent);
			return;
		}
		world = Larvattack.main.world;
		if (world != prevWorld)
			genNewWorldRenderer();
		world.update();
		if (!world.getEntities().contains(Larvattack.main.player))
			Larvattack.main.display(new GuiEnd(GameEnd.LOSE));
		Larvattack.main.player.increaseExp(1000000f);
	}
	
	public void draw() {
		if (worldRenderer != null) {
			if (checkNewWorldRenderer())
				worldRenderer.graphicsEnabling();
			worldRenderer.draw();
		}
		drawPlayerStats();
	}
	
	private void drawPlayerStats() {
		Player player = Larvattack.main.player;
		glPushMatrix();
		glTranslatef(5, Larvattack.main.sheight - 4 - 5, 0);
		renderBar(player.getHealth(), 1, 0, 0);
		glPushMatrix();
		glTranslatef(0, -5, 0);
		renderBar(player.getMana(), 0, 0, 1);
		glPopMatrix();
		glTranslatef(63, 0, 0);
		float nb = ((float) player.getExp() / (float) player.getExpToUp()) * 50f;
		renderBar1(nb, 0, 0, 1);
		glPopMatrix();
		glEnable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(50, Larvattack.main.sheight - 15 - 2, 0);
		guiTex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(32 / 64f, 25 / 64f); glVertex2f(0, 15);
		glTexCoord2f((32 + 15) / 64f, 25 / 64f); glVertex2f(15, 15);
		glTexCoord2f((32 + 15) / 64f, (25 + 15) / 64f); glVertex2f(15, 0);
		glTexCoord2f(32 / 64f, (25 + 15) / 64f); glVertex2f(0, 0);
		glEnd();
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
		
		FontRenderer fontRenderer = Larvattack.main.fontRenderer;
		
		String str = String.valueOf(player.getLevel());
		fontPosition.set(57 - (fontRenderer.widthOf(str) - 1) / 2, Larvattack.main.sheight - 5 - 7);
		fontRenderer.render(str, fontPosition);
		
		fontRenderer.setRGB(1, 1, 1);
		
		String str1 = "x:" + player.position.x, str2 = "y:" + player.position.y;
		Vector2f pos = new Vector2f(200, Larvattack.main.sheight - 5 - 7);
		fontRenderer.render(str1, pos);
		pos.y -= 7;
		fontRenderer.render(str2, pos);
		
		fontRenderer.setRGB(64 / 255f, 64 / 255f, 64 / 255f);
	}
	
	public void renderBar(int param, float red, float green, float blue) {
		glEnable(GL_TEXTURE_2D);
		guiTex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 5 / 64f); glVertex2f(0, 4);
		glTexCoord2f(42 / 64f, 5 / 64f); glVertex2f(42, 4);
		glTexCoord2f(42 / 64f, (5 + 4) / 64f); glVertex2f(42, 0);
		glTexCoord2f(0, (5 + 4) / 64f); glVertex2f(0, 0);
		glEnd();
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(1, 1, 0);
		glColor3f(red, green, blue);
		glBegin(GL_QUADS);
		glVertex2f(0, 2);
		glVertex2f(param * 2, 2);
		glVertex2f(param * 2, 0);
		glVertex2f(0, 0);
		glEnd();
		glPopMatrix();
	}
	
	public void renderBar1(float param, float red, float green, float blue) {
		glEnable(GL_TEXTURE_2D);
		guiTex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(1, 1, 1);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 56 / 64f); glVertex2f(0, 4);
		glTexCoord2f(52 / 64f, 56 / 64f); glVertex2f(56, 4);
		glTexCoord2f(52 / 64f, (56 + 4) / 64f); glVertex2f(56, 0);
		glTexCoord2f(0, (56 + 4) / 64f); glVertex2f(0, 0);
		glEnd();
		
		glDisable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(1, 1, 0);
		glColor3f(red, green, blue);
		glBegin(GL_QUADS);
		glVertex2f(0, 2);
		glVertex2f(param, 2);
		glVertex2f(param, 0);
		glVertex2f(0, 0);
		glEnd();
		glPopMatrix();
	}

	public void join() {
		prevWorld = Larvattack.main.world;
		genNewWorldRenderer();
	}

	private void genNewWorldRenderer() {
		worldRenderer = new WorldRenderer(Larvattack.main.world);
		isNew = true;
	}
	
	private boolean checkNewWorldRenderer() {
		return isNew ? !(isNew = !isNew) : false;
	}
	
	public void graphicsEnabling() {
		guiTex = new VTTexture("res/gui.png");
	}
}
