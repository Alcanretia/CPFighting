
import core.Character;
import entities.bullets.Bullet;
import entities.player.Player;
import entities.enemies.Enemy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rooms.Room;
import util.BulletUtil;
import util.CharacterType;

import javafx.geometry.Rectangle2D;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BulletUtilTest {

    private Player player;
    private TestEnemy enemy;
    private Bullet playerBullet;
    private Bullet enemyBullet;

    private static class TestEnemy extends Enemy {
        public TestEnemy(double x, double y, int hp, int power, RoomStub room, double diameter) {
            super(x, y, hp, power, null, diameter);
        }

        @Override
        public void update(double dt) {}
        @Override
        public void render(javafx.scene.canvas.GraphicsContext gc) {}
    }

    private static class RoomStub extends Room {
        public RoomStub() { super(0,0,800,600,null); }
        @Override public void update(double dt) {}
        @Override public void render(javafx.scene.canvas.GraphicsContext gc) {}
    }

    @BeforeEach
    void setUp() {
        player = new Player(100, 100, 100,  1, "Hero", 16);
        enemy = new TestEnemy(100, 100, 50, 5, new RoomStub(), 16);

        playerBullet = new Bullet(100, 100, 0, 0, 10, CharacterType.PlayerType);
        enemyBullet = new Bullet(100, 100, 0, 0, 5, CharacterType.ChaserType);
    }

    @Test
    void testPlayerBulletHitsEnemy() {
        List<Character> characters = new ArrayList<>();
        characters.add(enemy);

        boolean alive = BulletUtil.shouldBulletStayAlive(playerBullet, characters);

        assertFalse(alive);
        assertTrue(enemy.getHp() < 50);
    }

    @Test
    void testEnemyBulletHitsPlayer() {
        List<Character> characters = new ArrayList<>();
        characters.add(player);

        boolean alive = BulletUtil.shouldBulletStayAlive(enemyBullet, characters);

        assertFalse(alive);
        assertTrue(player.getHp() < 100);
    }

    @Test
    void testBulletStaysAliveIfNoHit() {
        List<Character> characters = new ArrayList<>();
        characters.add(enemy);

        Bullet b = new Bullet(200, 200, 0, 0, 10, CharacterType.PlayerType);

        boolean alive = BulletUtil.shouldBulletStayAlive(b, characters);

        assertTrue(alive);
    }

    @Test
    void testFilterAliveBulletsRemovesHitBullets() {
        List<Character> characters = new ArrayList<>();
        characters.add(enemy);

        List<Bullet> bullets = new ArrayList<>();
        bullets.add(playerBullet);
        bullets.add(new Bullet(200,200,0,0,10, CharacterType.PlayerType)); // should stay

        BulletUtil.filterAliveBullets(bullets, characters);

        assertEquals(1, bullets.size());
        assertEquals(200, bullets.get(0).getX(), 0.0001);
    }
}
