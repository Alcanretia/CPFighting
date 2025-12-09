package core;

import entities.player.Player;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

public interface Collectible {
    void collect(Player player);
    void update(double dt, Player player);
    void render(GraphicsContext gc);
    Rectangle2D getHitbox();
}
