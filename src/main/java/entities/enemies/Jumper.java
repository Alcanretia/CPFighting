package entities.enemies;

import entities.bullets.Bullet;
import javafx.scene.image.Image;
import rooms.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.CharacterType;

/**
 * Represents a Jumper enemy.
 * The Jumper randomly jumps within a radius around its current position,
 * and shoots four bullets in four directions after each jump.
 */
public class Jumper extends Enemy {

    /** Target X coordinate for the current jump. */
    private double targetX;

    /** Target Y coordinate for the current jump. */
    private double targetY;

    /** Flag indicating whether the Jumper is currently jumping. Volatile ensures every thread see the change */
    private volatile boolean jumping = false;

    /** Maximum jump distance from current position. */
    private double jumpRadius = 150;

    /** Sprite used to render the Jumper. */
    private static final Image JUMPER_SPRITE = new Image("sprites/jumper.png");

    /**
     * Constructs a new Jumper enemy.
     *
     * @param x        Initial X position.
     * @param y        Initial Y position.
     * @param room     Room where the enemy resides.
     * @param hp       Health points of the Jumper.
     * @param power    Attack power of the Jumper.
     * @param diameter Diameter for hitbox and rendering.
     */
    public Jumper(double x, double y, Room room, int hp, int power, double diameter) {
        super(x, y, hp, power, room, diameter);

        Thread behavior = new Thread(this::loopBehavior);
        behavior.setDaemon(true);
        behavior.start();
    }

    /**
     * Main AI loop running in a separate thread.
     * Handles waiting, jumping, and shooting behavior.
     */
    private void loopBehavior() {
        try {
            while (isAlive) {
                if (!room.isActive()) {
                    Thread.sleep(50);
                    continue;
                }

                // Wait before next jump
                Thread.sleep(1000 + (int) (Math.random() * 600));

                pickRandomTarget();
                jumping = true;

                smoothMove(0.4);
                jumping = false;

                shoot4Directions();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Picks a random target position within the jump radius.
     * Ensures the target remains inside room bounds.
     */
    private void pickRandomTarget() {
        double angle = Math.random() * 2 * Math.PI;
        double distance = Math.random() * jumpRadius;

        targetX = x + Math.cos(angle) * distance;
        targetY = y + Math.sin(angle) * distance;

        targetX = Math.max(25, Math.min(room.getWidth() - 25, targetX));
        targetY = Math.max(25, Math.min(room.getHeight() - 25, targetY));
    }

    /**
     * Smoothly moves the Jumper toward the target position over a specified duration.
     *
     * @param durationSeconds Duration of the movement in seconds.
     * @throws InterruptedException If the thread is interrupted.
     */
    private void smoothMove(double durationSeconds) throws InterruptedException {
        double startX = x;
        double startY = y;

        double time = 0;
        double dt = 0.01; // ~100 FPS movement

        while (time < durationSeconds) {
            double t = time / durationSeconds;
            double smooth = (1 - Math.cos(t * Math.PI)) / 2; // ease in-out

            x = startX + (targetX - startX) * smooth;
            y = startY + (targetY - startY) * smooth;

            Thread.sleep((int) (dt * 1000));
            time += dt;
        }

        x = targetX;
        y = targetY;
    }

    /**
     * Shoots bullets in four cardinal directions (up, down, left, right).
     */
    private void shoot4Directions() {
        if (isDead()) return;
        room.spawnBullet(new Bullet(x, y, 300, 0, this.getPower(), CharacterType.JumperType));   // Right
        room.spawnBullet(new Bullet(x, y, -300, 0, this.getPower(), CharacterType.JumperType));  // Left
        room.spawnBullet(new Bullet(x, y, 0, 300, this.getPower(), CharacterType.JumperType));   // Down
        room.spawnBullet(new Bullet(x, y, 0, -300, this.getPower(), CharacterType.JumperType));  // Up
    }

    /**
     * Updates the Jumper each frame.
     * Handles damage flash animation.
     *
     * @param dt Delta time since last update (seconds).
     */
    @Override
    public void update(double dt) {
        if (getDamageFlash() > 0) {
            setDamageFlash(getDamageFlash() - dt * 2);
            if (getDamageFlash() < 0) setDamageFlash(0);
        }
        // Position updates handled by behavior thread
    }

    /**
     * Renders the Jumper, including its sprite and damage flash.
     *
     * @param gc GraphicsContext used for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX() - (diameter / 2) - 1, getY() - (diameter / 2) - 1, diameter + 2, diameter + 2);
        gc.drawImage(JUMPER_SPRITE, getX() - (diameter / 2), getY() - (diameter / 2), diameter, diameter);

        if (getDamageFlash() > 0) {
            gc.setGlobalAlpha(getDamageFlash());
            gc.setFill(Color.WHITE);
            gc.fillRect(x - diameter / 2, y - diameter / 2, diameter, diameter);
            gc.setGlobalAlpha(1); // reset alpha
        }
    }
}
