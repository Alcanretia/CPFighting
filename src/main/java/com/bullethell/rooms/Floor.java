/*package com.bullethell.rooms;

import java.util.ArrayList;
import java.util.List;
import com.bullethell.entities.player.Player;

public class Floor {
    private List<Room> rooms = new ArrayList<>();
    private int currentIndex = 0;
    private Player player;

    public Floor() {
        player = new Player(400, 300);

        rooms.add(new CombatRoom());
        rooms.add(new TreasureRoom());

        for (Room r : rooms) r.load(player);
    }

    public Room getCurrentRoom() { return rooms.get(currentIndex); }
    public Player getPlayer() { return player; }

    public void moveTo(int index) {
        if (index >= 0 && index < rooms.size()) {
            currentIndex = index;
        }
    }
}*/