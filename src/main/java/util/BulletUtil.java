package util;

import core.Character;
import entities.bullets.Bullet;
import entities.enemies.Enemy;
import entities.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling bullet interactions in the game. Used in Room.
 * <p>
 * Provides methods to check collisions between bullets and characters
 * and filter out bullets that should no longer exist.
 */
public class BulletUtil {

    /**
     * Determines whether a bullet should continue existing or be removed
     * based on collisions with characters.
     *
     * @param b          the bullet to check
     * @param characters the list of characters to check collisions against
     * @return true if the bullet should stay alive, false if it hit a character
     */
    public static boolean shouldBulletStayAlive(Bullet b, List<Character> characters) {
        for (int i = 0; i < characters.size(); i++) {
            if (b.getHitBox().intersects(characters.get(i).getHitbox())) {
                if (b.getOwner() == CharacterType.PlayerType && characters.get(i) instanceof Enemy) {
                    characters.get(i).onHit(b.getPower());
                    return false;
                } else if ((b.getOwner() != CharacterType.PlayerType) && characters.get(i) instanceof Player) {
                    characters.get(i).onHit(b.getPower());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Filters the given list of bullets, removing bullets that have collided
     * with characters and should no longer exist.
     *
     * @param bullets    the list of bullets to filter
     * @param characters the list of characters to check collisions against
     */
    public static void filterAliveBullets(List<Bullet> bullets, List<Character> characters) {
        ArrayList<Bullet> current = new ArrayList<>();
        for (Bullet bullet : bullets) {
            if (shouldBulletStayAlive(bullet, characters)) {
                current.add(bullet);
            }
        }
        bullets.clear();
        bullets.addAll(current);
    }
}
