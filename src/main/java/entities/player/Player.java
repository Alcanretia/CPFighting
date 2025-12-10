package entities.player;

import core.Character;
import core.Hittable;
import core.SpecialAbilityCapable;
import entities.bullets.Bullet;
import entities.bullets.Laser;
import game.RoomManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.CharacterType;
import util.Input;
import util.SFXManager;

/**
 * Represents the player character in the game.
 * Handles movement, shooting, dashing, and special abilities.
 */
public class Player extends Character implements Hittable, SpecialAbilityCapable {
    /** Name of the player. */
    private String name;

    /** Movement speed in units per second. */
    private static final double SPEED = 250;

    /** Bullet speed constants. */
    private static final double BULLET_SPEED = 400;

    /** Coins collected by the player. */
    private int coins;

    /** Time tracker for shooting. */
    private long lastShotTime = 0;

    /** Shooting cooldown in milliseconds. */
    private static final double shootCooldown = 200;

    /** Last laser direction (X). */
    private double lastDirX = 1;

    /** Last laser direction (Y). */
    private double lastDirY = 0;

    /** Time tracker for laser special ability. */
    private long lastLaserTime = 0;

    /** Laser cooldown in milliseconds. */
    private static final long LASER_COOLDOWN = 3000;

    /** Dash state */
    private boolean isDashing = false;

    /** Current dashing duration */
    private double dashTime = 0;

    /** Max dashing duration */
    private static final double DASH_DURATION = 0.15;

    /** Dash speed */
    private static final double DASH_SPEED = 650;

    /** Dash cooldown */
    private static final double DASH_COOLDOWN = 1.0;

    /** Time tracker for dash cooldown */
    private double dashCooldownTimer = 0;

    /** Dash indicator (X axis) */
    private double dashDirX = 0;

    /** Dash indicator (Y axis) */
    private double dashDirY = 0;

    /** Player sprite image. */
    private static Image PLAYER_SPRITE;

    /** Damage flash alpha for visual feedback. */
    private double damageFlash;

    /**
     * Constructs a Player object at the specified position with stats.
     *
     * @param x X coordinate
     * @param y Y coordinate
     * @param hp Initial health points
     * @param power Attack power
     * @param cooldown Attack cooldown in ms
     * @param name Player name
     * @param diameter Diameter for hitbox rendering
     */
    public Player(double x, double y, int hp, int power, String name, double diameter) {
        super(x, y, hp, power, diameter);
        setName(name);
        setCoin(0);
        damageFlash = 0;
    }

    /**
     * Performs a dash in the current movement direction if cooldown allows.
     */
    public void dash() {
        if (isDashing || dashCooldownTimer > 0) return;

        double dirX = 0, dirY = 0;
        if (Input.isHeld("W")) dirY -= 1;
        if (Input.isHeld("S")) dirY += 1;
        if (Input.isHeld("A")) dirX -= 1;
        if (Input.isHeld("D")) dirX += 1;

        if (dirX == 0 && dirY == 0) dirY -= 1;

        double len = Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= len;
        dirY /= len;

        isDashing = true;
        dashTime = DASH_DURATION;
        dashCooldownTimer = DASH_COOLDOWN;
        dashDirX = dirX;
        dashDirY = dirY;
    }

    /**
     * Shoots a bullet in the direction of input keys (Arrow keys).
     */
    public void attack() {
        long now = System.currentTimeMillis();
        if (now - lastShotTime < shootCooldown) return;
        lastShotTime = now;
        SFXManager.play("shoot");

        Bullet b = new Bullet(getX(), getY(), 0, 0, this.getPower(), CharacterType.PlayerType);
        if (Input.isHeld("LEFT")) {
            b.setVx(-BULLET_SPEED);
            b.setVy(0);
            lastDirX = -1;
            lastDirY = 0;
        } else if (Input.isHeld("RIGHT")) {
            b.setVx(BULLET_SPEED);
            b.setVy(0);
            lastDirX = 1;
            lastDirY = 0;
        } else if (Input.isHeld("UP")) {
            b.setVx(0);
            b.setVy(-BULLET_SPEED);
            lastDirX = 0;
            lastDirY = -1;
        } else if (Input.isHeld("DOWN")) {
            b.setVx(0);
            b.setVy(BULLET_SPEED);
            lastDirX = 0;
            lastDirY = 1;
        } else {
            return;
        }
        RoomManager.getInstance().getCurrentRoom().spawnBullet(b);
    }

