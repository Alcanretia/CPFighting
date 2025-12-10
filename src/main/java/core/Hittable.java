package core;

import javafx.geometry.Rectangle2D;

/**
 * Interface representing objects that can be hit or take damage in the game.
 */
public interface Hittable {

    /**
     * Called when the object is hit and takes damage.
     *
     * @param damage The amount of damage inflicted.
     */
    void onHit(int damage);

    /**
     * Called when the object dies or is destroyed.
     * Can be used to trigger effects, drops, or removal from the game world.
     */
    void onDead();

    /**
     * Checks whether the object is still alive.
     *
     * @return true if the object is alive, false otherwise.
     */
    boolean isAlive();

    /**
     * Returns the diameter of the object.
     * Used for calculating hitboxes, rendering, and collision areas.
     *
     * @return Diameter of the object.
     */
    double getDiameter();

    /**
     * Returns the hitbox of the object for collision detection.
     *
     * @return Rectangle2D representing the object's hitbox.
     */
    Rectangle2D getHitbox();
}
