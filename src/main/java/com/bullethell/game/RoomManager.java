package com.bullethell.game;

import com.bullethell.entities.enemies.Jumper;
import com.bullethell.entities.player.Player;
import com.bullethell.rooms.CombatRoom;
import com.bullethell.rooms.Door;
import com.bullethell.rooms.NormalRoom;
import com.bullethell.rooms.Room;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomManager {

    private Map<Integer, Room> rooms = new HashMap<>();
    private Room currentRoom;

    // grid size for door generation
    private int cols, rows;

    public RoomManager(int cols, int rows) {
        this.cols = cols;
        this.rows = rows;
    }

    public void createRooms() {

        for (int id = 0; id < cols * rows; id++) {

            Room room;

            if (id == 0) {
                // Starting room (not combat)
                room = new NormalRoom(id, 0, 0, 800, 600);
            } else {

                double chance = Math.random(); // 0.0 → 1.0

                if (chance < 0.7) {
                    // 70% chance combat room
                    CombatRoom cr = new CombatRoom(id, 0, 0, 800, 600);
                    room = cr;
                    // spawn 1–3 enemies
                    int count = 1 + (int)(Math.random() * 3); // 1–3 enemies

                    for (int i = 0; i < count; i++) {
                        double ex = 200 + Math.random() * 400;
                        double ey = 150 + Math.random() * 300;

                        cr.spawnEnemy(new Jumper(ex, ey, room, 3));
                    }


                } else {
                    // normal room
                    room = new NormalRoom(id, 0, 0, 800, 600);
                }
            }

            addRoom(room);
        }

        currentRoom = rooms.get(0); // start in room 0
    }

    public void addRoom(Room room) {
        rooms.put(room.getId(), room);
    }

    public Room getRoom(int id) {
        return rooms.get(id);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(int id) {
        currentRoom = rooms.get(id);
    }

    public void update(double dt, Player player) {
        if (currentRoom == null) return;
        currentRoom.update(dt);
        player.update(dt);

        Door door = currentRoom.getDoorAtPlayer(player.getHitbox());
        if (door != null){
            setCurrentRoom(door.getDestination());
        }
    }

    public void render(GraphicsContext gc, Player player) {
        if (currentRoom != null) {
            currentRoom.render(gc);
            player.render(gc);
        }
    }

    public void generateDoors() {
        for (Room room : rooms.values()) {

            int id = room.getId();
            int cx = id % cols;
            int cy = id / cols;

            double W = room.getWidth();
            double H = room.getHeight();

            // UP
            if (cy > 0) {
                int upId = id - cols;
                room.addDoor(Room.Direction.UP,
                        new Door(W/2 - 30, 0, 60, 20, upId));
            }

            // DOWN
            if (cy < rows - 1) {
                int downId = id + cols;
                room.addDoor(Room.Direction.DOWN,
                        new Door(W/2 - 30, H - 20, 60, 20, downId));
            }

            // LEFT
            if (cx > 0) {
                int leftId = id - 1;

                room.addDoor(Room.Direction.LEFT,
                        new Door(0, H/2 - 30, 20, 60, leftId));
            }

            // RIGHT
            if (cx < cols - 1) {
                int rightId = id + 1;

                room.addDoor(Room.Direction.RIGHT,
                        new Door(W - 20, H/2 - 30, 20, 60, rightId));
            }
        }
    }
}