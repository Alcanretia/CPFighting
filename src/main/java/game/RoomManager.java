package game;

import entities.enemies.Boss;
import entities.enemies.Chaser;
import entities.enemies.Jumper;
import entities.player.Player;
import rooms.*;
import javafx.scene.canvas.GraphicsContext;

import java.util.HashMap;
import java.util.Map;

public class RoomManager {
    private static final int BOSS_INTERVAL = 0;
    private static final int TREASURE_INTERVAL = 5;
    private static final int SHOP_INTERVAL = 7;

    private int roomCleared = 0;
    private Room currentRoom;

    private Player player;

    private static RoomManager instance;

    private RoomManager(Player player) {
        this.player = player;
        currentRoom = new NormalRoom(0, 0, 800, 600, player);
        createTopDoor(currentRoom);

        currentRoom.setActive(true);
    }
    public static RoomManager getInstance(Player player) {
        if (instance == null) {
            instance = new RoomManager(player);
        }
        return instance;
    }

    // Later calls (no need to pass player again)
    public static RoomManager getInstance() {
        return instance;
    }

    public void createTopDoor(Room room){
        double w = room.getWidth();
        room.addDoor(new Door(w/2 - 45, 0));
    }

    public Room createNextRoom() {
        roomCleared++;
        Room room;
        if (roomCleared % 10 == BOSS_INTERVAL){
            BossRoom br = new BossRoom(0, 0, 800, 600, player);
            room = br;
            br.spawnEnemy(new Boss(400,300,room, 10, 1, player,60));
        }
        else if (roomCleared % 10 == TREASURE_INTERVAL){
            TreasureRoom tr = new TreasureRoom(0,0,800,600, player);
            room = tr;
        }
        else if (roomCleared % 10 == SHOP_INTERVAL){
            StoreRoom sr = new StoreRoom(0,0,800,600, player);
            room = sr;
        }
        else {
            CombatRoom cr = new CombatRoom(0, 0, 800, 600, player);
            room = cr;
            // spawn 1–3 enemies
            int count = 1 + (int) (Math.random() * 5); // 1–5 enemies

            for (int i = 0; i < count; i++) {
                double ex = 200 + Math.random() * 400;
                double ey = 150 + Math.random() * 300;

                double choice = Math.random();
                if (choice > 0.5) cr.spawnEnemy(new Jumper(ex, ey, room, 3, 1,20));
                else cr.spawnEnemy(new Chaser(ex, ey, room, player, 5, 1,20));
            }
        }
        return room;
    }

    public void enterNextRoom(){
        Room newRoom = createNextRoom();
        createTopDoor(newRoom);

        currentRoom.setActive(false);
        currentRoom = newRoom;
        currentRoom.setActive(true);

        player.setX(GameApp.WIDTH / 2);
        player.setY(GameApp.HEIGHT - 80);
    }

    public void update(double dt, Player player) {
        if (currentRoom == null) return;

        Door door = currentRoom.getDoorAtPlayer(player.getHitbox());
        if (door != null){
            enterNextRoom();
        }
        currentRoom.update(dt);
    }

    public void render(GraphicsContext gc, Player player) {
        if (currentRoom != null) {
            currentRoom.render(gc);
            player.render(gc);
        }
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}