package rooms;

import entities.bullets.Bullet;
import core.Collectible;
import entities.enemies.Enemy;
import entities.items.Item;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.*;

public abstract class Room {
    public boolean active = false;

    private Player player;

    protected double x, y, width, height;

    protected Door topDoor;
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Room(double x, double y, double width, double height, Player player) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }
    public void spawnBullet(Bullet b) {
        bullets.add(b);
    }
    public void spawnItems(Item i) { items.add(i); }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Bullet> getBullets() { return bullets; }
    public List<Item> getItems() { return items; }

    // Add a door for this room
    public void addDoor(Door door) {
        topDoor = door;
    }

    // Check if the player intersects any door
    public Door getDoorAtPlayer(Rectangle2D playerHitbox) {
        return topDoor.intersects(playerHitbox) ? topDoor : null;
    }

    public void updateEntities(double dt){
        for (Enemy e : enemies) {
            if (!e.isDead()) e.update(dt);
        }
        for (Bullet b : bullets) {
            b.update(dt);
        }
        for (Collectible i : items){
            i.update(dt, player);
        }

        enemies.removeIf(Enemy::isDead);
        bullets.removeIf(b-> !b.isAlive());
        items.removeIf(Item::isCollected);
    }

    public void renderEntities(GraphicsContext gc){
        for (Bullet b : bullets) b.render(gc);
        for (Enemy e : enemies) e.render(gc);
        for (Collectible i : items) i.render(gc);
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    public void render(GraphicsContext gc) {
        // Base room render (background)
        gc.setFill(javafx.scene.paint.Color.rgb(40, 40, 40));
        gc.fillRect(x, y, width, height);

        // Render doors
        topDoor.render(gc);
    }

    public abstract void update(double dt);
}
