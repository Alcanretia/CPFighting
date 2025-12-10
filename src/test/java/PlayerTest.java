import entities.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(100, 100, 100, 10, 1, "Hero", 16);
    }

    @Test
    void testCoinLogic() {
        player.addCoin(50);
        assertEquals(50, player.getCoin());
        player.useCoin(20);
        assertEquals(30, player.getCoin());
        assertTrue(player.sufficientAmount(25));
        assertFalse(player.sufficientAmount(50));
    }

    @Test
    void testHpAndDeathLogic() {
        player.onHit(30);
        assertEquals(70, player.getHp());
        player.setDamageFlash(0); // reset manually for test
        player.onHit(100);
        assertFalse(player.isAlive());
        assertTrue(player.getHp() <= 0);
    }

    @Test
    void testCooldownLogic() {
        player.setCoolDown(5);
        assertEquals(5, player.getCoolDown());
        player.setCoolDown(-10);
        assertEquals(1, player.getCoolDown());
    }

    @Test
    void testDamageFlash() {
        player.onHit(10);
        assertTrue(player.getDamageFlash() > 0);
    }
}
