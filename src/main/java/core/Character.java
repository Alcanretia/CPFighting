package core;

import javafx.geometry.Rectangle2D;

public abstract class Character extends GameObject implements Hittable {
    private int hp,power;
    protected boolean isAlive;
    protected double diameter;
    public Character(double x, double y,int hp, int power,double diameter) {
        super(x,y);
        setHp(hp);
        setPower(power);
        setAlive(true);
        setDiameter(diameter);
    }
    public boolean isDead(){
        return !isAlive;
    }
    public Rectangle2D getHitbox(){
        return new Rectangle2D(x-(diameter/2),y-(diameter/2),diameter,diameter);
    }
    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter<0?0:diameter;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(hp, 0);
    }
    public int getPower() {
        return power;
    }
    public void setPower(int power) {
        this.power = Math.max(power, 1);
    }
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
    public boolean isAlive() { return isAlive; }
    public void kill() { isAlive = false; }
}
