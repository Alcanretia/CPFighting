package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class StrengthPot extends Item{
    private static final double WIDTH = 32, HEIGHT = 32;
    private static final int STRENGTH = 1;
    private static final Image DMGUP_SPRITE = new Image("sprites/DamageUp.png");

    public StrengthPot(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.setPower(player.getPower() + STRENGTH);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(DMGUP_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
