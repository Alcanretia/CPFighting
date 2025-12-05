package com.bullethell.entities.enemies;

import com.bullethell.core.GameObject;
import javafx.scene.canvas.GraphicsContext;

public abstract class Enemy extends GameObject {
    protected int hp = 3;
    protected double speed = 200; // movement speed if needed
    protected boolean alive = true;

    public Enemy(double x, double y, int hp) {
        super(x, y);
        this.hp = hp;
        this.alive = hp > 0;
    }

    public boolean isDead() {
        return !alive;
    }

    public void kill() {
        alive = false;
    }

    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);
}
