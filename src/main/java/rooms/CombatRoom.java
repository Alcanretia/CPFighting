package rooms;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A room where the player must defeat all enemies to unlock the door.
 * <p>
 * The top door remains locked until all enemies in the room are defeated.
 */
public class CombatRoom extends Room {

    /**
     * Constructs a CombatRoom with specified position, size, and player reference.
     *
     * @param x      top-left x-coordinate of the room
     * @param y      top-left y-coordinate of the room
     * @param w      width of the room
     * @param h      height of the room
     * @param player the player in the room
     */
    public CombatRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }

    /**
     * Adds an enemy to the room.
     *
     * @param e the enemy to spawn
     */
    public void spawnEnemy(Enemy e) {
        addEnemy(e);
    }

    /**
     * Updates the room and its entities.
     * <p>
     * The top door is locked if any enemies remain, and unlocked if all enemies are defeated.
     *
     * @param dt delta time in seconds
     */
    @Override
    public void update(double dt) {
        updateEntities(dt);
        if (getEnemies().isEmpty()) {
            topDoor.setLocked(false);
        } else {
            topDoor.setLocked(true);
        }
    }

    /**
     * Renders the room and its entities.
     * <p>
     * Adds a red fog overlay if the door is locked, otherwise black fog.
     *
     * @param gc the GraphicsContext to draw on
     */
    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);

        if (topDoor.isLocked()) {
            drawFog(gc, Color.RED, 0.50);
        } else {
            drawFog(gc, Color.BLACK, 0.70);
        }

        renderEntities(gc);
    }
}
