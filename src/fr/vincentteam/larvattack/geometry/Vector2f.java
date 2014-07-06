package fr.vincentteam.larvattack.geometry;

public class Vector2f implements Cloneable {
	public float x, y;
	
	public Vector2f(float x, float y) {
		set(x, y);
	}
	
	public Vector2f() {
		this(0, 0);
	}
	
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public String toString() {
		return "(" + x + ";" + y + ")";
	}

	public void plus(Vector2f u) {
		if (u != null) {
			x += u.x;
			y += u.y;
		}
	}

	public void minus(Vector2f u) {
		if (u != null) {
			x -= u.x;
			y -= u.y;
		}
	}
	
	public boolean equals(Vector2f u) {
		return x == u.x && y == u.y;
	}
	
	public Vector2f clone() {
		try {
			return (Vector2f) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}
}
