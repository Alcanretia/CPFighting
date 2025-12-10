package rooms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Represents a door in a room.
 * <p>
 * A door can be locked or unlocked and can detect if a player intersects it.
 * It also renders a locked or unlocked sprite depending on its state.
 */
public class Door {
    private Rectangle2D region;
    private boolean locked;

    private static final Image LOCKEDDOOR_SPRITE = new Image("sprites/door_locked.png");
    private static final Image UNLOCKEDDOOR_SPRITE = new Image("sprites/Door-Unlocked.png");
    private static final double WIDTH = 90, HEIGHT = 30, IMAGE_HEIGHT = 60;

    private double x, y;

    /**
     * Constructs a door at the specified position.
     *
     * @param x top-left x-coordinate of the door
     * @param y top-left y-coordinate of the door
     */
    public Door(double x, double y) {
        region = new Rectangle2D(x, y, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        locked = false;
    }

    /**
     * Checks if the given hitbox intersects with this door.
     *
     * @param hitbox the hitbox to check
     * @return true if the hitbox intersects the door, false otherwise
     */
    public boolean intersects(Rectangle2D hitbox) {
        return region.intersects(hitbox);
    }

    /**
     * Sets the locked state of the door.
     *
     * @param locked true to lock the door, false to unlock
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Checks if the door is currently locked.
     *
     * @return true if the door is locked, false otherwise
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Renders the door using the appropriate sprite based on its locked state.
     *
     * @param gc the GraphicsContext to draw on
     */
    public void render(GraphicsContext gc) {
        if (isLocked()) {
            gc.drawImage(LOCKEDDOOR_SPRITE, x, y, WIDTH, IMAGE_HEIGHT);
        } else {
            gc.drawImage(UNLOCKEDDOOR_SPRITE, x, y, WIDTH, IMAGE_HEIGHT);
        }
    }
}
