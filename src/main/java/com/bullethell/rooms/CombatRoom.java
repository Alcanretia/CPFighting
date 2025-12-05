package com.bullethell.rooms;

import com.bullethell.entities.enemies.Enemy;
import com.bullethell.entities.enemies.Jumper;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

public class CombatRoom extends Room {
    private boolean doorsLocked = false;

    public CombatRoom(int id, double x, double y, double w, double h) {
        super(id, x, y, w, h);
    }

    public void spawnEnemy(Enemy e) {
        addEnemy(e);
        doorsLocked = true; // lock when enemies present
    }

    @Override
    public void update(double dt) {
        updateEntities(dt);
        doorsLocked = !getEnemies().isEmpty();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);

        // Tint room red if locked
        if (doorsLocked) {
            gc.setFill(javafx.scene.paint.Color.color(1, 0, 0, 0.2));
            gc.fillRect(x, y, width, height);
        }

        renderEntities(gc);
    }

    public boolean areDoorsLocked() {
        return doorsLocked;
    }
}