package core;

import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Interface representing objects that can be collected by a player.
 * Examples include coins, power-ups, mystery blocks.
 */
public interface Collectible {

    /**
     * Handles the action when the player collects this item.
     * This could modify player stats, inventory, or trigger effects.
     *
     * @param player The player who collected the item.
     */
    void collect(Player player);

    /**
     * Updates the state of the collectible.
     * Can be used for animations, timers, or interactions.
     *
     * @param dt     Delta time since last update, in seconds.
     * @param player The player in the game (for proximity checks, etc.).
     */
    void update(double dt, Player player);

    /**
     * Renders the collectible on the screen.
     *
     * @param gc GraphicsContext to draw the collectible.
     */
    void render(GraphicsContext gc);

    /**
     * Returns the hitbox of the collectible for collision detection.
     *
     * @return Rectangle2D representing the hitbox.
     */
    Rectangle2D getHitbox();
}
