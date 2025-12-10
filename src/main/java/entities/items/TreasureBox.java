package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class TreasureBox extends Item{
    private static final double WIDTH = 32, HEIGHT = 32;
    private static final int COINAMOUNT = 20;
    private static final Image TREASURE_SPRITE = new Image("sprites/treasure.png");

    public TreasureBox(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.addCoin(COINAMOUNT);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(TREASURE_SPRITE,getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
