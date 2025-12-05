package com.bullethell.entities.player;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.geometry.Rectangle2D;
import com.bullethell.util.Input;

public class Player {
    public double x, y;
    private double speed = 250;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
    }

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