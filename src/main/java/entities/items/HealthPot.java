package entities.items;

import core.Collectible;
import entities.player.Player;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HealthPot extends Item {
    private static final double WIDTH = 32, HEIGHT = 32;
    private static final int HEALING = 5;
    private static final Image HEALTHPOT_SPRITE = new Image("sprites/HealthPot.png");
    public HealthPot(double x, double y){
        super(x,y,WIDTH,HEIGHT);
    }

    @Override
    public void collect(Player player){
        player.setHp(player.getHp() + HEALING);
    }

    @Override
    public void render(GraphicsContext gc){
        gc.drawImage(HEALTHPOT_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
