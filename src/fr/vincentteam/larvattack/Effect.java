package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.geometry.Vector2f;

public class Effect {
	public Vector2f position, imagePosition;
	public int imageWidth, imageHeight, nbOfTile;
	private float step, state;
	private boolean dead;
	public boolean direction;
	
	public Effect(Vector2f position, Vector2f imagePosition, int imageWidth, int imageHeight, int nbOfTile, float step, boolean direction) {
		this.position = position;
		this.imagePosition = imagePosition;
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
		this.nbOfTile = nbOfTile;
		this.step = step;
		dead = false;
		this.direction = direction;
	}
	
	public Effect(Vector2f position, Vector2f imagePosition, int imageWidth, int imageHeight, int nbOfTile, float step) {
		this(position, imagePosition, imageWidth, imageHeight, nbOfTile, step, false);
	}
	
	public void update() {
		if (state + step > nbOfTile) {
			kill();
		} else {
			state += step;
		}
	}
	
	public int getState() {
		return (int) state;
	}
	
	public void kill() {
		dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}
}
