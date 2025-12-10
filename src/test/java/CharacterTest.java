

import core.Character;
import core.GameObject;
import core.Hittable;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CharacterTest {

    private Character character;

    // Minimal concrete subclass for testing
    private static class TestCharacter extends Character {
        public TestCharacter(double x, double y, int hp, int power, double diameter) {
            super(x, y, hp, power, diameter);
        }

        @Override
        public void onHit(int damage) {
            // simple hit logic
            setHp(getHp() - damage);
            if (getHp() <= 0) setAlive(false);
        }

        @Override
        public void onDead() {

        }

        @Override
        public void update(double dt) {

        }

        @Override
        public void render(GraphicsContext gc) {

        }
    }

    @BeforeEach
    void setUp() {
        character = new TestCharacter(100, 100, 100, 10, 20);
    }

    @Test
    void testHpLogic() {
        assertEquals(100, character.getHp());
        character.setHp(150);
        assertEquals(100, character.getHp()); // cannot exceed maxHp
        character.setHp(-10);
        assertEquals(0, character.getHp()); // cannot be negative
    }

    @Test
    void testMaxHpLogic() {
        character.setMaxHp(200);
        assertEquals(200, character.getMaxHp());
        character.setMaxHp(-50);
        assertEquals(0, character.getMaxHp()); // cannot be negative
    }

    @Test
    void testPowerLogic() {
        assertEquals(10, character.getPower());
        character.setPower(50);
        assertEquals(50, character.getPower());
        character.setPower(-5);
        assertEquals(1, character.getPower()); // minimum 1
    }

    @Test
    void testAliveLogic() {
        assertTrue(character.isAlive());
        character.kill();
        assertFalse(character.isAlive());
        character.setAlive(true);
        assertTrue(character.isAlive());
    }

    @Test
    void testHitboxAndDiameter() {
        Rectangle2D hitbox = character.getHitbox();
        assertEquals(100 - 10, hitbox.getMinX());
        assertEquals(100 - 10, hitbox.getMinY());
        assertEquals(20, hitbox.getWidth());
        assertEquals(20, hitbox.getHeight());

        character.setDiameter(-5);
        assertEquals(0, character.getDiameter()); // cannot be negative
    }

    @Test
    void testOnHit() {
        character.onHit(30);
        assertEquals(70, character.getHp());
        assertTrue(character.isAlive());

        character.onHit(100);
        assertEquals(0, character.getHp());
        assertFalse(character.isAlive());
    }

    @Test
    void testIsDead() {
        assertFalse(character.isDead());
        character.kill();
        assertTrue(character.isDead());
    }
}

