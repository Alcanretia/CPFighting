package com.bullethell.core;

import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected double x,y;
    protected boolean alive = true;

    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }

    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);

    public double getX() { return x; }
    public double getY() { return y; }

    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }
}
