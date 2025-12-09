package entities.bullets;

import core.GameObject;
import game.GameApp;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import util.CharacterType;

public class Laser extends GameObject {
    private double dirX, dirY;
    private double length = 800;
    private int damage;
    private boolean alive = true;

    private double lifetime = 0.5; // seconds the laser stays visible

    public Laser(double x, double y, double dirX, double dirY, int damage) {
        super(x, y);
        this.dirX = dirX;
        this.dirY = dirY;
        this.damage = damage;
    }

    @Override
    public void update(double dt) {
        lifetime -= dt;
        if (lifetime <= 0) alive = false;
    }

    public boolean isAlive() { return alive; }

    @Override
    public void render(GraphicsContext gc) {
        gc.setStroke(Color.RED);
        gc.setLineWidth(20);

        double endX = getX() + dirX * length;
        double endY = getY() + dirY * length;

        gc.strokeLine(getX(), getY(), endX, endY);
    }

    public Rectangle2D getHitbox() {
        return new Rectangle2D(
                Math.min(getX(), getX() + dirX * length),
                Math.min(getY(), getY() + dirY * length),
                Math.abs(dirX * length),
                Math.abs(dirY * length)
        );
    }

    public int getDamage() { return damage; }
}

