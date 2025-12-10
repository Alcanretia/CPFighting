package entities.enemies;

import entities.bullets.Bullet;
import entities.items.Coin;
import entities.player.Player;
import javafx.scene.image.Image;
import rooms.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.CharacterType;

/**
 * Represents a Chaser enemy in the game.
 * The Chaser moves toward the player along one axis at a time,
 * and charges at the player when aligned horizontally or vertically.
 * Upon death, it spawns a coin and four bullets in four directions.
 */
public class Chaser extends Enemy {
    /** Normal movement speed in pixels per second. */
    private static final double baseSpeed = 80;

    /** Charging speed when aligned with the player. */
    private static final double chargeSpeed = 150;

    /** Horizontal velocity component. */
    private double vx = 0;

    /** Vertical velocity component. */
    private double vy = 0;

    /** Reference to the player object. */
    private Player player;

    /** Static image used to render the Chaser sprite. */
    private static final Image CHASER_SPRITE = new Image("sprites/chaser.png");

    /**
     * Constructs a new Chaser enemy.
     *
     * @param x        Initial X position.
     * @param y        Initial Y position.
     * @param room     Room the enemy is in.
     * @param player   Reference to the player.
     * @param hp       Health points of the Chaser.
     * @param power    Attack power of the Chaser.
     * @param diameter Diameter of the Chaser for hitbox and rendering.
     */
    public Chaser(double x, double y, Room room, Player player, int hp, int power, double diameter) {
        super(x, y, hp, power, room, diameter);
        setPlayer(player);
    }

    /**
     * Called when the Chaser dies.
     * Spawns bullets in four directions and drops a coin.
     */
    public void onDead() {
        room.spawnBullet(new Bullet(x, y, 300, 0, this.getPower(), CharacterType.ChaserType));
        room.spawnBullet(new Bullet(x, y, -300, 0, this.getPower(), CharacterType.ChaserType));
        room.spawnBullet(new Bullet(x, y, 0, 300, this.getPower(), CharacterType.ChaserType));
        room.spawnBullet(new Bullet(x, y, 0, -300, this.getPower(), CharacterType.ChaserType));
        room.spawnItems(new Coin(x, y));
    }

    /**
     * Updates the Chaser's position and damage flash each frame.
     * Moves toward the player along one axis and charges if aligned.
     *
     * @param dt Delta time since last update (seconds).
     */
    @Override
    public void update(double dt) {
        if (isDead()) return;

        if (getDamageFlash() > 0) {
            setDamageFlash(getDamageFlash() - dt * 2);
            if (getDamageFlash() < 0) setDamageFlash(0);
        }

        double dx = player.getX() - x;
        double dy = player.getY() - y;

        // Decide which axis to move along
        if (Math.abs(dx) > Math.abs(dy)) {
            vx = Math.signum(dx);
            vy = 0;
        } else {
            vx = 0;
            vy = Math.signum(dy);
        }

        // Determine speed: base or charge
        double speed;
        if (vx != 0 && Math.abs(dy) < 50) {
            speed = chargeSpeed;
        } else if (vy != 0 && Math.abs(dx) < 50) {
            speed = chargeSpeed;
        } else {
            speed = baseSpeed;
        }

        x += vx * speed * dt;
        y += vy * speed * dt;
    }

    /**
     * Renders the Chaser, including its sprite and damage flash.
     *
     * @param gc GraphicsContext used for drawing.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX() - (diameter / 2) - 1, getY() - (diameter / 2) - 1, diameter + 2, diameter + 2);
        gc.drawImage(CHASER_SPRITE, getX() - (getDiameter() / 2), getY() - (getDiameter() / 2), getDiameter(), getDiameter());

        if (getDamageFlash() > 0) {
            gc.setGlobalBlendMode(javafx.scene.effect.BlendMode.SRC_ATOP);
            gc.setFill(Color.rgb(255, 255, 255, getDamageFlash())); // white flash
            gc.fillRect(x - diameter / 2, y - diameter / 2, diameter, diameter);
            gc.setGlobalBlendMode(javafx.scene.effect.BlendMode.SRC_OVER); // reset
        }
    }

    /**
     * Returns the player object that the Chaser is targeting.
     *
     * @return Player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Sets the player object that the Chaser targets.
     *
     * @param player Player object.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
}
