package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import rooms.Room;

public abstract class Item implements Collectible {
    private double x,y;
    private double width, height;
    private boolean collected;
    private Room room;

    public Item(double x, double y, double width, double height) {
        setX(x);
        setY(y);
        setWidth(width);
        setHeight(height);
        setCollected(false);
    }

    public void update(double dt, Player player) {
        if (player.getHitbox().intersects(this.getHitbox()) && !isCollected()){
            collect(player);
            setCollected(true);
        }
    }

    @Override
    public Rectangle2D getHitbox(){
        return new Rectangle2D(x - (width/2), y - (height/2), width, height);
    }

    public abstract void render(GraphicsContext gc);

    public double getX() {
        return x;
    }

    public void setX(double x) { this.x = x; }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getWidth() { return width; }

    public void setWidth(double width){ this.width = width; }

    public double getHeight() { return height; }

    public void setHeight(double height) { this.height = height; }

    public boolean isCollected(){ return collected; }

    public void setCollected(boolean collected) { this.collected = collected; }

    public abstract void collect(Player player);

}
