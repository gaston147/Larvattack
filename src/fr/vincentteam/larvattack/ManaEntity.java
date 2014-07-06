package fr.vincentteam.larvattack;

import fr.vincentteam.larvattack.entity.Entity;

public class ManaEntity extends Entity {
	protected float mana;
	
	public ManaEntity(String id, int level) {
		super(id, level);
		mana = 20;
	}
	
	public int getMana() {
		return (int) mana;
	}
	
	public boolean usedMana(int used) {
		if (mana - used < 0)
			return false;
		mana -= used;
		return true;
	}
	
	public void update() {
		super.update();
		if (mana < 20)
			mana += 0.01f;
		if (mana > 20)
			mana = 20;
	}
}
