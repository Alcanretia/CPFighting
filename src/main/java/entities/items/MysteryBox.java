package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rooms.Room;

public class MysteryBox extends Item{
    private static final double WIDTH = 30, HEIGHT = 30;
    private static final Image COIN_SPRITE = new Image("sprites/Coin.png");
    private int cost;
    private Room room;

    public MysteryBox(double x, double y, Room room, int cost){
        super(x,y,WIDTH,HEIGHT);
        this.room = room;
        this.cost = cost;
    }

    @Override
    public void collect(Player player){
        double chance = Math.random();
        if (chance > 0.8) room.spawnItems(new MaxHealthUpgrade(getX(),getY()));
        else if (chance > 0.6) room.spawnItems(new StrengthPot(getX(),getY()));
        else if (chance > 0.1) room.spawnItems(new HealthPot(getX(),getY()));
        else room.spawnItems(new TreasureBox(getX(),getY()));
    }

    @Override
    public void update(double dt, Player player){
        if (player.getHitbox().intersects(this.getHitbox()) && !isCollected()){
            if (player.sufficientAmount(cost)){
                collect(player);
                setCollected(true);
            }
            // set player x,y outside of box
            player.setX(getX());
            player.setY(getY() + 50);
        }
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillRect(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
        if (cost > 0) {
            double iconSize = 16;

            double textY = getY() + getHeight() / 2 + 25;
            double iconY = textY - iconSize + 2;

            gc.setImageSmoothing(false);
            Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 12);
            gc.setFont(pixelFont);

            // Total width: coin + 4px + text
            String costStr = String.valueOf(cost);
            double textWidth = gc.getFont().getSize() * costStr.length() * 0.6;

            double totalWidth = iconSize + 4 + textWidth;
            double startX = getX() - totalWidth / 2;

            // draw coin
            gc.drawImage(COIN_SPRITE, startX, iconY, iconSize, iconSize);

            // draw cost text
            gc.setFill(Color.WHITE);
            gc.fillText(costStr, startX + iconSize + 4, textY);
        }
    }
}
