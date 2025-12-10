package entities.bullets;

import core.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import util.CharacterType;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a bullet in the game.
 * Bullets have a velocity, power, an owner (who fired it), and a hitbox for collision detection.
 */
public class Bullet extends GameObject {
    /** Horizontal velocity of the bullet. */
    private double vx;

    /** Vertical velocity of the bullet. */
    private double vy;

    /** The type of character who owns/fired the bullet. */
    private CharacterType owner;

    /** Power (damage) of the bullet. */
    private int power;

    /** Diameter of the bullet. */
    private final static double DIAMETER = 16;

    /** Sprite image used to render the bullet. */
    private static Image BULLET_SPRITE;

    /**
     * Constructs a new Bullet.
     *
     * @param x      Initial X position.
     * @param y      Initial Y position.
     * @param vx     Horizontal velocity.
     * @param vy     Vertical velocity.
     * @param power  Damage power of the bullet.
     * @param owner  Owner of the bullet (who fired it).
     */
    public Bullet(double x, double y, double vx, double vy, int power, CharacterType owner) {
        super(x, y);
        setVx(vx);
        setVy(vy);
        this.owner = owner;
        setPower(power);
    }

    /**
     * Updates the position of the bullet based on its velocity.
     *
     * @param dt Time elapsed since the last update (seconds).
     */
    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    /**
     * Renders the bullet on the screen.
     *
     * @param gc GraphicsContext used for drawing.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getBulletSprite(), getX() - (DIAMETER / 2), getY() - (DIAMETER / 2), DIAMETER, DIAMETER);
    }

    /**
     * Returns the sprite image for the bullet.
     * Loads the image lazily on first call.
     *
     * @return Image of the bullet.
     */
    public Image getBulletSprite() {
        if (BULLET_SPRITE == null) {
            BULLET_SPRITE = new Image("sprites/bullet.png");
        }
        return BULLET_SPRITE;
    }

    /**
     * Returns the hitbox of the bullet for collision detection.
     *
     * @return Rectangle2D representing the bullet's hitbox.
     */
    public Rectangle2D getHitBox() {
        return new Rectangle2D(getX() - (DIAMETER / 2), getY() - (DIAMETER / 2), DIAMETER, DIAMETER);
    }

    /**
     * Returns the owner of the bullet.
     *
     * @return CharacterType representing the bullet's owner.
     */
    public CharacterType getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the bullet.
     *
     * @param owner CharacterType to set as the bullet's owner.
     */
    public void setOwner(CharacterType owner) {
        this.owner = owner;
    }

    /**
     * Returns the vertical velocity of the bullet.
     *
     * @return Vertical velocity.
     */
    public double getVy() {
        return vy;
    }

    /**
     * Sets the vertical velocity of the bullet.
     *
     * @param vy Vertical velocity to set.
     */
    public void setVy(double vy) {
        this.vy = vy;
    }

    /**
     * Returns the horizontal velocity of the bullet.
     *
     * @return Horizontal velocity.
     */
    public double getVx() {
        return vx;
    }

    /**
     * Sets the horizontal velocity of the bullet.
     *
     * @param vx Horizontal velocity to set.
     */
    public void setVx(double vx) {
        this.vx = vx;
    }

    /**
     * Returns the damage power of the bullet.
     *
     * @return Power of the bullet.
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the damage power of the bullet. Values below 0 are clamped to 0.
     *
     * @param power Power to set.
     */
    public void setPower(int power) {
        this.power = Math.max(power, 0);
    }
}
