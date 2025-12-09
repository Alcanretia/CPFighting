package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MaxHealthUpgrade extends Item{
    private static final double WIDTH = 30, HEIGHT = 30;
    private static final int MAXHEALTHUP = 5;

    public MaxHealthUpgrade(double x, double y){
        super(x,y,WIDTH, HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.setMaxHp(player.getMaxHp() + MAXHEALTHUP);
        player.setHp(player.getMaxHp());
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.DARKRED);
        gc.fillOval(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
