package entities.bullets;

import core.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import util.CharacterType;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a projectile fired by either the player or an enemy.
 * A Bullet travels in a straight line using its velocity values and
 * deals damage determined by its power. Rendering and collision
 * detection are handled based on a fixed circular hitbox.
 */
public class Bullet extends GameObject {

    /**
     * Horizontal velocity (units per second).
     */
    private double vx;

    /**
     * Vertical velocity (units per second).
     */
    private double vy;

    /**
     * Identifies which character type fired the bullet
     * (player or enemy), used for collision filtering.
     */
    private CharacterType owner;

    /**
     * Damage value dealt when the bullet hits a target.
     */
    private int power;

    /**
     * Constant diameter of the bullet sprite and its hitbox.
     */
    private static final double DIAMETER = 16;

    /**
     * Static sprite image used to render all bullets.
     */
    private static final Image BULLET_SPRITE = new Image("sprites/bullet.png");

    /**
     * Creates a new Bullet instance at the given position with the specified
     * velocity, power, and owner type.
     *
     * @param x      initial x-position
     * @param y      initial y-position
     * @param vx     horizontal velocity
     * @param vy     vertical velocity
     * @param power  damage dealt on impact
     * @param owner  type of character that fired this bullet
     */
    public Bullet(double x, double y, double vx, double vy, int power, CharacterType owner) {
        super(x, y);
        setVx(vx);
        setVy(vy);
        this.owner = owner;
        setPower(power);
    }

    /**
     * Updates the bullet's position based on its velocity and the
     * elapsed time.
     *
     * @param dt time delta (in seconds)
     */
    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    /**
     * Draws the bullet sprite at its current position.
     *
     * @param gc graphics context used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(BULLET_SPRITE, getX() - (DIAMETER / 2), getY() - (DIAMETER / 2), DIAMETER, DIAMETER);
    }

    /**
     * Returns the rectangular hitbox used for collision detection.
     *
     * @return Rectangle2D representing the bullet's hitbox
     */
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX() - (DIAMETER / 2), getY() - (DIAMETER / 2), DIAMETER, DIAMETER);
    }

    /**
     * Gets the character type that fired this bullet.
     *
     * @return owner of the bullet
     */
    public CharacterType getOwner() {
        return owner;
    }

    /**
     * Sets the owner that fired this bullet.
     *
     * @param owner character type (player or enemy)
     */
    public void setOwner(CharacterType owner) {
        this.owner = owner;
    }

    /**
     * Gets the bullet's vertical velocity.
     *
     * @return vertical speed
     */
    public double getVy() {
        return vy;
    }

    /**
     * Sets the bullet's vertical velocity.
     *
     * @param vy new vertical speed
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Gets the bullet's horizontal velocity.
     *
     * @return horizontal speed
     */
    public double getVx() {
        return vx;
    }

    /**
     * Sets the bullet's horizontal velocity.
     *
     * @param vx new horizontal speed
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Gets the bullet's damage output.
     *
     * @return power value
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the bullet's damage, ensuring it is non-negative.
     *
     * @param power damage dealt on impact
     */
    public void setPower(int power) {
        this.power = Math.max(power, 0);
    }

    /**
     * Indicates whether the bullet is still active.
     * This implementation always returns true, but subclasses
     * or game logic may override or extend this behavior.
     *
     * @return true indicating the bullet is active
     */

}
