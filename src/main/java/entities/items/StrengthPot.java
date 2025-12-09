package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StrengthPot extends Item{
    private static final double WIDTH = 10, HEIGHT = 10;
    private static final int STRENGTH = 1;

    public StrengthPot(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.setPower(player.getPower() + STRENGTH);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.BROWN);
        gc.fillOval(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
