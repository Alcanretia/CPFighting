import entities.enemies.Enemy;
import entities.items.Coin;
import entities.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.Room;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private Enemy enemy;
    private MockRoom room;

    // Minimal concrete subclass
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

    // Minimal Room mock to track spawned items
    private static class MockRoom extends Room {
        public Coin spawnedCoin = null;

        public MockRoom(double x, double y, double width, double height, Player player) {
            super(x, y, width, height, player);
        }

        @Override
        public void spawnItems(Coin coin) {
            spawnedCoin = coin;
        }
    }

    @BeforeEach
    void setUp() {
        room = new MockRoom();
        enemy = new TestEnemy(50, 50, 100, 10, room, 20);
    }

    @Test
    void testInitialState() {
        assertTrue(enemy.isAlive());
        assertEquals(0, enemy.getDamageFlash());
    }

    @Test
    void testOnHitReducesHpAndSetsDamageFlash() {
        enemy.onHit(30);
        assertEquals(70, enemy.getHp());
        assertEquals(1, enemy.getDamageFlash());
        assertTrue(enemy.isAlive());
    }

    @Test
    void testOnHitKillsEnemy() {
        enemy.onHit(150);
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());
        // onDead should spawn a coin
        enemy.onDead();
        assertNotNull(((MockRoom) enemy.getRoom()).spawnedCoin);
        assertEquals(enemy.getX(), ((MockRoom) enemy.getRoom()).spawnedCoin.getX());
        assertEquals(enemy.getY(), ((MockRoom) enemy.getRoom()).spawnedCoin.getY());
    }

    @Test
    void testSetDamageFlash() {
        enemy.setDamageFlash(0.5);
        assertEquals(0.5, enemy.getDamageFlash());
    }
}
