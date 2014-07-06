package fr.vincentteam.larvattack;

import java.util.ArrayList;
import java.util.List;

import fr.vincentteam.larvattack.entity.Entity;
import fr.vincentteam.larvattack.geometry.Vector2f;

public class World {
	private List<Entity> entities;
	private List<Platform> platforms;
	private List<Effect> effects;
	private String levelName;
	private Vector2f startPoint;
	
	public World(String levelName) {
		entities = new ArrayList<Entity>();
		platforms = new ArrayList<Platform>();
		effects = new ArrayList<Effect>();
		this.levelName = levelName;
		startPoint = new Vector2f(Larvattack.main.swidth / 2 + 0.75f, 10);
		
		platforms.add(new Bar(100, new Vector2f(-7, 0)));
		platforms.add(new Ladder(10, new Vector2f(50, 5)));
		platforms.add(new Bar(4, new Vector2f(40, 96)));
		platforms.add(new Bar(4, new Vector2f(66, 65)));
		platforms.add(new Bar(4, new Vector2f(100, 80)));
//		platforms.add(new Bar(4, new Vector2f(100, 10)));
//		platforms.add(new Bar(4, new Vector2f(100, 10 + 16 + 5)));
	}
	
	public void spawnEntity(Entity entity) {
		entities.add(entity);
		entity.setWorld(this);
	}

	public List<Entity> getEntities() {
		return entities;
	}
	
	public String getLevelName() {
		return levelName;
	}

	public List<Platform> getPlatforms() {
		return platforms;
	}

	public void update() {
		for (Entity entity : new ArrayList<Entity>(entities)) {
			entity.update();
			if (entity.position.y < -entity.height - 100)
				entity.kill();
			if (entity.isDead())
				entities.remove(entity);
		}
		for (Effect effect : new ArrayList<Effect>(effects)) {
			effect.update();
			if (effect.isDead())
				effects.remove(effect);
		}
	}
	
	public Vector2f getStartPoint() {
		return startPoint.clone();
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}

	public List<Effect> getEffects() {
		return effects;
	}
}
