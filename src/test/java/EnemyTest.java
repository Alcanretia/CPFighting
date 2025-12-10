import entities.enemies.Enemy;
import entities.items.Coin;
import entities.items.Item;
import entities.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.Room;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private Player player;
    private MockRoom room;
    private TestEnemy enemy;

    // Minimal concrete subclass of Enemy
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

    // Minimal mock Room to test coin spawning
    private static class MockRoom extends Room {
        public Coin spawnedCoin = null;

        public MockRoom(Player player) {
            super(0,0,800,600,player);
        }

        @Override
        public void spawnItems(Item coin) {
            spawnedCoin = (Coin) coin;
        }
    }

    @BeforeEach
    void setUp() {
        player = new Player(100,100,100,1,"Hero",16);
        room = new MockRoom(player);
        enemy = new TestEnemy(50, 50, 100, 10, room, 20);
        room.addEnemy(enemy);
    }

    @Test
    void testInitialState() {
        assertTrue(enemy.isAlive());
        assertEquals(0, enemy.getDamageFlash());
        assertEquals(100, enemy.getHp());
    }

    @Test
    void testOnHitReducesHpAndSetsDamageFlash() {
        enemy.onHit(30);
        assertEquals(70, enemy.getHp());
        assertEquals(1, enemy.getDamageFlash());
        assertTrue(enemy.isAlive());
    }

    @Test
    void testOnHitKillsEnemyAndSpawnsCoin() {
        enemy.onHit(150); // deal more than HP
        assertEquals(0, enemy.getHp());
        assertFalse(enemy.isAlive());

        enemy.onDead();
        assertNotNull(((MockRoom) enemy.getRoom()).spawnedCoin);
        assertEquals(enemy.getX(), ((MockRoom) enemy.getRoom()).spawnedCoin.getX());
        assertEquals(enemy.getY(), ((MockRoom) enemy.getRoom()).spawnedCoin.getY());
    }

    @Test
    void testDamageFlashSetter() {
        enemy.setDamageFlash(0.5);
        assertEquals(0.5, enemy.getDamageFlash());
    }

    @Test
    void testIsDead() {
        assertFalse(enemy.isDead());
        enemy.onHit(200);
        assertTrue(enemy.isDead());
    }
}
