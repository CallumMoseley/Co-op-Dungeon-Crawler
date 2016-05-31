import java.util.ArrayList;

public class DungeonFactory
{
	private static final int MIN_ROOM_WIDTH = 10, MAX_ROOM_WIDTH = 20,
			MIN_ROOM_HEIGHT = 10, MAX_ROOM_HEIGHT = 20;
	private static final int RIGHT = 1, UP = 2, LEFT = 3, DOWN = 4;
	private static int totalRooms;

	private static int randomWidth()
	{
		return (int) (Math.random() * (MAX_ROOM_WIDTH - MIN_ROOM_WIDTH + 1))
				+ MIN_ROOM_WIDTH;
	}

	private static int randomHeight()
	{
		return (int) (Math.random() * (MAX_ROOM_HEIGHT - MIN_ROOM_HEIGHT + 1))
				+ MIN_ROOM_HEIGHT;
	}

	public static Room generateMap(int numberOfRooms)
	{
		// x, y, width (+ x), height(+ y)
		Room entry = new Room(0, 0, randomWidth(), randomHeight(), 0);
		totalRooms = 1;
		generateConnections(entry, numberOfRooms - 1);
		return entry;
	}

	private static void generateConnections(Room room, int left)
	{
		if (left == 0)
			return;
		
		int roomsConnected = (int) (Math.random() * (Math.min(left, 4))) + 1, successfulConnections = 0;
		boolean newLeft = false, newRight = false, newUp = false, newDown = false;

		for (int tries = 0; tries < 100
				&& roomsConnected != successfulConnections; tries++)
		{
			int direction = (int) (Math.random() * 4) + 1;

			int width = randomWidth(), height = randomHeight();

			if (direction == LEFT && room.getLeft() == null
					&& fits(room, room.x() - width, room.y() + room.height()
							/ 2 - height / 2, width, height,
							new boolean[totalRooms]))
			{
				room.setLeft(new Room(room.x() - width, room.y()
						+ room.height()
						/ 2 - height / 2, width, height, totalRooms));
				totalRooms++;
				successfulConnections++;
				newLeft = true;
				System.out.printf("Room %d placed LEFT of Room %d%n", room
						.getLeft().id(), room.id());
			}
			else if (direction == UP && room.getUp() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() + room.height(), width,
							height, new boolean[totalRooms]))
			{
				room.setUp(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() + room.height(), width, height, totalRooms));
				totalRooms++;
				successfulConnections++;
				newUp = true;
				System.out.printf("Room %d placed UP of Room %d%n", room
						.getUp()
						.id(), room.id());
			}
			else if (direction == RIGHT && room.getRight() == null
					&& fits(room, room.x() + room.width(),
							room.y() + room.height()
									/ 2 - height / 2, width, height,
							new boolean[totalRooms]))
			{
				room.setRight(new Room(room.x() + room.width(),
						room.y() + room.height()
								/ 2 - height / 2, width, height, totalRooms));
				totalRooms++;
				successfulConnections++;
				newRight = true;
				System.out.printf("Room %d placed RIGHT of Room %d%n", room
						.getRight().id(), room.id());
			}
			else if (direction == DOWN && room.getDown() == null
					&& fits(room, room.x() + room.width() / 2 - width / 2,
							room.y() - height, width, height,
							new boolean[totalRooms]))
			{
				room.setDown(new Room(room.x() + room.width() / 2 - width / 2,
						room.y() - height, width, height, totalRooms));
				totalRooms++;
				successfulConnections++;
				newDown = true;
				System.out.printf("Room %d placed DOWN of Room %d%n", room
						.getDown().id(), room.id());
			}
		}

		
		int split = (left - successfulConnections) / successfulConnections;
		
		if (newLeft)
			generateConnections(room.getLeft(), split);
		if (newUp)
			generateConnections(room.getUp(), split);
		if (newRight)
			generateConnections(room.getRight(), split);
		if (newDown)
			generateConnections(room.getDown(), split);
	}

	public static boolean fits(Room room, int x, int y, int width, int height,
			boolean[] vis)
	{
		if (room.x() >= x + width || room.x() + room.width() <= x
				|| room.y() >= y + height || room.y() + room.height() <= y)
		{
			vis[room.id()] = true;

			boolean ret = true;

			if (room.getRight() != null && !vis[room.getRight().id()]
					&& !fits(room.getRight(), x, y, width, height, vis))
				ret = false;
			if (room.getUp() != null && !vis[room.getUp().id()]
					&& !fits(room.getUp(), x, y, width, height, vis))
				ret = false;
			if (room.getLeft() != null && !vis[room.getLeft().id()]
					&& !fits(room.getLeft(), x, y, width, height, vis))
				ret = false;
			if (room.getDown() != null && !vis[room.getDown().id()]
					&& !fits(room.getDown(), x, y, width, height, vis))
				ret = false;

			return ret;
		}
		System.out.printf("Overlap :( %d %d %d %d %d %d %d %d%n", room.x(),
				room.y(),
				room.width(), room.height(), x, y, width, height);
		return false;
	}

	public static void fillMap(Room entry, int difficulty)
	{
		fillRooms(entry, difficulty);
	}

	private static void fillRooms(Room room, int difficulty)
	{

	}
}