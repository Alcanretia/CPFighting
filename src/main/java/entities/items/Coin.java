package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SFXManager;

public class Coin extends Item {
    private static final double WIDTH = 32, HEIGHT = 32;
    private static Image COIN_SPRITE;

    public Coin(double x, double y){
        super(x,y,WIDTH, HEIGHT);
    }

    @Override
    public void collect(Player player){
        SFXManager.play("coin");
        player.addCoin(1);
    }

    public static Image getCoinSprite() {
        if (COIN_SPRITE == null) COIN_SPRITE = new Image("sprites/coin.png");
        return COIN_SPRITE;
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(getCoinSprite(),getX() - WIDTH / 2,getY() - HEIGHT / 2,WIDTH,HEIGHT);
    }
}
