package com.bullethell.game;

import com.bullethell.entities.bullets.Bullet;
import com.bullethell.entities.enemies.Enemy;
import com.bullethell.rooms.Room;
import com.bullethell.util.CharacterType;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;

public class BulletManager {

    private final ArrayList<Bullet> bullets = new ArrayList<>();
    public void add(Bullet b) {
        bullets.add(b);
    }

    public void update(double dt, Room room) {
        ArrayList<Bullet> toRemove = new ArrayList<>();

        for (Bullet b : bullets) {
            b.update(dt);

            // Check bullet hitting walls or exiting room
            if (!room.isInside(b)) {
                toRemove.add(b);
                continue;
            }

            if (b.getOwner() == CharacterType.PlayerType) {
                // Check hitting enemies
                for (Enemy e : room.getEnemies()) {
                    if (b.collidesWith(e)) {
                        e.onHit(1);
                        toRemove.add(b);
                        break;
                    }
                }
            } else {
                // Enemy bullet hitting player
                if (b.collidesWith(room.getPlayer())) {
                    room.getPlayer().onHit(1);
                    toRemove.add(b);
                }
            }
        }

        bullets.removeAll(toRemove);
    }

    public void render(GraphicsContext gc) {
        for (Bullet b : bullets) {
            b.render(gc);
        }
    }
    public void clear(){
        bullets.clear();
    }
    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