    /** Triggered on hit
     * @param damage damage taken
     * */
    @Override
    public void onHit(int damage) {
        if (damageFlash <= 0) {
            setHp(getHp() - damage);
            damageFlash = 1.0;
            SFXManager.play("take_damage");
        }
        if (getHp() <= 0) {
            setAlive(false);
            onDead();
        }
    }

    /** Use special ability */
    @Override
    public void useSpecialAbility() {
        long now = System.currentTimeMillis();
        if (now - lastLaserTime < LASER_COOLDOWN) return;

        lastLaserTime = now;
        SFXManager.play("laser");
        Laser laser = new Laser(getX(), getY(), lastDirX, lastDirY, getPower());
        RoomManager.getInstance().getCurrentRoom().spawnLaser(laser);
    }

    /** Do nothing */
    @Override
    public void onDead() {
        // Player death handling
    }

    /** @return Player name */
    public String getName() { return name; }

    /** @param name Sets the player name */
    public void setName(String name) { this.name = name; }

    /** @return Current damage flash alpha */
    public double getDamageFlash() { return damageFlash; }

    /** @param damageFlash Set damage flash alpha (0-1) */
    public void setDamageFlash(double damageFlash) {
        this.damageFlash = Math.min(1.0, Math.max(0, damageFlash));
    }

    /** @return Player sprite image */
    public static Image getPlayerSprite() {
        if (PLAYER_SPRITE == null) PLAYER_SPRITE = new Image("sprites/player.png");
        return PLAYER_SPRITE;
    }

    /** @param coin Sets player's coins */
    public void setCoin(int coin) { this.coins = Math.max(coin, 0); }

    /** @return Player's coin count */
    public int getCoin() { return coins; }

    /** @param amount Adds coins to player */
    public void addCoin(int amount) { coins += amount; }

    /** @param amount Uses coins from player */
    public void useCoin(int amount) { coins -= amount; }

    /** @param amount Check if player has sufficient coins
     *  @return true if coins >= amount
     */
    public boolean sufficientAmount(int amount) { return coins >= amount; }

    /**
     * Updates player movement, dash, and attack state.
     *
     * @param dt Delta time in seconds
     */
    @Override
    public void update(double dt) {
        if (Input.isHeld("SPACE")) dash();

        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= dt;
            if (dashCooldownTimer < 0) dashCooldownTimer = 0;
        }

        if (isDashing) {
            x += dashDirX * DASH_SPEED * dt;
            y += dashDirY * DASH_SPEED * dt;
            x = Math.max(10, Math.min(790, x));
            y = Math.max(10, Math.min(590, y));
            dashTime -= dt;
            if (dashTime <= 0) isDashing = false;
            return;
        }

        double dx = 0, dy = 0;
        if (Input.isHeld("W")) dy -= SPEED * dt;
        if (Input.isHeld("S")) dy += SPEED * dt;
        if (Input.isHeld("A")) dx -= SPEED * dt;
        if (Input.isHeld("D")) dx += SPEED * dt;

        x += dx;
        y += dy;

        if (Input.isHeld("LEFT") || Input.isHeld("RIGHT") || Input.isHeld("UP") || Input.isHeld("DOWN")) attack();
        x = Math.max(10, Math.min(790, x));
        y = Math.max(10, Math.min(590, y));
        if (Input.isHeld("E")) useSpecialAbility();

        if (damageFlash > 0) {
            damageFlash -= dt * 2;
            if (damageFlash < 0) damageFlash = 0;
        }
    }

    /**
     * Renders the player sprite.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getPlayerSprite(), getX() - diameter, getY() - diameter, diameter * 2, diameter * 2);
    }
}
