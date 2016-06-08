package player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import engine.Stats;
import utility.Constants;
import utility.Vector2D;
import world.Room;

public class Tank extends Player
{

	public Tank()
	{
		super();
		setStats(new Stats(Constants.TANK_HEALTH, Constants.TANK_ATTACK_SPEED,
				Constants.TANK_ATTACK_LENGTH, Constants.TANK_SPEED,
				Constants.TANK_DEFENCE));
	}

	@Override
	public void draw(Graphics g, Vector2D offset)
	{
		Vector2D shifted = getPos().add(offset);
		g.setColor(Color.DARK_GRAY);
		g.fillRect((int) shifted.getX() - getWidth() / 2,
				(int) shifted.getY() - getHeight() / 2, getWidth(),
				getHeight());
	}

	@Override
	public boolean attack(Point p, Room r)
	{
		return super.attack(p, r);
	}

	@Override
	public void ability1(Point p, Room r)
	{

	}

	@Override
	public void ability2(Point p, Room r)
	{

	}

	@Override
	public void ability3(Point p, Room r)
	{
		// TODO Auto-generated method stub

	}
}