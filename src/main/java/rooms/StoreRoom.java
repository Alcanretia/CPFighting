package rooms;

import entities.items.MysteryBox;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

public class StoreRoom extends Room{
    private static final int ITEMCOST = 10;

    public StoreRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
        spawnItems(new MysteryBox(200,300,this, ITEMCOST));
        spawnItems(new MysteryBox(400,300,this, ITEMCOST));
        spawnItems(new MysteryBox(600,300,this, ITEMCOST));
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
