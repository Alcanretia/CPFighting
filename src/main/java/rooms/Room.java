package rooms;

import core.Character;
import entities.bullets.Bullet;
import core.Collectible;
import entities.bullets.Laser;
import entities.enemies.Enemy;
import entities.items.Item;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import util.BulletUtil;

import java.util.*;

/**
 * Abstract base class for all types of rooms in the game.
 * <p>
 * Handles entities such as enemies, bullets, items, lasers, and the player.
 * Provides update and render methods for the room and its entities.
 */
public abstract class Room {

    /** Shared floor sprite for all rooms */
    private static Image FLOOR_SPRITE;

    /** Whether this room is currently active */
    public boolean active = false;

    private Player player;
    protected double x, y, width, height;
    protected Door topDoor;

    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<Item> pendingItems = new ArrayList<>();
    private List<Character> characters = new ArrayList<>();
    private List<Laser> lasers = new ArrayList<>();

    /**
     * Constructs a room with specified size and player reference.
     *
     * @param x top-left x-coordinate
     * @param y top-left y-coordinate
     * @param width width of the room
     * @param height height of the room
     * @param player the player object inside the room
     */
    public Room(double x, double y, double width, double height, Player player) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.player = player;
        characters.add(player);
    }

    /**
     * Adds an enemy to the room and to the characters list.
     *
     * @param e enemy to add
     */
    public void addEnemy(Enemy e) {
        characters.add(e);
        enemies.add(e);
    }

    /**
     * Spawns a bullet in the room.
     *
     * @param b bullet to spawn
     */
    public void spawnBullet(Bullet b) {
        bullets.add(b);
    }

    /**
     * Schedules an item to be added to the room.
     *
     * @param i item to spawn
     */
    public void spawnItems(Item i) {
        pendingItems.add(i);
    }

    /**
     * Spawns a laser in the room.
     *
     * @param l laser to spawn
     */
    public void spawnLaser(Laser l) {
        lasers.add(l);
    }

    /** @return width of the room */
    public double getWidth() { return width; }

    /** @return height of the room */
    public double getHeight() { return height; }

    /** @return list of enemies in the room */
    public List<Enemy> getEnemies() { return enemies; }

    /** @return list of bullets in the room */
    public List<Bullet> getBullets() { return bullets; }

    /** @return list of items in the room */
    public List<Item> getItems() { return items; }

    /** @return list of lasers in the room */
    public List<Laser> getLasers() { return lasers; }

    /** @return list of all characters (player + enemies) in the room */
    public List<Character> getCharacters() { return characters; }

    /**
     * @return shared floor sprite
     */
    public Image getFloorSprite(){
        if (FLOOR_SPRITE == null) FLOOR_SPRITE = new Image("sprites/floor.png");
        return FLOOR_SPRITE;
    }

    /**
     * Adds a door to the room.
     *
     * @param door the door to add
     */
    public void addDoor(Door door) {
        topDoor = door;
    }

    /**
     * Checks if the player intersects the door.
     *
     * @param playerHitbox the player's hitbox
     * @return the door if intersecting, otherwise null
     */
    public Door getDoorAtPlayer(Rectangle2D playerHitbox) {
        return topDoor.intersects(playerHitbox) ? topDoor : null;
    }

    /**
     * Updates all entities in the room: enemies, bullets, items, lasers.
     *
     * @param dt delta time in seconds
     */
    public void updateEntities(double dt) {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy a = enemies.get(i);
            if (!a.isDead()) a.update(dt);
            if (!a.isDead() && a.getHitbox().intersects(player.getHitbox())) {
                player.onHit(a.getPower());
            }

            // Enemy collision resolution
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

                    a.setX(a.getX() - dx * overlap * 0.5);
                    a.setY(a.getY() - dy * overlap * 0.5);
                    b.setX(b.getX() + dx * overlap * 0.5);
                    b.setY(b.getY() + dy * overlap * 0.5);
                }
            }
        }

        for (Bullet b : bullets) b.update(dt);
        for (Collectible i : items) i.update(dt, player);

        for (Laser l : lasers) {
            l.update(dt);
            for (Enemy e : enemies) {
                if (!e.isDead() && l.getHitbox().intersects(e.getHitbox())) {
                    e.onHit(l.getDamage());
                }
            }
        }

        // Remove dead enemies, bullets, collected items, and expired lasers
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            if (e.isDead()) {
                e.onDead();
                characters.remove(e);
                it.remove();
            }
        }

        BulletUtil.filterAliveBullets(bullets, characters);
        characters.removeIf(ch -> (ch instanceof Enemy) && ch.isDead());
        items.removeIf(Item::isCollected);
        lasers.removeIf(l -> !l.isAlive());

        if (!pendingItems.isEmpty()) {
            items.addAll(pendingItems);
            pendingItems.clear();
        }
    }

    /** Renders bullets, enemies, items, and lasers in the room */
    public void renderEntities(GraphicsContext gc) {
        for (Laser l : lasers) l.render(gc);
        for (Bullet b : bullets) b.render(gc);
        for (Enemy e : enemies) e.render(gc);
        for (Collectible i : items) i.render(gc);
    }

    public void setActive(boolean active) { this.active = active; }

    public boolean isActive() { return active; }

    /**
     * Draws fog/smoke effect over the room.
     *
     * @param gc GraphicsContext
     * @param edgeColor color of the outer edge
     * @param strength opacity of the fog
     */
    protected void drawFog(GraphicsContext gc, Color edgeColor, double strength) {
        double fadeDistance = 120;

        var gradient = new javafx.scene.paint.RadialGradient(
                0, 0,
                player.getX(), player.getY(),
                Math.max(width, height),
                false,
                javafx.scene.paint.CycleMethod.NO_CYCLE,
                new javafx.scene.paint.Stop(0.0, Color.color(edgeColor.getRed(), edgeColor.getGreen(), edgeColor.getBlue(), 0)),
                new javafx.scene.paint.Stop(1.0, Color.color(edgeColor.getRed(), edgeColor.getGreen(), edgeColor.getBlue(), strength))
        );

        gc.setFill(gradient);
        gc.fillRect(x, y, width, height);
    }

    /**
     * Base render method for the room.
     *
     * @param gc GraphicsContext
     */
    public void render(GraphicsContext gc) {
        gc.drawImage(getFloorSprite(), 0, 0, 800, 600);
        drawFog(gc, Color.BLACK, 0.70);
        renderEntities(gc);
        topDoor.render(gc);
    }

    /**
     * Updates the room and its entities.
     *
     * @param dt delta time in seconds
     */
    public void update(double dt) {
        updateEntities(dt);
    }
}
