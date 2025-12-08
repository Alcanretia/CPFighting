package bullets;

import core.GameObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Bullet extends GameObject {
    private double vx, vy;
    private double speed = 300;
    private static final Image BULLET_SPRITE = new Image("sprites/Bullet.png");

    public Bullet(double x, double y, double vx, double vy) {
        super(x, y);
        this.vx = vx;
        this.vy = vy;
    }

    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(BULLET_SPRITE, x -10, y - 10, 20, 20);
    }
}