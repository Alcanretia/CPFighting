package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import rooms.Room;

public class MysteryBox extends Item{
    private static final double WIDTH = 30, HEIGHT = 30;
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
        if (player.getHitbox().intersects(this.getHitbox())){
            if (player.sufficientAmount(cost)){
                collect(player);
                setCollected(true);
            }
            else {
                // set player x,y outside of box
                player.setX(getX());
                player.setY(getY() + 50);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(Color.YELLOW);
        gc.fillRect(getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
