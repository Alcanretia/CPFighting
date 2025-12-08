import entities.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void testConstructorInitialization() {
        Player p = new Player(100, 200, 10, 5, 3, "Hero", 20);

        assertEquals(100, p.getX());
        assertEquals(200, p.getY());
        assertEquals(10, p.getHp());
        assertEquals(10, p.getMaxHp());
        assertEquals(5, p.getPower());
        assertEquals(3, p.getCoolDown());
        assertEquals("Hero", p.getName());
        assertEquals(0, p.getCoin());
    }

    @Test
    void testOnHitReducesHp() {
        Player p = new Player(0, 0, 20, 5, 5, "Test", 20);

        p.onHit(5);
        assertEquals(15, p.getHp());

        p.onHit(20); // lethal damage
        assertEquals(0, p.getHp()); // your class allows negative hp
        assertTrue(p.getDamageFlash() > 0);
    }

    @Test
    void testSetMaxHp() {
        Player p = new Player(0, 0, 30, 5, 5, "Test", 20);
        p.setMaxHp(-10);
        assertEquals(0, p.getMaxHp());
    }

    @Test
    void testCoolDownMinimum() {
        Player p = new Player(0, 0, 30, 5, 0, "Test", 20);
        p.setCoolDown(0);
        assertEquals(1, p.getCoolDown());
    }

    @Test
    void testCoinOperations() {
        Player p = new Player(0, 0, 10, 5, 3, "CoinMan", 20);

        p.addCoin(10);
        assertEquals(10, p.getCoin());

        p.useCoin(4);
        assertEquals(6, p.getCoin());

        assertTrue(p.sufficientAmount(5));
        assertFalse(p.sufficientAmount(10));

        p.setCoin(-100);
        assertEquals(0, p.getCoin());
    }

    @Test
    void testDamageFlashDecay() {
        Player p = new Player(0, 0, 10, 5, 3, "Test", 20);

        p.onHit(3);
        assertTrue(p.getDamageFlash() > 0);

        p.update(1.0); // dt = 1 second
        assertEquals(0, p.getDamageFlash());
    }

    @Test
    void testMovementClamping() {
        Player p = new Player(400, 300, 10, 5, 3, "Test", 20);

        // Force out-of-bounds manually
        p.setX(-100);
        p.setY(999);
        p.update(0); // movement disabled but clamp still works

        assertEquals(10, p.getX());
        assertEquals(590, p.getY());
    }
}
