package rooms;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a boss room, which is a specialized type of CombatRoom.
 * <p>
 * This room is intended to contain a boss enemy. It inherits all
 * behavior from CombatRoom, including enemy updates, collision detection,
 * and door locking/unlocking mechanics.
 */
public class BossRoom extends CombatRoom {

    /**
     * Constructs a BossRoom with specified position, size, and player reference.
     *
     * @param x      the x-coordinate of the top-left corner of the room
     * @param y      the y-coordinate of the top-left corner of the room
     * @param w      the width of the room
     * @param h      the height of the room
     * @param player the player in this room
     */
    public BossRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }
}
