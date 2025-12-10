package game;

import entities.enemies.Boss;
import entities.enemies.Chaser;
import entities.enemies.Jumper;
import entities.player.Player;
import rooms.*;
import javafx.scene.canvas.GraphicsContext;

/**
 * Singleton class that manages room creation, transitions, and difficulty scaling.
 * Handles which room the player is in, spawning enemies, and tracking cleared rooms.
 */
public class RoomManager {

    /** Interval at which boss rooms appear */
    private static final int BOSS_INTERVAL = 0;

    /** Interval at which treasure rooms appear */
    private static final int TREASURE_INTERVAL = 5;

    /** Interval at which store rooms appear */
    private static final int SHOP_INTERVAL = 7;

    /** Number of rooms the player has cleared */
    private int roomCleared = 0;

    /** Currently active room */
    private Room currentRoom;

    /** Difficulty scaling factor for enemy spawning */
    private double difficulty = 1.0;

    /** The player instance */
    private Player player;

    /** Singleton instance */
    private static RoomManager instance;

    /**
     * Private constructor for singleton pattern.
     *
     * @param player the player instance to assign to rooms
     */
    private RoomManager(Player player) {
        this.player = player;
        currentRoom = new NormalRoom(0, 0, 800, 600, player);
        createTopDoor(currentRoom);
        currentRoom.setActive(true);
    }

    /**
     * Retrieves the singleton instance of RoomManager and initializes it if necessary.
     *
     * @param player the player instance
     * @return the RoomManager singleton instance
     */
    public static RoomManager getInstance(Player player) {
        if (instance == null) {
            instance = new RoomManager(player);
        }
        return instance;
    }

    /**
     * Retrieves the singleton instance for later calls, after it has been initialized.
     *
     * @return the RoomManager singleton instance
     */
    public static RoomManager getInstance() {
        return instance;
    }

    /** Resets the singleton instance (for restarting the game). */
    public void resetInstance() {
        instance = null;
    }

    /**
     * Adds a door at the top of the room.
     *
     * @param room the room to add the door to
     */
    public void createTopDoor(Room room) {
        double w = room.getWidth();
        room.addDoor(new Door(w / 2 - 45, 0));
    }

    /**
     * Creates the next room based on the last digit of roomsCleared and intervals.
     * Boss, treasure, shop, and combat rooms are spawned accordingly.
     * Last digit = 0 -> BossRoom
     * Last digit = 5 -> TreasureRoom
     * Last digit = 7 -> StoreRoom
     * Anything else  -> CombatRoom
     * @return the newly created room
     */
    public Room createNextRoom() {
        roomCleared++;
        Room room;
        if (roomCleared % 10 == BOSS_INTERVAL) {
            BossRoom br = new BossRoom(0, 0, 800, 600, player);
            room = br;
            br.spawnEnemy(new Boss(400, 300, room, (int)(20 * difficulty) , 1, player, 60));
        } else if (roomCleared % 10 == TREASURE_INTERVAL) {
            TreasureRoom tr = new TreasureRoom(0, 0, 800, 600, player);
            room = tr;
        } else if (roomCleared % 10 == SHOP_INTERVAL) {
            StoreRoom sr = new StoreRoom(0, 0, 800, 600, player);
            room = sr;
        } else {
            CombatRoom cr = new CombatRoom(0, 0, 800, 600, player);
            room = cr;
            int count = 1 + (int) (Math.random() * (int) (3 * difficulty)); // 1–3 enemies

            for (int i = 0; i < count; i++) {
                double ex = 200 + Math.random() * 400;
                double ey = 150 + Math.random() * 300;
                double choice = Math.random();
                if (choice > 0.5) cr.spawnEnemy(new Jumper(ex, ey, room, (int)(3 * difficulty), 1, 30));
                else cr.spawnEnemy(new Chaser(ex, ey, room, player, (int)(5 * difficulty), 1, 30));
            }
            room = cr;
        }
        return room;
    }

    /** Transitions the player to the next room, creating a top door and resetting player position. */
    public void enterNextRoom() {
        Room newRoom = createNextRoom();
        createTopDoor(newRoom);

        currentRoom.setActive(false);
        currentRoom = newRoom;
        currentRoom.setActive(true);

        player.setX(GameApp.WIDTH / 2);
        player.setY(GameApp.HEIGHT - 80);
    }

    /**
     * Updates the current room and checks for door collisions.
     *
     * @param dt     delta time in seconds
     * @param player the player instance
     */
    public void update(double dt, Player player) {
        if (currentRoom == null) return;

        Door door = currentRoom.getDoorAtPlayer(player.getHitbox());
        if (door != null && !door.isLocked()) {
            enterNextRoom();
        }
        currentRoom.update(dt);
    }

    /**
     * Renders the current room and the player.
     *
     * @param gc     the GraphicsContext for drawing
     * @param player the player instance
     */
    public void render(GraphicsContext gc, Player player) {
        if (currentRoom != null) {
            currentRoom.render(gc);
            player.render(gc);
        }
    }

    /** @return the currently active room */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /** @return the player instance */
    public Player getPlayer() {
        return player;
    }

    /** @param player the player instance to set */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /** Increases the difficulty scaling factor for future rooms. */
    public void increaseDifficulty() {
        difficulty += 0.25;
    }

    /**
     * Returns the player's score as a 3-digit string.
     *
     * @return score formatted as 3 digits (e.g., "007")
     */
    public String getScore() {
        return String.format("%03d", roomCleared - 1);
    }
}
