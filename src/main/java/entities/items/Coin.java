package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Collection;

public class Coin extends Item {
    private static final double WIDTH = 32, HEIGHT = 32;
    private static final Image COIN_SPRITE = new Image("sprites/Coin.png");

    public Coin(double x, double y){
        super(x,y,WIDTH, HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.addCoin(1);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(COIN_SPRITE,getX() - WIDTH / 2,getY() - HEIGHT / 2,WIDTH,HEIGHT);
    }
}
