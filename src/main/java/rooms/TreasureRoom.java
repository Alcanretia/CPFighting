package rooms;

import entities.items.*;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class TreasureRoom extends Room {

    public TreasureRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
        double chance = Math.random();
        if (chance > 0.6) {
            spawnItems(new HealthPot(300,300));
            spawnItems(new StrengthPot(500,300));
        }
        else if (chance > 0.3) {
            spawnItems(new TreasureBox(400,300));
        }
        else if (chance > 0.1){
            spawnItems(new MaxHealthUpgrade(300,300));
            spawnItems(new MaxHealthUpgrade(500,300));
            spawnItems(new StrengthPot(400,200));
        }
        else spawnItems(new MysteryBox(400,300, this, 0));
    }

    @Override
    public void update(double dt) {
        updateEntities(dt);
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        renderEntities(gc);
    }
}
