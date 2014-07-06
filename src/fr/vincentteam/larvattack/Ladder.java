package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.geometry.Vector2f;

public class Ladder extends Platform {
	public int height;
	
	public Ladder(int height, Vector2f position) {
		super(position, 16, height * 9 + 1);
		this.height = height;
	}
	
	public boolean isSolid() {
		return false;
	}
}
