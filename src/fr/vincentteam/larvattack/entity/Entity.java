package fr.vincentteam.larvattack.entity;

import java.util.ArrayList;
import java.util.List;

import fr.vincentteam.larvattack.Effect;
import fr.vincentteam.larvattack.Inventory;
import fr.vincentteam.larvattack.Ladder;
import fr.vincentteam.larvattack.Platform;
import fr.vincentteam.larvattack.World;
import fr.vincentteam.larvattack.geometry.Box;
import fr.vincentteam.larvattack.geometry.Vector2f;

public abstract class Entity {
	public Vector2f position, velocity;
	protected Vector2f wantedPos;
	public boolean headRotation;
	private String id;
	protected float state;
	protected float speed;
	protected boolean jumping;
	protected World world;
	public int width, height;
	private int health;
	private boolean dead;
	protected boolean onGround;
	private int level;
	private float exp;
	public Inventory inventory;
	private float expToUp;
	
	public static final float gravity = 0.2f, airResistance = 0.05f;
	
	public Entity(String id, int level) {
		position = new Vector2f();
		velocity = new Vector2f();
		wantedPos = new Vector2f();
		headRotation = true;
		this.id = id;
		state = 3;
		speed = 0;
		jumping = false;
		width = 8;
		height = 16;
		health = 20;
		dead = false;
		this.level = level;
		exp = 0;
		expToUp = _getExpToUp(level);
	}
	
	protected float _getExpToUp(float level) {
		System.out.println("aaa");
		return (float) Math.pow(2, level);
	}

	public String getId() {
		return id;
	}

	public int getState() {
		return (int) state;
	}

	public void update() {
		boolean inLadder = isInLadder();
		move(velocity.x, (inLadder ? -0.3f : velocity.y));
		
		velocity.y -= gravity;
		velocity.x = (velocity.x < -airResistance ? velocity.x + airResistance : (velocity.x > airResistance ? velocity.x - airResistance : 0));
		velocity.y = (velocity.y < -airResistance ? velocity.y + airResistance : (velocity.y > airResistance ? velocity.y - airResistance : 0));
		
		if (inLadder)
			velocity.y = 0;
	}
	
	public void move(float x, float y) {
		wantedPos.set(position.x + x, position.y + y);
		
		Vector2f beforeChecking = wantedPos.clone();
		resolveCollisions();
		
		onGround = false;
		
		if (wantedPos.x != beforeChecking.x)
			velocity.x = 0;
		if (wantedPos.y != beforeChecking.y) {
			if (position.y > beforeChecking.y) {
				jumping = false;
				onGround = true;
				if (velocity.y <= -3)
					damage((int) ((velocity.y * velocity.y) / 5f));
			}
			velocity.y = 0;
		}
		
		position.set(wantedPos.x, wantedPos.y);
	}
	
	public void move(Vector2f u) {
		move(u.x, u.y);
	}
	
	private void resolveCollisions() {
		Box player = getBox();
		
		List<Box> first_list = new ArrayList<Box>();
		List<Box> second_list = new ArrayList<Box>();
		for (Platform platform : world.getPlatforms()) {
			Box obj = platform.getBox();
			if (!platform.isSolid() || !collides(player, obj)) {
				continue;
			}
			if (collidesByAxis(player.lastPosition.x, player.width, obj.position.x, obj.width) || collidesByAxis(player.lastPosition.y, player.height, obj.position.y, obj.height)) {
				first_list.add(obj);
			} else {
				second_list.add(obj);
			}
		}
		for (Box obj : first_list) {
			resolveObjects(player, obj);
		}
		for (Box obj : second_list) {
			resolveObjects(player, obj);
		}
		
		wantedPos.x = player.position.x + 4;
		wantedPos.y = player.position.y;
	}
	
	public Box getBox() {
		return new Box(new Vector2f(wantedPos.x - 4, wantedPos.y), new Vector2f(position.x - 4, position.y), width, height);
	}

	private boolean collidesByAxis(float o1pos, int o1size, float o2pos, int o2size) {
		return o1pos + o1size > o2pos && o2pos + o2size > o1pos;
	}
	
	private boolean collides(Box o1, Box o2) {
		return collidesByAxis(o1.position.x, o1.width, o2.position.x, o2.width) && collidesByAxis(o1.position.y, o1.height, o2.position.y, o2.height);
	}
	
	private float getOverlap(float o1previous_pos, float o1pos, int o1size, float o2pos, int o2size) {
		if (o1previous_pos > o2pos) {
			return -(o2pos + o2size - o1pos);
		} else {
			return o1pos + o1size - o2pos;
		}
	}

	private void resolveObjects(Box o1, Box o2) {
		boolean axis_x = false, axis_y = false;

		float overlap_x, overlap_y;

		boolean pp_collides_x = collidesByAxis(o1.lastPosition.x, o1.width, o2.position.x, o2.width);
		boolean pp_collides_y = collidesByAxis(o1.lastPosition.y, o1.height, o2.position.y, o2.height);

		if (pp_collides_x || pp_collides_y) {
			if (pp_collides_x) {
				axis_y = true;
			}
			if (pp_collides_y) {
				axis_x = true;
			}
		} else {
			axis_x = true;
			axis_y = true;
		}

		overlap_x = getOverlap(o1.lastPosition.x, o1.position.x, o1.width, o2.position.x, o2.width);
		overlap_y = getOverlap(o1.lastPosition.y, o1.position.y, o1.height, o2.position.y, o2.height);

		if (axis_x && axis_y) {
			o1.position.x -= overlap_x;
			o1.position.y -= overlap_y;
		} else {
			if (axis_x) {
				o1.position.x -= overlap_x;
			} else if (axis_y) {
				o1.position.y -= overlap_y;
			}
		}
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	public int getHealth() {
		return health;
	}
	
	public void damage(int damage) {
		health -= damage;
		if (health <= 0)
			kill();
		world.addEffect(new Effect(position.clone(), new Vector2f(0, 9), 16, 16, 4, 0.3f));
	}
	
	public void kill() {
		health = 0;
		dead = true;
	}
	
	public boolean isDead() {
		return dead;
	}
	
	public boolean isInLadder() {
		for (Platform platform : world.getPlatforms())
			if (platform instanceof Ladder && collides(getBox(), platform.getBox()))
				return true;
		return false;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void increaseExp(float exp) {
		float expToUp = this.expToUp, fexp = this.exp + exp;
		if (fexp >= expToUp) {
			int newLevel = 0;
			do {
				newLevel++;
				fexp -= expToUp;
			} while (fexp >= (expToUp = _getExpToUp(level + newLevel)));
			this.expToUp = expToUp;
			this.exp = fexp;
			level += newLevel;
			onLevelUp(newLevel);
		} else {
			this.exp = fexp;
		}
	}
	
	public float getExpToUp() {
		return expToUp;
	}

	protected void onLevelUp(int nbOfLevelUp) {
		System.out.println("LEVEL UP !!! (x" + nbOfLevelUp + ")");
	}
	
	public float getExp() {
		return exp;
	}
}
