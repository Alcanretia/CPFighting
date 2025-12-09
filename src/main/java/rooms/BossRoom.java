package rooms;

import entities.enemies.Enemy;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class BossRoom extends CombatRoom{

    public BossRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }

}
