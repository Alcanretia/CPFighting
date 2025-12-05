package com.bullethell.entities.player;

import com.bullethell.core.Hittable;
import com.bullethell.core.SpecialAbilityCapable;
import com.bullethell.entities.enemies.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import com.bullethell.util.Input;

public class Player implements Hittable, SpecialAbilityCapable {
    private int hp;
    private int power;
    private int cooldown;
    private boolean isAlive;
    private boolean isUsedSpecialAbility;
    private String name;
    public double x, y;
    private double speed = 250;

    public Player(double x, double y, int hp, int power, int cooldown, String name) {
        this.x = x;
        this.y = y;
        setHp(hp);
        setPower(power);
        setAlive(true);
        setCoolDown(cooldown);
        setName(name);
    }

    public int attack(Enemy enemy){
        return 1;
    }
    public void walk(){

    }

    @Override
    public void onHit(int damage) {
        this.setHp(getHp()-damage);
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
        this.hp = hp;
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
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.CYAN);
        gc.fillOval(x - 10, y - 10, 20, 20);
    }

    public Rectangle2D getHitbox() {
        return new Rectangle2D(x - 10, y - 10, 20, 20);
    }
}