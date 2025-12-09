package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;

public class Coin extends Item {
    private static final double WIDTH = 10, HEIGHT = 10;

    public Coin(double x, double y){
        super(x,y,WIDTH, HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.addCoin(1);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillOval(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
