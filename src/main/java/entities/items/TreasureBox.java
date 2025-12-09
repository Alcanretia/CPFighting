package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class TreasureBox extends Item{
    private static final double WIDTH = 30, HEIGHT = 30;
    private static final int COINAMOUNT = 20;

    public TreasureBox(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.addCoin(COINAMOUNT);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.GOLD);
        gc.fillRect(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
