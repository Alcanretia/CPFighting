package rooms;

import javafx.scene.canvas.GraphicsContext;

public class NormalRoom extends Room {

    public NormalRoom(int id, double x, double y, double w, double h) {
        super(id, x, y, w, h);
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