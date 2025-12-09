package rooms;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CombatRoom extends Room {

    public CombatRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }

    public void spawnEnemy(Enemy e) {
        addEnemy(e);
    }

    @Override
    public void update(double dt) {
        updateEntities(dt);
        if (getEnemies().isEmpty()){
            topDoor.setLocked(false);
        }
        else topDoor.setLocked(true);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);

        if (topDoor.isLocked()) {
            drawFog(gc, Color.RED, 0.50);
        }
        else {
            drawFog(gc, Color.BLACK, 0.70);
        }

        renderEntities(gc);
    }


}