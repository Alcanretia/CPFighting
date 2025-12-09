package entities.enemies;

import core.Character;
import core.GameObject;
import core.Hittable;
import entities.items.Coin;
import javafx.scene.canvas.GraphicsContext;
import rooms.Room;

public abstract class Enemy extends Character implements Hittable {
    protected Room room;
    private double damageFlash = 0;

    public Enemy(double x, double y, int hp, int power, Room room,double diameter){
        super(x, y,hp,power,diameter);
        setRoom(room);
    }

    public boolean isDead() {
        return !isAlive;
    }

    @Override
    public void onHit(int damage){
        damageFlash = 1;

        this.setHp(getHp()-damage);
        if (getHp() <= 0){
            setAlive(false);
        }
    }

    public double getDamageFlash() {
        return damageFlash;
    }

    public void setDamageFlash(double damageFlash){
        this.damageFlash = damageFlash;
    }

    @Override
    public boolean isAlive() {
        return isAlive;
    }


    @Override
    public void onDead(){
        room.spawnItems(new Coin(x,y));
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }


    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);

}
