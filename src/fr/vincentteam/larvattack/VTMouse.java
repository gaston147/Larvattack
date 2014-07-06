package fr.vincentteam.larvattack;

import java.util.HashMap;

import org.lwjgl.input.Mouse;

public class VTMouse {
	private static HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
	
	public static boolean buttonDown(int button) {
		if (Mouse.isButtonDown(button)) {
			if (map.get(button) != true) {
				map.put(button, true);
				return true;
			}
		} else {
			map.put(button, false);
		}
		return false;
	}
}
