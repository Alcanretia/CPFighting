package entities.bullets;

import core.Character;
import core.GameObject;
import entities.enemies.Enemy;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import util.CharacterType;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Bullet extends GameObject {
    private double vx, vy;
    private CharacterType owner;
    private int power;
    private final static double DIAMETER = 16;
    private static final Image BULLET_SPRITE = new Image("sprites/Bullet.png");

    public Bullet(double x, double y, double vx, double vy,int power,CharacterType owner) {
        super(x, y);
        setVx(vx);
        setVy(vy);
        this.owner=owner;
        setPower(power);

    }
    @Override
    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(BULLET_SPRITE, getX() -(DIAMETER/2), getY() - (DIAMETER/2), DIAMETER, DIAMETER);
    }

    public Rectangle2D getHitBox(){
        return new Rectangle2D(getX()-(DIAMETER/2),getY()-(DIAMETER/2),DIAMETER,DIAMETER);
    }
    public CharacterType getOwner() {
        return owner;
    }

    public void setOwner(CharacterType owner) {
        this.owner = owner;
    }

    public double getVy() {
        return vy;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public double getVx() {
        return vx;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = Math.max(power, 0);
    }

    public boolean isAlive(){
        return true;
    }
}