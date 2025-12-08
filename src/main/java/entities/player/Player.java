package entities.player;

import core.Character;
import core.Hittable;
import core.SpecialAbilityCapable;
import entities.bullets.Bullet;
import entities.enemies.Enemy;
import game.GameApp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import util.Input;

public class Player extends Character implements Hittable, SpecialAbilityCapable {
    private int maxHp;
    private int cooldown;
    private boolean isUsedSpecialAbility;
    private String name;
    private static final double SPEED = 250;
    public static final int VX=200;
    public static final int VY=200;
    private int coins;
    private Rectangle2D hitbox;

    private double damageFlash;
    public Player(double x, double y, int hp, int power, int cooldown, String name,double diameter) {
        super(x,y,hp,power,diameter);
        setMaxHp(hp);
        setCoolDown(cooldown);
        setName(name);
        setCoin(0);
        damageFlash = 0;
    }

    public void attack(Enemy enemy){

    }

    @Override
    public void onHit(int damage) {
        this.setHp(getHp()-damage);
        damageFlash = 1.0;
    }

    @Override
    public void useSpecialAbility() {
        return;
    }


    @Override
    public void onDead(){
        return; // Death screen?
    }

    public int getMaxHp() { return maxHp; }

    public void setMaxHp(int maxHp) {
        if (maxHp < 0) this.maxHp = 0;
        else this.maxHp = maxHp;
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
        double dx = 0, dy = 0;

        if (Input.isHeld("W")) dy -= SPEED * dt;
        if (Input.isHeld("S")) dy += SPEED * dt;
        if (Input.isHeld("A")) dx -= SPEED * dt;
        if (Input.isHeld("D")) dx += SPEED * dt;

        x += dx;
        y += dy;

        // Keep inside bounds
        x = Math.max(10, Math.min(790, x));
        y = Math.max(10, Math.min(590, y));

        if (damageFlash > 0){
            damageFlash -= dt * 2;
            if (damageFlash < 0) damageFlash = 0;
        }
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillOval(x - (diameter/2), y - (diameter/2), 20, 20);
    }

}