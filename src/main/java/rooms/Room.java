package rooms;

import core.Character;
import entities.bullets.Bullet;
import core.Collectible;
import entities.enemies.Enemy;
import entities.items.Item;
import entities.items.Item;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import util.BulletUtil;

import java.util.*;

public abstract class Room {
    private static final Image FLOOR_SPRITE = new Image("sprites/Floor.png");

    public boolean active = false;

    private Player player;
    protected double x, y, width, height;
    protected Door topDoor;

    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Item> pendingItems = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();

    public Room(double x, double y, double width, double height, Player player) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        characters.add(player);
    }

    public void addEnemy(Enemy e) {
        characters.add(e);
        enemies.add(e);
    }
    public void spawnBullet(Bullet b) {
        bullets.add(b);
    }
    public void spawnItems(Item i) { pendingItems.add(i); }

    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Bullet> getBullets() { return bullets; }
    public List<Item> getItems() { return items; }

    public List<Character> getCharacters() { return characters; }

    // Add a door for this room
    public void addDoor(Door door) {
        topDoor = door;
    }

    // Check if the player intersects any door
    public Door getDoorAtPlayer(Rectangle2D playerHitbox) {
        return topDoor.intersects(playerHitbox) ? topDoor : null;
    }

    public void updateEntities(double dt){
        for (int i = 0; i < enemies.size(); i++) {
            Enemy a = enemies.get(i);
            if (!a.isDead()) a.update(dt);
            if (!a.isDead() && a.getHitbox().intersects(player.getHitbox())){
                player.onHit(a.getPower());
            }
            for (int j = i + 1; j < enemies.size(); j++) {
                Enemy b = enemies.get(j);

                double dx = b.getX() - a.getX();
                double dy = b.getY() - a.getY();
                double dist = Math.sqrt(dx*dx + dy*dy);
                double minDist = a.getDiameter() / 1.5 + b.getDiameter() / 1.5;

                if (dist < minDist && dist > 0.0001) {
                    double overlap = minDist - dist;
                    dx /= dist;
                    dy /= dist;

                    // Push both enemies apart
                    a.setX(a.getX() - dx * overlap * 0.5);
                    a.setY(a.getY() - dy * overlap * 0.5);
                    b.setX(b.getX() + dx * overlap * 0.5);
                    b.setY(b.getY() + dy * overlap * 0.5);
                }
            }
        }
        for (Bullet b : bullets) {
            b.update(dt);
        }
        for (Collectible i : items){
            i.update(dt, player);
        }

        enemies.removeIf(Enemy::isDead);
        BulletUtil.filterAliveBullets(bullets,characters);
        characters.removeIf(ch -> (ch instanceof Enemy) && ch.isDead());
        bullets.removeIf(b-> !b.isAlive());
        items.removeIf(Item::isCollected);

        if (!pendingItems.isEmpty()) {
            items.addAll(pendingItems);
            pendingItems.clear();
        }
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

    protected void drawFog(GraphicsContext gc, Color edgeColor, double strength) {
        double fadeDistance = 120; // how far the smoke fades inward

        // 1. Edge red/black glow
        var gradient = new javafx.scene.paint.RadialGradient(
                0, 0,
                player.getX(), player.getY(),             // center of room
                Math.max(width, height),
                false,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0.0, Color.color(edgeColor.getRed(), edgeColor.getGreen(), edgeColor.getBlue(), 0)),
                new javafx.scene.paint.Stop(1.0, Color.color(edgeColor.getRed(), edgeColor.getGreen(), edgeColor.getBlue(), strength))
        );

        gc.setFill(gradient);
        gc.fillRect(x, y, width, height);
    }

    public void render(GraphicsContext gc) {
        // Base room render (background)
        gc.drawImage(FLOOR_SPRITE,0,0,800,600);
        drawFog(gc, Color.BLACK, 0.70);
        // Render doors
        topDoor.render(gc);
    }

    public abstract void update(double dt);
}
