package fr.vincentteam.larvattack;

import java.util.HashMap;

import org.lwjgl.input.Keyboard;

public class VTKeyboard {
	private static HashMap<Integer, Boolean> map = new HashMap<Integer, Boolean>();
	
	public static boolean onePress(int key) {
		if (Keyboard.isKeyDown(key)) {
			if (map.get(key) != true) {
				map.put(key, true);
				return true;
			}
		} else {
			map.put(key, false);
		}
		return false;
	}
}
