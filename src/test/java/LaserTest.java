import entities.bullets.Laser;
import javafx.geometry.Rectangle2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LaserTest {

    private Laser laser;

    @BeforeEach
    void setUp() {
        // Position (100,100), pointing right (1,0), damage 10
        laser = new Laser(100, 100, 1, 0, 10);
    }

    @Test
    void testInitialState() {
        assertTrue(laser.isAlive());
        assertEquals(10, laser.getDamage());
        Rectangle2D hitbox = laser.getHitbox();
        assertEquals(100, hitbox.getMinX());
        assertEquals(100, hitbox.getMinY());
        assertEquals(800, hitbox.getWidth());
        assertEquals(0, hitbox.getHeight());
    }

    @Test
    void testLifetimeDecreasesAndDies() {
        // Update with small dt
        laser.update(0.1);
        assertTrue(laser.isAlive());

        // Update past lifetime
        laser.update(0.5);
        assertFalse(laser.isAlive());
    }

    @Test
    void testHitboxDirectionality() {
        // Up direction
        Laser upLaser = new Laser(50, 50, 0, -1, 5);
        Rectangle2D hb = upLaser.getHitbox();
        assertEquals(50, hb.getMinX());
        assertEquals(-750, hb.getMinY()); // 50 + (-1*800) = -750
        assertEquals(0, hb.getWidth());
        assertEquals(800, hb.getHeight());
    }
}
