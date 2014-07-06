package fr.vincentteam.larvattack.geometry;

public class Box {
	public Vector2f position, lastPosition;
	public int width, height;
	
	public Box(Vector2f position, Vector2f lastPosition, int width, int height) {
		this.position = position;
		this.lastPosition = lastPosition;
		this.width = width;
		this.height = height;
	}
}
