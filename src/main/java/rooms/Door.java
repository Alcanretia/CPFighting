package rooms;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Door {
    private Rectangle2D region;
    private int destinationRoom;

    public Door(double x, double y, double w, double h, int dest) {
        region = new Rectangle2D(x, y, w, h);
        this.destinationRoom = dest;
    }

    public boolean intersects(Rectangle2D hitbox) {
        return region.intersects(hitbox);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.color(1, 1, 1, 0.25));
        gc.fillRect(region.getMinX(), region.getMinY(),
                    region.getWidth(), region.getHeight());
    }

    public int getDestination() {
        return destinationRoom;
    }
}