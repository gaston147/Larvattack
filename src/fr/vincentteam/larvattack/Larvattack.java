package fr.vincentteam.larvattack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.PNGDecoder;

import fr.vincentteam.larvattack.geometry.Vector2f;
import fr.vincentteam.larvattack.gui.GuiMainMenu;
import fr.vincentteam.larvattack.gui.GuiNil;
import fr.vincentteam.larvattack.gui.GuiScreen;

import static org.lwjgl.opengl.GL11.*;

public class Larvattack {
	private boolean running;
	private Thread threadUpdate, threadDrawing;
	public Player player;
	public World world;
	public int width, height, swidth, sheight, scale;
	private boolean displayEnabled;
	public GuiScreen mainMenu, currentGui, uprevGui, dprevGui;
	private Object drawEnd;
	public FontRenderer fontRenderer;
	
	public static Larvattack main;
	
	public static void main(String[] args) {
		main = new Larvattack();
		main.start();
	}
	
	public static String getTitle() {
		return getName() + " " + getVersion();
	}

	public static String getName() {
		return "Larvattack";
	}
	
	public static String getVersion() {
		return "alpha 0.1";
	}
	
	public static ByteBuffer loadIcon(String address) {
		try {
			InputStream is = new File(address).toURI().toURL().openStream();
			try {
				PNGDecoder decoder = new PNGDecoder(is);
				ByteBuffer bb = ByteBuffer.allocateDirect(decoder.getWidth() * decoder.getHeight() * 4);
				decoder.decode(bb, decoder.getWidth() * 4, PNGDecoder.RGBA);
				bb.flip();
				return bb;
			} finally {
				is.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void start() {
		running = true;
		
		threadUpdate = new Thread() {
			public void run() {
				Sync s = new Sync();
				while (running) {
					update();
					
					s.sync(60);
				}
			}
		};
		
		threadDrawing = new Thread() {
			public void run() {
				try {
					Display.setDisplayMode(new DisplayMode(640, 480));
					Display.setTitle(getTitle());
					Display.setIcon(new ByteBuffer[] {
							loadIcon("res/logo16.png"),
							loadIcon("res/logo32.png")
					});
					Display.create();
					Keyboard.create();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				dimensions();
				initGL();
				fontRenderer.graphicsEnabling();
				displayEnabled = true;
				Sync s = new Sync();
				while (running) {
					draw();
					synchronized (drawEnd) {
						drawEnd.notifyAll();
					}
					
					Display.update();
					s.sync(60);
				}
				Display.destroy();
			}
		};
		
		scale = 2;
		displayEnabled = false;
		fontRenderer = new FontRenderer("res/gui.png", new Vector2f(0, 41), "0123456789,.:        " + "abcdefghijklmnopqrstu" + "vwxyz", 3, 5, 21);
		uprevGui = dprevGui = new GuiNil();
		mainMenu = currentGui = new GuiMainMenu();
		drawEnd = new Object();
		
		threadUpdate.start();
		threadDrawing.start();
	}
	
	private void initGL() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, width / scale, 0, height / scale, 1, -1);
		glViewport(0, 0, width, height);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		glDisable(GL_CULL_FACE);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glClearColor(1, 1, 1, 1);
	}
	
	public void update() {
		GuiScreen currentGui = this.currentGui;
		if (!displayEnabled)
			return;
		if (Display.isCloseRequested())
			stop();
		dimensions();
		
		if (uprevGui != currentGui) {
			uprevGui.quit();
			currentGui.join();
			uprevGui = currentGui;
		}
		currentGui.update();
	}

	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glLoadIdentity();
		
		if (dprevGui != currentGui) {
			currentGui.graphicsEnabling();
			dprevGui = currentGui;
		}
		glAccum(GL_RETURN, 0.95f);
		glClear(GL_ACCUM_BUFFER_BIT);
		currentGui.draw();
		glAccum(GL_ACCUM, 0.5f);
	}
	
	public void stop() {
		Thread t = new Thread() {
			public void run() {
				running = false;
				try {
					threadUpdate.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				try {
					threadDrawing.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}
	
	private void dimensions() {
		width = Display.getWidth();
		swidth = width / scale;
		height = Display.getHeight();
		sheight = height / scale;
	}

	public void display(GuiScreen guiScreen) {
		synchronized (drawEnd) {
			try {
				drawEnd.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			currentGui = guiScreen;
		}
	}
}
