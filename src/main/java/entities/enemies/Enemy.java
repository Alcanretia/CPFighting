package entities.enemies;

import core.GameObject;
import core.Hittable;
import entities.items.Coin;
import javafx.scene.canvas.GraphicsContext;
import rooms.Room;

public abstract class Enemy extends GameObject implements Hittable {
    protected int hp;
    protected int power;
    protected boolean isAlive;
    protected double hitRadius; /// CHANGE IN CONSTRUCTOR LATER
    protected Room room;

    public Enemy(double x, double y, int hp, int power, Room room) {
        super(x, y);
        this.hp = hp;
        this.power = power;
        this.setAlive(true);
        this.room = room;
    }

    public boolean isDead() {
        return !isAlive;
    }

    @Override
    public void onHit(int damage){
        return; // Change
    }

    @Override
    public boolean isAlive(){
        return isAlive;
    }

    @Override
    public double getHitRadius(){
        return hitRadius;
    }

    @Override
    public void onDead(){
        room.spawnItems(new Coin(x,y));
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);
}
