package rooms;

import entities.items.Coin;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class TreasureRoom extends Room {

    public TreasureRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
        spawnItems(new Coin(400,300));
    }

    @Override
    public void update(double dt) {
        // normal rooms have no enemies or special updating
        updateEntities(dt);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        // you can add decorations here later
        renderEntities(gc);
    }
}
