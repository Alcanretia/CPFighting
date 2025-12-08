package com.bullethell.entities.enemies;

import com.bullethell.entities.bullets.Bullet;
import com.bullethell.rooms.Room;
import com.bullethell.util.CharacterType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Jumper extends Enemy {

    private double targetX, targetY;
    private Room room;
    private volatile boolean jumping = false;
    private double jumpRadius = 150;   // distance enemy is allowed to jump

    public Jumper(double x, double y, Room room, int hp, int power) {
        super(x, y, hp, power);
        this.room = room;

        Thread behavior = new Thread(this::loopBehavior);
        behavior.setDaemon(true);
        behavior.start();
    }

    private void loopBehavior() {
        try {
            while (isAlive) {
                if (!room.isActive()){
                    Thread.sleep(50);
                    continue;
                }

                // Wait before next jump
                Thread.sleep(1000 + (int) (Math.random() * 600));

                pickRandomTarget();
                jumping = true;

                smoothMove(0.4);
                jumping = false;

                shoot4Directions();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pickRandomTarget() {

        double angle = Math.random() * 2 * Math.PI;
        double distance = Math.random() * jumpRadius;

        targetX = x + Math.cos(angle) * distance;
        targetY = y + Math.sin(angle) * distance;

        // Keep jumps inside room bounds
        targetX = Math.max(25, Math.min(room.getWidth() - 25, targetX));
        targetY = Math.max(25, Math.min(room.getHeight() - 25, targetY));
    }


    private void smoothMove(double durationSeconds) throws InterruptedException {
        double startX = x;
        double startY = y;

        double time = 0;
        double dt = 0.01; // ~100 FPS movement

        while (time < durationSeconds) {
            double t = time / durationSeconds;

            // Smooth interpolation (ease in-out)
            double smooth = (1 - Math.cos(t * Math.PI)) / 2;

            x = startX + (targetX - startX) * smooth;
            y = startY + (targetY - startY) * smooth;

            Thread.sleep((int)(dt * 1000));
            time += dt;
        }

        // Snap to final point
        x = targetX;
        y = targetY;
    }

    private void shoot4Directions() {
        room.spawnBullet(new Bullet(x, y, 300, 0, CharacterType.JumperType));    // Right
        room.spawnBullet(new Bullet(x, y, -300, 0,CharacterType.JumperType));   // Left
        room.spawnBullet(new Bullet(x, y, 0, 300,CharacterType.JumperType));    // Down
        room.spawnBullet(new Bullet(x, y, 0, -300,CharacterType.JumperType));   // Up
    }
    @Override
    public void onDead(){
        return;
    }

    @Override
    public void update(double dt) {
        // Nothing needed — thread updates x,y
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(jumping ? Color.ORANGE : Color.RED);
        gc.fillOval(x - 12, y - 12, 24, 24);
    }



}
