import entities.items.Item;
import entities.player.Player;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    private Player player;
    private TestItem item;

    // Minimal concrete subclass
    private static class TestItem extends Item {
        private boolean collectedByPlayer = false;

        public TestItem(double x, double y, double width, double height) {
            super(x, y, width, height);
        }

        @Override
        public void collect(Player player) {
            collectedByPlayer = true;
        }

        @Override
        public void render(javafx.scene.canvas.GraphicsContext gc) {
            // do nothing
        }

        public boolean wasCollectedByPlayer() {
            return collectedByPlayer;
        }
    }

    @BeforeEach
    void setUp() {
        player = new Player(100, 100, 100, 1, "Hero", 16);
        item = new TestItem(100, 100, 20, 20); // same position as player
    }

    @Test
    void testInitialState() {
        assertFalse(item.isCollected());
        assertFalse(item.wasCollectedByPlayer());
    }

    @Test
    void testCollisionAndCollection() {
        // Update should detect collision and collect
        item.update(0.016, player); // dt is irrelevant here
        assertTrue(item.isCollected());
        assertTrue(item.wasCollectedByPlayer());
    }

    @Test
    void testNoCollectionIfNoCollision() {
        item.setX(500);
        item.setY(500);
        item.update(0.016, player);
        assertFalse(item.isCollected());
        assertFalse(item.wasCollectedByPlayer());
    }

    @Test
    void testHitboxCalculation() {
        Rectangle2D hb = item.getHitbox();
        assertEquals(item.getX() - item.getWidth()/2, hb.getMinX());
        assertEquals(item.getY() - item.getHeight()/2, hb.getMinY());
        assertEquals(item.getWidth(), hb.getWidth());
        assertEquals(item.getHeight(), hb.getHeight());
    }
}
