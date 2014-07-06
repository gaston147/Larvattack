package fr.vincentteam.larvattack.gui;

import org.lwjgl.input.Mouse;

import fr.vincentteam.larvattack.FontRenderer;
import fr.vincentteam.larvattack.Larvattack;
import fr.vincentteam.larvattack.VTMouse;
import fr.vincentteam.larvattack.geometry.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class GuiEnd extends GuiScreen {
	private GameEnd gameEnd;
	private String str;
	private FontRenderer fontRenderer;
	private Vector2f position;
	
	private Vector2f link1p;
	private String link1s;
	private int link1w, link1h;
	private boolean link1o;
	
	public enum GameEnd {
		WIN, LOSE
	}
	
	public GuiEnd(GameEnd gameEnd) {
		this.gameEnd = gameEnd;
		fontRenderer = Larvattack.main.fontRenderer;
		
		link1p = new Vector2f(0, -7);
		link1s = "ok";
		link1w = fontRenderer.widthOf(link1s);
		link1h = fontRenderer.heightOf(link1s);
	}
	
	public void update() {
		Vector2f u = new Vector2f(Mouse.getX() / Larvattack.main.scale, Mouse.getY() / Larvattack.main.scale);
		
		link1o = collides(u, link1p, link1w, link1h);
		
		if (VTMouse.buttonDown(0))
			if (link1o)
				Larvattack.main.display(Larvattack.main.mainMenu);
	}
	
	private boolean collides(Vector2f point, Vector2f boxP, int boxW, int boxH) {
		if (point.x >= boxP.x && point.x < boxP.x + boxW && point.y >= boxP.y && point.y < boxP.y + boxH)
			return true;
		return false;
	}
	
	public void draw() {
		glPushMatrix();
		glTranslatef(Larvattack.main.swidth / 2, Larvattack.main.sheight / 2, 0);
		fontRenderer.render(str, position);
		glPopMatrix();
		
		link1p.set(Larvattack.main.swidth / 2, Larvattack.main.sheight / 2 - 10);
		setColor(link1o);
		fontRenderer.render(link1s, link1p);
		
		setColor(false);
	}
	
	private void setColor(boolean b) {
		if (b)
			fontRenderer.setRGB(128 / 255f, 128 / 255f, 128 / 255f);
		else
			fontRenderer.setRGB(64 / 255f, 64 / 255f, 64 / 255f);
	}
	
	public void graphicsEnabling() {
		if (gameEnd == GameEnd.LOSE)
			str = "GAME OVER";
		else if (gameEnd == GameEnd.WIN)
			str = "You win the game!!";
		position = new Vector2f(-fontRenderer.widthOf(str) / 2, -fontRenderer.heightOf(str) / 2);
	}
}
