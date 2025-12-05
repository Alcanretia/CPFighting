package entities;

import interfaces.Hitable;
import interfaces.SpecialAbilityCapable;

public class Player implements Hitable, SpecialAbilityCapable {
    private int hp;
    private int power;
    private int coolDown;
    private Boolean isAlive;
    private Boolean isUsedSpeacialAbility;
    private String name;
    public Player(int hp,int power,int coolDown,String name){
        setHp(hp);
        setPower(power);
        setCoolDown(coolDown);
        setAlive(true);
        setUsedSpeacialAbility(true);}
    public int attack(Enemy enemy){

    }
    public void walk(){

    }
    @Override
    public void onHit(int damage) {
        this.setHp(getHp()-damage);
    }

    @Override
    public void useSpecialAbility() {

    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    public Boolean getAlive() {
        return isAlive;
    }

    public void setAlive(Boolean alive) {
        isAlive = alive;
    }

    public Boolean getUseSpeacialAbility() {
        return isUseSpeacialAbility;
    }

    public void setUsedSpeacialAbility(Boolean usedSpeacialAbility) {
        isUsedSpeacialAbility = usedSpeacialAbility;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
