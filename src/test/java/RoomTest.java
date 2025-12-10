package test;

import entities.enemies.Enemy;
import entities.items.Item;
import entities.items.Coin;
import entities.player.Player;
import entities.bullets.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.Room;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    private Player player;
    private TestRoom room;

    private static class TestRoom extends Room {
        public TestRoom(Player player) {
            super(0, 0, 800, 600, player);
        }
    }

    private static class TestEnemy extends Enemy {
        public TestEnemy(double x, double y, int hp, int power, Room room, double diameter) {
            super(x, y, hp, power, room, diameter);
        }

        @Override
        public void update(double dt) {
            // do nothing
        }

        @Override
        public void render(javafx.scene.canvas.GraphicsContext gc) {
            // do nothing
        }
    }

    private static class TestItem extends Item {
        private boolean collectedByPlayer = false;
        public TestItem(double x, double y) { super(x, y, 10, 10); }

        @Override
        public void collect(Player player) { collectedByPlayer = true; }

        @Override
        public void render(javafx.scene.canvas.GraphicsContext gc) { }

        public boolean wasCollectedByPlayer() { return collectedByPlayer; }
    }

    @BeforeEach
    void setUp() {
        player = new Player(100, 100, 100, 10, 1, "Hero", 16);
        room = new TestRoom(player);
    }

    @Test
    void testAddEnemyAndUpdate() {
        TestEnemy e1 = new TestEnemy(100, 100, 10, 5, room, 20);
        room.addEnemy(e1);
        assertEquals(1, room.getEnemies().size());

        room.updateEntities(0.016);
        assertTrue(room.getEnemies().contains(e1));
    }

    @Test
    void testEnemyDeathRemovesFromRoom() {
        TestEnemy e1 = new TestEnemy(100, 100, 10, 5, room, 20);
        room.addEnemy(e1);
        e1.onHit(50); // kill enemy
        room.updateEntities(0.016);
        assertFalse(room.getEnemies().contains(e1));
        assertFalse(room.getCharacters().contains(e1));
    }

    @Test
    void testItemCollection() {
        TestItem item = new TestItem(player.getX(), player.getY());
        room.spawnItems(item);
        room.updateEntities(0.016); // Add pending to real list
        room.updateEntities(0.016); // update list
        assertTrue(item.wasCollectedByPlayer());
        assertTrue(room.getItems().contains(item) == false); // removed after update
    }

    @Test
    void testSpawnBullet() {
        Bullet b = new Bullet(player.getX(), player.getY(), 0, 0, 10, null);
        room.spawnBullet(b);
        List<Bullet> bullets = room.getBullets();
        assertEquals(1, bullets.size());
        assertEquals(b, bullets.get(0));
    }
}
