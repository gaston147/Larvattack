package fr.vincentteam.larvattack;

import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import fr.vincentteam.larvattack.geometry.Vector2f;

import static org.lwjgl.opengl.GL11.*;

public class FontRenderer {
	private String img;
	private String chars;
	private Vector2f imagePosition;
	private VTTexture tex;
	private int imageW, imageH, charW, charH, charPerLine;
	private float r, g, b;
	
	public FontRenderer(String img, Vector2f imagePosition, String chars, int charW, int charH, int charPerLine) {
		this.img = img;
		this.imagePosition = imagePosition;
		this.chars = chars;
		this.charW = charW;
		this.charH = charH;
		this.charPerLine = charPerLine;
		
		r = g = b = 64 / 255f;
	}
	
	public void render(String str, Vector2f position) {
		str = str.toLowerCase();
		glEnable(GL_TEXTURE_2D);
		glPushMatrix();
		glTranslatef(position.x, position.y, 0);
		tex.bind();
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		glColor3f(r, g, b);
		for (char c : str.toCharArray()) {
			if (chars.indexOf(c) != -1)
				renderChar(c);
			glTranslatef(charW + 1, 0, 0);
		}
		glPopMatrix();
		glDisable(GL_TEXTURE_2D);
	}
	
	private void renderChar(char c) {
		int imageX = (int) imagePosition.x + (chars.indexOf(c) % charPerLine) * charW, imageY = (int) imagePosition.y + (int) (chars.indexOf(c) / charPerLine) * charH;
		glBegin(GL_QUADS);
		glTexCoord2f(imageX / (float) imageW, imageY / (float) imageH); glVertex2f(0, charH);
		glTexCoord2f((imageX + charW) / (float) imageW, imageY / (float) imageH); glVertex2f(charW, charH);
		glTexCoord2f((imageX + charW) / (float) imageW, (imageY + charH) / (float) imageH); glVertex2f(charW, 0);
		glTexCoord2f(imageX / (float) imageW, (imageY + charH) / (float) imageH); glVertex2f(0, 0);
		glEnd();
	}
	
	public int widthOf(String str) {
		return str.length() * charW + str.length() - 1;
	}

	public int heightOf(String str) {
		return charH;
	}

	public void graphicsEnabling() {
		tex = new VTTexture(img);
		imageW = tex.width;
		imageH = tex.height;
	}
	
	public void setRGB(float r, float g, float b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}
}
