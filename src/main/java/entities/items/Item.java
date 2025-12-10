package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import rooms.Room;

/**
 * Abstract base class for all collectible items in the game.
 * Handles position, size, collection state, and collision detection with the player.
 */
public abstract class Item implements Collectible {

    /** X coordinate of the item center. */
    private double x;

    /** Y coordinate of the item center. */
    private double y;

    /** Width of the item for hitbox and rendering. */
    private double width;

    /** Height of the item for hitbox and rendering. */
    private double height;

    /** Flag indicating whether the item has been collected. */
    private boolean collected;

    /** Room in which the item exists. */
    private Room room;

    /**
     * Constructs a new Item.
     *
     * @param x      Initial X position.
     * @param y      Initial Y position.
     * @param width  Width of the item.
     * @param height Height of the item.
     */
    public Item(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setCollected(false);
    }

    /**
     * Updates the item state.
     * Checks for collision with the player and collects the item if intersecting.
     *
     * @param dt     Delta time since last update (seconds).
     * @param player Player object to check collision against.
     */
    public void update(double dt, Player player) {
        if (player.getHitbox().intersects(this.getHitbox()) && !isCollected()) {
            collect(player);
            setCollected(true);
        }
    }

    /**
     * Returns the hitbox of the item.
     *
     * @return Rectangle2D representing the item's hitbox.
     */
    @Override
    public Rectangle2D getHitbox() {
        return new Rectangle2D(x - (width / 2), y - (height / 2), width, height);
    }

    /**
     * Renders the item.
     *
     * @param gc GraphicsContext used for rendering.
     */
    public abstract void render(GraphicsContext gc);

    /**
     * Collects the item, applying its effect to the player.
     *
     * @param player Player who collected the item.
     */
    @Override
    public abstract void collect(Player player);

    // --- Getters and Setters ---
    /**
     * Returns the X coordinate of the item.
     * @return X coordinate
     */
    public double getX() { return x; }

    /**
     * Sets the X coordinate of the item.
     * @param x New X coordinate
     */
    public void setX(double x) { this.x = x; }

    /**
     * Returns the Y coordinate of the item.
     * @return Y coordinate
     */
    public double getY() { return y; }

    /**
     * Sets the Y coordinate of the item.
     * @param y New Y coordinate
     */
    public void setY(double y) { this.y = y; }

    /**
     * Returns the width of the item.
     * @return Width
     */
    public double getWidth() { return width; }

    /**
     * Sets the width of the item.
     * @param width New width
     */
    public void setWidth(double width) { this.width = width; }

    /**
     * Returns the height of the item.
     * @return Height
     */
    public double getHeight() { return height; }

    /**
     * Sets the height of the item.
     * @param height New height
     */
    public void setHeight(double height) { this.height = height; }

    /**
     * Returns true if the item has been collected.
     * @return true if collected, false otherwise
     */
    public boolean isCollected() { return collected; }

    /**
     * Sets the collected state of the item.
     * @param collected New collected state
     */
    public void setCollected(boolean collected) { this.collected = collected; }

    /**
     * Returns the room the item belongs to.
     * @return Room object
     */
    public Room getRoom() { return room; }

    /**
     * Sets the room the item belongs to.
     * @param room Room object
     */
    public void setRoom(Room room) { this.room = room; }

}
