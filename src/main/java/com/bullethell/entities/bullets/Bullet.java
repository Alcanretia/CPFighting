package com.bullethell.entities.bullets;

import com.bullethell.core.GameObject;
import com.bullethell.entities.enemies.Enemy;
import com.bullethell.util.CharacterType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends GameObject {
    private double vx, vy;
    private double speed = 300;
    private CharacterType owner;
    private int power;

    public Bullet(double x, double y, double vx, double vy,CharacterType owner) {
        super(x, y);
        this.vx = vx;
        this.vy = vy;
        this.owner=owner;
    }
    public boolean collidesWith(Enemy e){
        double dx = Math.abs(this.x - e.getX());
        double dy = Math.abs(this.y - e.getY());
        double distanceSquared = dx * dx + dy * dy;

        double enemyRadius = e.getHitRadius();
        return distanceSquared <= enemyRadius * enemyRadius;
    }
    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.YELLOW);
        gc.fillOval(x - 3, y - 3, 6, 6);
    }

    public CharacterType getOwner() {
        return owner;
    }

    public void setOwner(CharacterType owner) {
        this.owner = owner;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }
}