import entities.enemies.Enemy;
import entities.items.Coin;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;
import rooms.CombatRoom;
import rooms.Room;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    // --------------------------



    // --------------------------
    // Minimal Enemy subclass for testing
    // --------------------------



    @Test
    void testConstructor() {
        CombatRoom room = new CombatRoom(0,0,800,600,null);
        Enemy e = new Enemy(50, 100, 20, 5, room, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };

        assertEquals(50, e.getX());
        assertEquals(100, e.getY());
        assertEquals(20, e.getHp());
        assertEquals(5, e.getPower());
        assertEquals(room, e.getRoom());
        assertTrue(e.isAlive());
    }

    @Test
    void testOnHitReducesHp() {
        CombatRoom room = new CombatRoom(0,0,800,600,null);
        Enemy e = new Enemy(0, 0, 30, 5, room, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };

        e.onHit(10);
        assertEquals(20, e.getHp());

        e.onHit(25);
        assertEquals(0, e.getHp());
    }

    @Test
    void testIsDeadAndIsAlive() {
        CombatRoom room = new CombatRoom(0,0,800,600,null);
        Enemy e = new Enemy(0, 0, 10, 5, room, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };

        assertTrue(e.isAlive());
        assertFalse(e.isDead());

        // Kill enemy
        e.setHp(0);
        e.setAlive(false);

        assertFalse(e.isAlive());
        assertTrue(e.isDead());
    }

    @Test
    void testOnDeadDropsCoin() {
        CombatRoom room = new CombatRoom(0,0,800,600,null);
        Enemy e = new Enemy(100, 200, 10, 3, room, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };

        e.onDead();

        assertEquals(1, room.getItems().size());
        assertTrue(room.getItems().get(0) instanceof Coin);

        Coin c = (Coin) room.getItems().get(0);
        assertEquals(100, c.getX());
        assertEquals(200, c.getY());
    }

    @Test
    void testGetSetRoom() {
        CombatRoom room1 = new CombatRoom(0,0,800,600,null);
        CombatRoom room2 = new CombatRoom(0,0,800,800,null);

        Enemy e = new Enemy(0, 0, 10, 5, room1, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };
        assertEquals(room1, e.getRoom());

        e.setRoom(room2);
        assertEquals(room2, e.getRoom());
    }

    @Test
    void testUpdateAndRenderDoNotCrash() {
        CombatRoom room = new CombatRoom(0,0,800,600,null);
        Enemy e = new Enemy(0, 0, 10, 5, room, 20) {
            @Override
            public void update(double dt) {

            }

            @Override
            public void render(GraphicsContext gc) {

            }
        };

        assertDoesNotThrow(() -> e.update(0.016)); // 60 FPS dt
        assertDoesNotThrow(() -> e.render(null));  // passing null GC
    }
}
