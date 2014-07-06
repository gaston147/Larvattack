package fr.vincentteam.larvattack;

import org.lwjgl.input.Keyboard;
import fr.vincentteam.larvattack.geometry.Vector2f;

public class Player extends ManaEntity {
	private boolean bdown;
	
	public Player() {
		super("player", 1);
		bdown = false;
	}
	
	public void update() {
		super.update();
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			velocity.x = 1;
			headRotation = false;
			state = (state + 0.1f) % 3;
		} else if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			velocity.x = -1;
			headRotation = true;
			state = (state + 0.1f) % 3;
		} else {
			state = 3;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			if (isInLadder()) {
				move(0, 0.3f * 2);
			} else {
				if (!jumping && onGround) {
					jumping = true;
					velocity.y += 3;
				}
			}
		}
		if (VTMouse.buttonDown(0)) {
			if (!bdown) {
				bdown = true;
				world.addEffect(new Effect(new Vector2f(position.x + (headRotation ? -8 : 8), position.y), new Vector2f(0, 25), 8, 16, 4, 0.1f, headRotation));
			}
		} else {
			bdown = false;
		}
	}
}
