package rooms;

import entities.items.MysteryBox;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a store room where the player can purchase items.
 * <p>
 * This room contains multiple {@link MysteryBox} items, each with a fixed cost.
 * Players can collect these items if they have enough coins.
 */
public class StoreRoom extends Room {
    private static final int ITEMCOST = 10;

    /**
     * Constructs a StoreRoom with specified position, size, and player reference.
     * <p>
     * Spawns three MysteryBox items in fixed positions within the room.
     *
     * @param x      the x-coordinate of the top-left corner of the room
     * @param y      the y-coordinate of the top-left corner of the room
     * @param w      the width of the room
     * @param h      the height of the room
     * @param player the player in this room
     */
    public StoreRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
        spawnItems(new MysteryBox(200, 300, this, ITEMCOST));
        spawnItems(new MysteryBox(400, 300, this, ITEMCOST));
        spawnItems(new MysteryBox(600, 300, this, ITEMCOST));
    }
}
