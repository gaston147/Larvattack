package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.geometry.Box;
import fr.vincentteam.larvattack.geometry.Vector2f;

public abstract class Platform {
	public Vector2f position;
	public int width, height;
	
	public Platform(Vector2f position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
		calculateAABB();
	}
	
	private void calculateAABB() {
		
	}

	public Box getBox() {
		return new Box(position, null, width, height);
	}
	
	public abstract boolean isSolid();
}
