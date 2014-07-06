package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.geometry.Vector2f;

public class Bar extends Platform {
	public int size;
	
	public Bar(int size, Vector2f position) {
		super(position, 14 + size * 6, 5);
		this.size = size;
	}
	
	public boolean isSolid() {
		return true;
	}
}
