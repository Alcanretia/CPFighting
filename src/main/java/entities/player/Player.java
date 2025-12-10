package entities.player;

import core.Character;
import core.Hittable;
import core.SpecialAbilityCapable;
import entities.bullets.Bullet;
import entities.bullets.Laser;
import game.RoomManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;
import util.CharacterType;
import util.Input;
import util.SFXManager;

public class Player extends Character implements Hittable, SpecialAbilityCapable {
    private int cooldown;
    private boolean isUsedSpecialAbility;
    private String name;
    private static final double SPEED = 250;
    private int coins;
    private static final double BULLET_SPEED = 400;

    private long lastShotTime=0;
    private static final double shootCooldown=200;

    ///  LASER
    private double lastDirX = 1; // default right
    private double lastDirY = 0;
    private long lastLaserTime = 0;
    private static final long LASER_COOLDOWN = 3000;


    /// Dash system
    private boolean isDashing = false;
    private double dashTime = 0;                        // How long current dash lasts
    private static final double DASH_DURATION = 0.15;   // seconds
    private static final double DASH_SPEED = 650;       // dash movement speed
    private static final double DASH_COOLDOWN = 1.0;    // seconds
    private double dashCooldownTimer = 0;               // cooldown tracker
    private double dashDirX = 0;                        // dash direction
    private double dashDirY = 0;

    private static Image PLAYER_SPRITE;

    private double damageFlash;

    public Player(double x, double y, int hp, int power, int cooldown, String name,double diameter) {
        super(x,y,hp,power,diameter);
        setCoolDown(cooldown);
        setName(name);
        setCoin(0);
        damageFlash = 0;
    }
    public void dash() {
        // If already dashing or still cooling down, cannot dash
        if (isDashing || dashCooldownTimer > 0) return;

        // Get direction based on movement keys (WASD)
        double dirX = 0;
        double dirY = 0;

        if (Input.isHeld("W")) dirY -= 1;
        if (Input.isHeld("S")) dirY += 1;
        if (Input.isHeld("A")) dirX -= 1;
        if (Input.isHeld("D")) dirX += 1;

        // No direction = no dash
        if (dirX == 0 && dirY == 0) dirY -= 1;

        // Normalize direction vector
        double len = Math.sqrt(dirX * dirX + dirY * dirY);
        dirX /= len;
        dirY /= len;

        isDashing = true;
        dashTime = DASH_DURATION;
        dashCooldownTimer = DASH_COOLDOWN;

        dashDirX = dirX;
        dashDirY = dirY;
    }

    public void attack(){
        long now = System.currentTimeMillis();
        if (now - lastShotTime < shootCooldown) {
            return; // still cooling down
        }
        lastShotTime = now;
        SFXManager.play("shoot");

        Bullet b= new Bullet(getX(),getY(),0,0, this.getPower(),CharacterType.PlayerType);
        if (Input.isHeld("LEFT")) {
            b.setVy(0);
            b.setVx(-1*BULLET_SPEED);
            lastDirX = -1;
            lastDirY = 0;
        }
        else if (Input.isHeld("RIGHT")) {
            b.setVx(BULLET_SPEED);
            b.setVy(0);
            lastDirX = 1;
            lastDirY = 0;
        }
        else if (Input.isHeld("UP")) {
            b.setVx(0);
            b.setVy(-1*BULLET_SPEED);
            lastDirX = 0;
            lastDirY = -1;
        }
        else if (Input.isHeld("DOWN")) {
            b.setVx(0);
            b.setVy(BULLET_SPEED);
            lastDirX = 0;
            lastDirY = 1;

        }else {
            return;
        }
        RoomManager.getInstance().getCurrentRoom().spawnBullet(b);
    }

    @Override
    public void onHit(int damage) {
        if (damageFlash <= 0) {
            this.setHp(getHp() - damage);
            damageFlash = 1.0;
            SFXManager.play("take_damage");
        }
        if (getHp() <= 0){
            setAlive(false);
            onDead();
        }
    }

    @Override
    public void useSpecialAbility() {
        long now = System.currentTimeMillis();
        if (now - lastLaserTime < LASER_COOLDOWN) return;

        lastLaserTime = now;
        SFXManager.play("laser");
        // Create a laser with very high speed or infinite range
        Laser laser = new Laser(getX(), getY(), lastDirX, lastDirY, getPower());
        RoomManager.getInstance().getCurrentRoom().spawnLaser(laser);
    }


    @Override
    public void onDead(){
        return;
    }


    public int getCoolDown() {
        return cooldown;
    }

    public void setCoolDown(int cooldown) {
        this.cooldown = Math.max(cooldown, 1);
    }

    public boolean getUseSpecialAbility() {
        return isUsedSpecialAbility;
    }

    public void setUsedSpecialAbility(Boolean usedSpecialAbility) {
        isUsedSpecialAbility = usedSpecialAbility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDamageFlash(){
        return damageFlash;
    }
    public void setDamageFlash(double damageFlash){
        if (damageFlash > 1.0){
            this.damageFlash = 1;
        }
        else if (damageFlash < 0) {
            this.damageFlash = 0;
        }
        else this.damageFlash = damageFlash;
    }

    public static Image getPlayerSprite() {
        if (PLAYER_SPRITE == null) {
            PLAYER_SPRITE = new Image("sprites/player.png");
        }
        return PLAYER_SPRITE;
    }

    ///  For Collectible ///
    public void setCoin(int coin){
        this.coins= Math.max(coin, 0);
    }
    public int getCoin() { return coins; }

    public void addCoin(int amount){
        coins += amount;
    }
    public void useCoin(int amount){
        coins -= amount;
    }
    public boolean sufficientAmount(int amount){
        return coins >= amount;
    }



    /// Added for movement testing ///
    public void update(double dt) {
        if (Input.isHeld("SPACE")) dash();
        // Reduce cooldown
        if (dashCooldownTimer > 0) {
            dashCooldownTimer -= dt;
            if (dashCooldownTimer < 0) dashCooldownTimer = 0;
        }

        // === HANDLE DASHING FIRST ===
        if (isDashing) {
            // Move fast in dash direction
            x += dashDirX * DASH_SPEED * dt;
            y += dashDirY * DASH_SPEED * dt;
            x = Math.max(10, Math.min(790, x));
            y = Math.max(10, Math.min(590, y));
            dashTime -= dt;
            if (dashTime <= 0) {
                isDashing = false;
            }

            return; // skip normal movement while dashing
        }

        double dx = 0, dy = 0;

        if (Input.isHeld("W")) dy -= SPEED * dt;
        if (Input.isHeld("S")) dy += SPEED * dt;
        if (Input.isHeld("A")) dx -= SPEED * dt;
        if (Input.isHeld("D")) dx += SPEED * dt;

        x += dx;
        y += dy;
        if(Input.isHeld("LEFT")||Input.isHeld("RIGHT")||Input.isHeld("UP")||Input.isHeld("DOWN")) attack();
        // Keep inside bounds
        x = Math.max(10, Math.min(790, x));
        y = Math.max(10, Math.min(590, y));
        if (Input.isHeld("E")) useSpecialAbility();

        if (damageFlash > 0){
            damageFlash -= dt * 2;
            if (damageFlash < 0) damageFlash = 0;
        }
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(getPlayerSprite(),getX() - (diameter), getY() - (diameter), diameter*2, diameter*2);
        // Draw bigger than actual hitbox
    }

}