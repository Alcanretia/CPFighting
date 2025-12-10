package rooms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Door {
    private Rectangle2D region;
    private boolean locked;
    private static final Image LOCKEDDOOR_SPRITE = new Image("sprites/door_locked.png");
    private static final Image UNLOCKEDDOOR_SPRITE = new Image("sprites/Door-Unlocked.png");
    private static final double WIDTH = 90, HEIGHT = 30, IMAGE_HEIGHT = 60;
    private double x,y;
    public Door(double x, double y) {
        region = new Rectangle2D(x, y, WIDTH, HEIGHT);
        this.x = x;
        this.y = y;
        locked = false;
    }

    public boolean intersects(Rectangle2D hitbox) {
        return region.intersects(hitbox);
    }

    public void setLocked(boolean locked) {this.locked = locked; }
    public boolean isLocked() { return locked; }

    public void render(GraphicsContext gc) {
        if (isLocked()){
            gc.drawImage(LOCKEDDOOR_SPRITE, x, y, WIDTH, IMAGE_HEIGHT);
        }
        else {
            gc.drawImage(UNLOCKEDDOOR_SPRITE, x, y, WIDTH, IMAGE_HEIGHT);
        }
    }
}