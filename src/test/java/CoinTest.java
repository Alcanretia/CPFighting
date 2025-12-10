import entities.items.Coin;
import entities.player.Player;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CoinTest {
    /// COIN AND TREASURE CHEST BEHAVE THE SAME

    // Minimal player for testing (does not rely on JavaFX)
    class TestPlayer extends Player {
        public TestPlayer(double x, double y) {
            super(x, y, 10, 1, 1, "Test", 20);
        }
    }

    @Test
    void testConstructor() {
        Coin c = new Coin(100, 200);

        assertEquals(100, c.getX());
        assertEquals(200, c.getY());
        assertFalse(c.isCollected());
    }

    @Test
    void testCollect() {
        TestPlayer player = new TestPlayer(0, 0);
        Coin c = new Coin(50, 50);

        int before = player.getCoin();

        c.setX(0);
        c.setY(0); // Align hitboxes

        c.update(0.016, player); // this calls collect() AND sets collected
        assertEquals(before + 1, player.getCoin());
        assertTrue(c.isCollected());
    }

    @Test
    void testUpdateCollectsWhenClose() {
        TestPlayer player = new TestPlayer(100, 100);
        Coin c = new Coin(110, 110); // within 25px radius

        c.update(0.016, player);

        assertTrue(c.isCollected());
        assertEquals(1, player.getCoin());
    }

    @Test
    void testUpdateDoesNotCollectWhenFar() {
        TestPlayer player = new TestPlayer(0, 0);
        Coin c = new Coin(300, 300);

        c.update(0.016, player);

        assertFalse(c.isCollected());
        assertEquals(0, player.getCoin());
    }

    @Test
    void testSetXSetY() {
        Coin c = new Coin(0, 0);

        c.setX(123);
        c.setY(456);

        assertEquals(123, c.getX());
        assertEquals(456, c.getY());
    }

}
