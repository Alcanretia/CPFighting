

import entities.bullets.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.CharacterType;
import javafx.geometry.Rectangle2D;

import static org.junit.jupiter.api.Assertions.*;

public class BulletTest {

    private Bullet bullet;

    @BeforeEach
    void setUp() {
        // Starting at (100,100), moving right 50 units/sec, power 10
        bullet = new Bullet(100, 100, 50, 0, 10, CharacterType.PlayerType);
    }

    @Test
    void testInitialValues() {
        assertEquals(100, bullet.getX());
        assertEquals(100, bullet.getY());
        assertEquals(50, bullet.getVx());
        assertEquals(0, bullet.getVy());
        assertEquals(10, bullet.getPower());
        assertEquals(CharacterType.PlayerType, bullet.getOwner());
    }

    @Test
    void testMovement() {
        bullet.update(1.0); // dt = 1 second
        assertEquals(150, bullet.getX(), 0.0001);
        assertEquals(100, bullet.getY(), 0.0001);

        bullet.setVy(20);
        bullet.update(0.5); // dt = 0.5 second
        assertEquals(175, bullet.getX(), 0.0001);
        assertEquals(110, bullet.getY(), 0.0001);
    }

    @Test
    void testPowerSetter() {
        bullet.setPower(5);
        assertEquals(5, bullet.getPower());

        bullet.setPower(-10);
        assertEquals(0, bullet.getPower()); // cannot go below 0
    }

    @Test
    void testHitBox() {
        Rectangle2D hb = bullet.getHitBox();
        assertEquals(bullet.getX() - 8, hb.getMinX(), 0.0001);
        assertEquals(bullet.getY() - 8, hb.getMinY(), 0.0001);
        assertEquals(16, hb.getWidth(), 0.0001);
        assertEquals(16, hb.getHeight(), 0.0001);
    }
}
