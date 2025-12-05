package com.bullethell.entities.enemies;

import com.bullethell.core.GameObject;
import com.bullethell.core.Hittable;
import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy extends GameObject implements Hittable {
    protected int hp;
    protected int power;
    protected final double speed = 200; // movement speed if needed
    protected boolean isAlive;
    protected double hitRadius;

    public Enemy(double x, double y, int hp, int power) {
        super(x, y);
        this.hp = hp;
        this.power = power;
        this.setAlive(true);
    }

    public boolean isDead() {
        return !isAlive;
    }

    @Override
    public void onHit(int damage){
        return; // Change
    }

    @Override
    public boolean isAlive(){
        return isAlive;
    }

    @Override
    public double getHitRadius(){
        return hitRadius;
    }

    @Override
    public void onDead(){}

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);
}
