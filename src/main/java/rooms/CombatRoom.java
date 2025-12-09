package rooms;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

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

        // Tint room red if locked
        if (topDoor.isLocked()) {
            gc.setFill(javafx.scene.paint.Color.color(1, 0, 0, 0.2));
            gc.fillRect(x, y, width, height);
        }

        renderEntities(gc);
    }

}