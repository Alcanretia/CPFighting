package entities.player;

import core.Hittable;
import core.SpecialAbilityCapable;
import entities.enemies.Enemy;
import game.GameApp;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import util.Input;

public class Player implements Hittable, SpecialAbilityCapable {
    private int hp;
    private int maxHp;
    private int power;
    private int cooldown;
    private boolean isAlive;
    private boolean isUsedSpecialAbility;
    private String name;

    public double x, y;
    private double speed = 250;
    private static final double WIDTH = 20, HEIGHT = 20;

    private int coins;

    private double damageFlash;
    public Player(double x, double y, int hp, int power, int cooldown, String name) {
        this.x = x;
        this.y = y;
        setMaxHp(hp);
        setHp(hp);
        setPower(power);
        setAlive(true);
        setCoolDown(cooldown);
        setName(name);
        damageFlash = 0;
    }

    public int attack(Enemy enemy){
        return 1;
    }
    public void walk(){

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
    public boolean isAlive() {
        return isAlive;
    }

    @Override
    public double getHitRadius() {
        return 0;
    }

    @Override
    public void onDead(){
        return; // Death screen?
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        if (hp < 0 ) this.hp = 0;
        else if (hp > maxHp) this.hp = maxHp;
        else this.hp = hp;
    }

    public int getMaxHp() { return maxHp; }

    public void setMaxHp(int maxHp) {
        if (maxHp < 0) this.maxHp = 0;
        else this.maxHp = maxHp;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCoolDown() {
        return cooldown;
    }

    public void setCoolDown(int cooldown) {
        this.cooldown = cooldown;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
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

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        if (x > GameApp.WIDTH){
            this.x = 800;
        }
        else if (x < 0){
            this.x = 0;
        }
        else this.x = x;
    }

    public void setY(double y){
        if (y > GameApp.HEIGHT){
            this.y = 600;
        }
        else if (y < 0){
            this.y = 0;
        }
        else this.y = y;
    }

    public double getWidth(){
        return WIDTH;
    }
    public double getHeight(){
        return HEIGHT;
    }

    public double getDamageFlash(){
        return damageFlash;
    }

    ///  For Collectible ///
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

        if (Input.isHeld("W")) dy -= speed * dt;
        if (Input.isHeld("S")) dy += speed * dt;
        if (Input.isHeld("A")) dx -= speed * dt;
        if (Input.isHeld("D")) dx += speed * dt;

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
        gc.fillOval(x - 10, y - 10, 20, 20);
    }

    public Rectangle2D getHitbox() {
        return new Rectangle2D(x - 10, y - 10, WIDTH, HEIGHT);
    }
}