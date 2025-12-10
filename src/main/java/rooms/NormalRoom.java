package rooms;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a standard room with no special behavior.
 * <p>
 * This room does not override the default update or rendering behavior
 * from the base Room class.
 * Used for starting (first) room.
 */
public class NormalRoom extends Room {

    /**
     * Constructs a NormalRoom with specified position, size, and player reference.
     *
     * @param x      the x-coordinate of the top-left corner of the room
     * @param y      the y-coordinate of the top-left corner of the room
     * @param w      the width of the room
     * @param h      the height of the room
     * @param player the player in this room
     */
    public NormalRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }
}
