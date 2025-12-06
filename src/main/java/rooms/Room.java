package rooms;

import bullets.Bullet;
import entities.enemies.Enemy;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class Room {
    public boolean active = false;

    public enum Direction { UP, DOWN, LEFT, RIGHT }

    protected int id;
    protected double x, y, width, height;

    protected Map<Direction, Door> doors = new EnumMap<>(Direction.class);
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();

    public Room(int id, double x, double y, double width, double height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void addEnemy(Enemy e) {
        enemies.add(e);
    }

    public void spawnBullet(Bullet b) {
        bullets.add(b);
    }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Bullet> getBullets() { return bullets; }

    // Add a door for this room
    public void addDoor(Direction dir, Door door) {
        doors.put(dir, door);
    }

    // Check if the player intersects any door
    public Door getDoorAtPlayer(Rectangle2D playerHitbox) {
        for (Door d : doors.values()) {
            if (d.intersects(playerHitbox)) return d;
        }
        return null;
    }

    public void updateEntities(double dt){
        for (Enemy e : enemies) {
            if (!e.isDead()) e.update(dt);
        }
        for (Bullet b : bullets) {
            b.update(dt);
        }

        enemies.removeIf(Enemy::isDead);
        bullets.removeIf(b-> !b.isAlive());
    }

    public void renderEntities(GraphicsContext gc){
        for (Bullet b : bullets) b.render(gc);
        for (Enemy e : enemies) e.render(gc);
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
        for (Door d : doors.values()) {
            d.render(gc);
        }
    }

    public abstract void update(double dt);
    public int getId() { return id; }
}
