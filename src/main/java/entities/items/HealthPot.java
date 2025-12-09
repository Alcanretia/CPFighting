package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class HealthPot extends Item {
    private static final double WIDTH = 10, HEIGHT = 10;
    private static final int HEALING = 5;
    public HealthPot(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.setHp(player.getHp() + HEALING);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.RED);
        gc.fillOval(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
