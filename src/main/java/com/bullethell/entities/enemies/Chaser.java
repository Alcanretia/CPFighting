package com.bullethell.entities.enemies;

import com.bullethell.entities.bullets.Bullet;
import com.bullethell.entities.player.Player;
import com.bullethell.rooms.Room;
import com.bullethell.util.CharacterType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Chaser extends Enemy{
    private double baseSpeed = 80;       // normal movement
    private double chargeSpeed = 180;    // speed when charging
    protected double width, height;
    private double vx = 0, vy = 0;

    private Player player;
    private Room room;

    private boolean deathBombed = false;

    public Chaser(double x, double y, double width, double height, Room room, Player player) {
        super(x, y, 5, 1); // width, height, hp
        this.room = room;
        this.player = player;
        this.width = width;
        this.height = height;
    }

    public boolean getDeathBombed(){
        return deathBombed;
    }

    public void setDeathBombed(boolean deathBombed){
        this.deathBombed = deathBombed;
    }

    public void onDead(){
        room.spawnBullet(new Bullet(x, y, 300, 0, CharacterType.ChaserType));    // Right
        room.spawnBullet(new Bullet(x, y, -300, 0,CharacterType.ChaserType));   // Left
        room.spawnBullet(new Bullet(x, y, 0, 300,CharacterType.ChaserType));    // Down
        room.spawnBullet(new Bullet(x, y, 0, -300,CharacterType.ChaserType));   // Up
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
        if (vx != 0 && Math.abs(dy) < 15) {        // horizontally aligned
            speed = chargeSpeed;
        } else if (vy != 0 && Math.abs(dx) < 15) { // vertically aligned
            speed = chargeSpeed;
        } else {
            speed = baseSpeed;
        }


        x += vx * speed * dt;
        y += vy * speed * dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.ORANGE);
        gc.fillRect(x, y, width, height);
    }
}
