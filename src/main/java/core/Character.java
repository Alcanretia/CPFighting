package core;

import javafx.geometry.Rectangle2D;

/**
 * Abstract base class representing a character in the game.
 * Characters have health points, power, a hitbox, and a size (diameter).
 * They can be alive or dead and can be attacked or attack others.
 */
public abstract class Character extends GameObject implements Hittable {
    /** Current health points of the character. */
    private int hp;

    /** Attack power of the character. */
    private int power;

    /** Maximum health points the character can have. */
    private int maxHp;

    /** Indicates whether the character is alive. */
    protected boolean isAlive;

    /** Diameter of the character's hitbox. */
    protected double diameter;

    /**
     * Constructs a new Character.
     *
     * @param x       X position of the character's center.
     * @param y       Y position of the character's center.
     * @param hp      Initial health points.
     * @param power   Attack power of the character.
     * @param diameter Diameter of the character's hitbox.
     */
    public Character(double x, double y, int hp, int power, double diameter) {
        super(x, y);
        setMaxHp(hp);
        setHp(hp);
        setPower(power);
        setAlive(true);
        setDiameter(diameter);
    }

    /**
     * Checks if the character is dead.
     *
     * @return true if the character is dead, false otherwise.
     */
    public boolean isDead() {
        return !isAlive;
    }

    /**
     * Returns the hitbox of the character as a rectangle centered on (x, y).
     *
     * @return Rectangle2D representing the character's hitbox.
     */
    public Rectangle2D getHitbox() {
        return new Rectangle2D(x - (diameter / 2), y - (diameter / 2), diameter, diameter);
    }

    /**
     * Returns the diameter of the character's hitbox.
     *
     * @return Diameter of the character.
     */
    public double getDiameter() {
        return diameter;
    }

    /**
     * Sets the diameter of the character's hitbox.
     * Negative values are clamped to 0.
     *
     * @param diameter Diameter to set.
     */
    public void setDiameter(double diameter) {
        this.diameter = diameter < 0 ? 0 : diameter;
    }

    /**
     * Returns the current health points of the character.
     *
     * @return Current HP.
     */
    public int getHp() {
        return hp;
    }

    /**
     * Sets the health points of the character.
     * Values are clamped between 0 and maxHp.
     *
     * @param hp Health points to set.
     */
    public void setHp(int hp) {
        if (hp > getMaxHp()) {
            this.hp = maxHp;
        } else if (hp < 0) {
            this.hp = 0;
        } else this.hp = hp;
    }

    /**
     * Sets the maximum health points of the character.
     * Values below 0 are clamped to 0.
     *
     * @param maxHp Maximum HP to set.
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = Math.max(maxHp, 0);
    }

    /**
     * Returns the maximum health points of the character.
     *
     * @return Maximum HP.
     */
    public int getMaxHp() {
        return maxHp;
    }

    /**
     * Returns the attack power of the character.
     *
     * @return Power of the character.
     */
    public int getPower() {
        return power;
    }

    /**
     * Sets the attack power of the character.
     * Values below 1 are clamped to 1.
     *
     * @param power Power to set.
     */
    public void setPower(int power) {
        this.power = Math.max(power, 1);
    }

    /**
     * Sets whether the character is alive.
     *
     * @param alive True if alive, false otherwise.
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Checks if the character is alive.
     *
     * @return True if alive, false otherwise.
     */
    public boolean isAlive() {
        return isAlive;
    }

}
