package entities.enemies;

import core.Character;
import core.Hittable;
import entities.items.Coin;
import javafx.scene.canvas.GraphicsContext;
import rooms.Room;

/**
 * Abstract base class for all enemy types.
 * Handles health, damage reactions, damage flash, and room reference.
 * Every enemy must implement update and render methods (Some enemies do not use update() but will create its own thread.)
 */
public abstract class Enemy extends Character implements Hittable {
    /** Reference to the room where the enemy exists. */
    protected Room room;

    /** Visual flash effect when the enemy takes damage (0 = none, 1 = full). */
    private double damageFlash = 0;

    /**
     * Constructs an Enemy.
     *
     * @param x        Initial X position.
     * @param y        Initial Y position.
     * @param hp       Health points.
     * @param power    Attack power.
     * @param room     Room where the enemy is located.
     * @param diameter Diameter for hitbox and rendering.
     */
    public Enemy(double x, double y, int hp, int power, Room room, double diameter) {
        super(x, y, hp, power, diameter);
        setRoom(room);
    }

    /**
     * Checks whether the enemy is dead.
     *
     * @return true if dead, false otherwise.
     */
    public boolean isDead() {
        return !isAlive;
    }

    /**
     * Called when the enemy takes damage.
     * Updates HP and triggers damage flash effect.
     *
     * @param damage Amount of damage to apply.
     */
    @Override
    public void onHit(int damage) {
        damageFlash = 1;
        this.setHp(getHp() - damage);
        if (getHp() <= 0) {
            setAlive(false);
        }
    }

    /**
     * Returns the current damage flash value.
     *
     * @return Damage flash (0–1).
     */
    public double getDamageFlash() {
        return damageFlash;
    }

    /**
     * Sets the damage flash value.
     *
     * @param damageFlash Value to set (0–1).
     */
    public void setDamageFlash(double damageFlash) {
        this.damageFlash = damageFlash;
    }

    /**
     * Checks if the enemy is alive.
     *
     * @return true if alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Called when the enemy dies.
     * Default behavior is to spawn a coin at the enemy's location.
     */
    @Override
    public void onDead() {
        room.spawnItems(new Coin(x, y));
    }

    /**
     * Returns the room this enemy belongs to.
     *
     * @return Room object.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room this enemy belongs to.
     *
     * @param room Room object.
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Updates the enemy state each frame.
     *
     * @param dt Delta time since last update (seconds).
     */
    public abstract void update(double dt);

    /**
     * Renders the enemy on the screen.
     *
     * @param gc GraphicsContext used for rendering.
     */
    public abstract void render(GraphicsContext gc);
}
