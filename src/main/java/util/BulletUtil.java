package util;

import core.Character;
import entities.bullets.Bullet;
import entities.enemies.Enemy;
import entities.player.Player;

import java.util.ArrayList;
import java.util.List;

public class BulletUtil {
    public static boolean shouldBulletStayAlive(Bullet b, List<Character> characters) {
        for (int i = 0; i < characters.size(); i++) {
            if (b.getHitBox().intersects(characters.get(i).getHitbox())) {
                if (b.getOwner() == CharacterType.PlayerType && characters.get(i) instanceof Enemy) {
                    return false;
                } else if (!(b.getOwner() == CharacterType.PlayerType) && characters.get(i) instanceof Player) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void filterAliveBullets(List<Bullet> b, List<Character> characters) {
        ArrayList<Bullet> current = new ArrayList<>();
        for (Bullet bullet : b) {
            if (shouldBulletStayAlive(bullet, characters)) {
                current.add(bullet);
            }
        }
        b.clear();
        b.addAll(current);
    }

}