package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;

public class Coin implements Collectible {
    private double x,y;
    private boolean collected;

    public Coin(double x, double y){
        this.x = x;
        this.y = y;
        collected = false;
    }

    public void collect(Player player){
        player.addCoin(1);
        this.collected = true;
    }

    @Override
    public boolean isCollected(){
        return collected;
    }

    public void update(double dt, Player player) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        if (dx*dx + dy*dy < 25 * 25) {
            collect(player);
        }
    }

    public void render(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillOval(x - 5, y - 5, 10, 10);
    }
}
