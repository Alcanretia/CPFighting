package entities.bullets;

import core.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Represents a laser beam in the game.
 * The laser has a direction, length, damage, and limited lifetime.
 * It is rendered as a thick line and has a rectangular hitbox for collision detection.
 */
public class Laser extends GameObject {
    /** Direction vector of the laser (normalized). */
    private double dirX, dirY;

    /** Length of the laser in pixels. */
    private double length = 800;

    /** Damage dealt by the laser. */
    private int damage;

    /** Whether the laser is still active/alive. */
    private boolean alive = true;

    /** Time remaining before the laser disappears (in seconds). */
    private double lifetime = 0.5;

    /**
     * Constructs a new Laser.
     *
     * @param x      Starting X position of the laser.
     * @param y      Starting Y position of the laser.
     * @param dirX   X component of the laser's direction vector.
     * @param dirY   Y component of the laser's direction vector.
     * @param damage Damage dealt by the laser.
     */
    public Laser(double x, double y, double dirX, double dirY, int damage) {
        super(x, y);
        this.dirX = dirX;
        this.dirY = dirY;
        this.damage = damage;
    }

    /**
     * Updates the laser's lifetime.
     * Marks it as inactive when lifetime expires.
     *
     * @param dt Time elapsed since last update (seconds).
     */
    @Override
    public void update(double dt) {
        lifetime -= dt;
        if (lifetime <= 0) alive = false;
    }

    /**
     * Checks if the laser is still active.
     *
     * @return true if the laser is alive/active, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Renders the laser as a red line on the screen.
     *
     * @param gc GraphicsContext used for drawing.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(20);

        double endX = getX() + dirX * length;
        double endY = getY() + dirY * length;

        gc.strokeLine(getX(), getY(), endX, endY);
    }

    /**
     * Returns the rectangular hitbox of the laser for collision detection.
     *
     * @return Rectangle2D representing the laser's hitbox.
     */
    public Rectangle2D getHitbox() {
        return new Rectangle2D(
                Math.min(getX(), getX() + dirX * length),
                Math.min(getY(), getY() + dirY * length),
                Math.abs(dirX * length),
                Math.abs(dirY * length)
        );
    }

    /**
     * Returns the damage/power of the laser.
     *
     * @return Damage value.
     */
    public int getDamage() {
        return damage;
    }
}
