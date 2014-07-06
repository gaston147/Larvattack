package fr.vincentteam.larvattack.gui;

import org.lwjgl.input.Mouse;

import fr.vincentteam.larvattack.FontRenderer;
import fr.vincentteam.larvattack.Larvattack;
import fr.vincentteam.larvattack.VTMouse;
import fr.vincentteam.larvattack.geometry.Vector2f;

public class GuiMainMenu extends GuiScreen {
	private FontRenderer fontRenderer;
	private Vector2f link1p, link2p, link3p;
	private String link1s, link2s, link3s;
	private int link1w, link1h, link2w, link2h, link3w, link3h;
	private boolean link1o, link2o, link3o;
	
	public GuiMainMenu() {
		fontRenderer = Larvattack.main.fontRenderer;
		
		link1p = new Vector2f(2, 0);
		link1s = "New game";
		link1w = fontRenderer.widthOf(link1s);
		link1h = fontRenderer.heightOf(link1s);
		
		link2p = new Vector2f(2, 0);
		link2s = "Load game";
		link2w = fontRenderer.widthOf(link2s);
		link2h = fontRenderer.heightOf(link2s);
		
		link3p = new Vector2f(2, 0);
		link3s = "Quit";
		link3w = fontRenderer.widthOf(link3s);
		link3h = fontRenderer.heightOf(link3s);
	}
	
	public void update() {
		Vector2f u = new Vector2f(Mouse.getX() / Larvattack.main.scale, Mouse.getY() / Larvattack.main.scale);
		
		link1p.y = Larvattack.main.sheight - 5 - (2 + 7 * 0);
		link2p.y = Larvattack.main.sheight - 5 - (2 + 7 * 1);
		link3p.y = Larvattack.main.sheight - 5 - (2 + 7 * 2);
		
		link1o = collides(u, link1p, link1w, link1h);
		link2o = collides(u, link2p, link2w, link2h);
		link3o = collides(u, link3p, link3w, link3h);
		if (VTMouse.buttonDown(0)) {
			if (link1o) {
				Larvattack.main.display(new GuiGame(this));
			} else if (link2o) {
				Larvattack.main.display(new GuiGame(this));
			} else if (link3o) {
 				Larvattack.main.stop();
			}
		}
	}
	
	private boolean collides(Vector2f point, Vector2f boxP, int boxW, int boxH) {
		if (point.x >= boxP.x && point.x < boxP.x + boxW && point.y >= boxP.y && point.y < boxP.y + boxH)
			return true;
		return false;
	}

	public void draw() {
		setColor(link1o);
		fontRenderer.render(link1s, link1p);
		
		setColor(link2o);
		fontRenderer.render(link2s, link2p);
		
		setColor(link3o);
		fontRenderer.render(link3s, link3p);
		
		setColor(false);
	}

	private void setColor(boolean b) {
		if (b)
			fontRenderer.setRGB(128 / 255f, 128 / 255f, 128 / 255f);
		else
			fontRenderer.setRGB(64 / 255f, 64 / 255f, 64 / 255f);
	}
}
