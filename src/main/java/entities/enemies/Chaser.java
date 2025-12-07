package entities.enemies;

import bullets.Bullet;
import entities.items.Coin;
import entities.player.Player;
import rooms.Room;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Chaser extends Enemy {
    private double baseSpeed = 80;       // normal movement
    private double chargeSpeed = 180;    // speed when charging
    private double width = 20, height = 20;
    private double vx = 0, vy = 0;

    private Player player;

    private boolean deathBombed = false;

    public Chaser(double x, double y, Room room, Player player, int hp, int power) {
        super(x, y, hp, power,room); // width, height, hp

        this.player = player;
    }

    public boolean getDeathBombed(){
        return deathBombed;
    }

    public void setDeathBombed(boolean deathBombed){
        this.deathBombed = deathBombed;
    }

    public void onDead(){
        room.spawnBullet(new Bullet(x, y, 300, 0));    // Right
        room.spawnBullet(new Bullet(x, y, -300, 0));   // Left
        room.spawnBullet(new Bullet(x, y, 0, 300));    // Down
        room.spawnBullet(new Bullet(x, y, 0, -300));   // Up
        room.spawnItems(new Coin(x,y));
    }

    @Override
    public void update(double dt) {
        if (isDead()){
            if (!getDeathBombed()){
                onDead();
                setDeathBombed(true);
            }
            return;
        }

        double dx = player.getX() - x;
        double dy = player.getY() - y;

        // Decide axis to move along
        if (Math.abs(dx) > Math.abs(dy)) {
            // Move horizontally
            vx = Math.signum(dx);
            vy = 0;
        } else {
            // Move vertically
            vx = 0;
            vy = Math.signum(dy);
        }

        // If already aligned along the moving axis → charge
        double speed;
        if (vx != 0 && Math.abs(dy) < 50) {        // horizontally aligned
            speed = chargeSpeed;
        } else if (vy != 0 && Math.abs(dx) < 50) { // vertically aligned
            speed = chargeSpeed;
        } else {
            speed = baseSpeed;
        }


        x += vx * speed * dt;
        y += vy * speed * dt;
    }

    ///  Just in case
    public void setHeight(double height){
        if (height >= 20){
            this.height = height;
        }
        else this.height = 20;
    }

    public void setWidth(double width){
        if (width >= 20){
            this.width = width;
        }
        else this.width = 20;
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}
