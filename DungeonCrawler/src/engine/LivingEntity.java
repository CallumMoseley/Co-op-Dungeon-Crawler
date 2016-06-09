package engine;

import java.awt.Graphics;
import java.util.ArrayList;

import utility.Vector2D;
import world.Room;

public abstract class LivingEntity implements Drawable {
	private Vector2D position;
	private Vector2D speed;
	private Stats stats;
	private AABB hitbox;
	private boolean immobile;
	private boolean invincible;

	private ArrayList<StatusEffect> effects;

	public LivingEntity() {
		position = new Vector2D();
		speed = new Vector2D();
		effects = new ArrayList<StatusEffect>();
		stats = new Stats(0, 0, 0, 0, 0);
		hitbox = new AABB(position.add(new Vector2D(16, 16)), 32, 32);
		immobile = false;
		invincible = false;
	}

	public Vector2D getPos() {
		return position;
	}

	public Vector2D getSpeed() {
		return speed;
	}

	public void setPos(Vector2D v) {
		position = v;
		hitbox.updatePosition(position);
	}

	public void setSpeed(Vector2D v) {
		speed = v.clone();
	}

	public void setStats(Stats s) {
		stats = s;
	}

	public Stats getStats() {
		return stats;
	}

	public void setHitbox(AABB hitbox) {
		this.hitbox = hitbox;
	}

	public AABB getHitbox() {
		return hitbox;
	}

	public ArrayList<StatusEffect> getEffects() {
		return effects;
	}

	public void giveStatusEffect(StatusEffect s) {
		effects.add(s);
	}

	public abstract int getWidth();

	public abstract int getHeight();

	public boolean getImmobile() {
		return immobile;
	}

	public void setImmobile(boolean i) {
		immobile = i;
	}

	public boolean getInvicible() {
		return invincible;
	}

	public void setInvincible(boolean i) {
		invincible = i;
	}
	
	public void damage(int amount) {
		stats.setHealth(Math.max(0, stats.getHealth() - amount));
	}

	public void update(Room l) {
		for (int s = 0; s < effects.size(); s++) {
			effects.get(s).elapseTime();
			if (effects.get(s).getTime() == 0) {
				effects.remove(s);
				s--;
			} else if (effects.get(s).getType() == StatusEffect.HEALTH) {
				stats.setHealth(stats.getHealth() + (int) effects.get(s).getStrength());
			}
		}
		
		AABB tempX = hitbox.clone();
		AABB tempY = hitbox.clone();

		tempX.updatePosition(position.add(new Vector2D(speed.getX() + (speed.getX() < 0 ? -1 : 1), 0)));
		tempY.updatePosition(position.add(new Vector2D(0, speed.getY() + (speed.getY() < 0 ? -1 : 1))));

		Vector2D newSpeed = new Vector2D(0, 0);

		if (!l.hasCollisionWith(tempX)) {
			newSpeed.addToThis(new Vector2D(speed.getX(), 0));
		}

		if (!l.hasCollisionWith(tempY)) {
			newSpeed.addToThis(new Vector2D(0, speed.getY()));
		}

		speed = newSpeed;
		setPos(position.add(speed));
	}

	@Override
	public abstract void draw(Graphics g, Vector2D offset);
}