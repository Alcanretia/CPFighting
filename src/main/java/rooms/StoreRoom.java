package rooms;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class StoreRoom extends Room{
    public StoreRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
    }

    @Override
    public void update(double dt) {
        // normal rooms have no enemies or special updating
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        // you can add decorations here later
    }
}
