package com.bullethell.core;

public interface Hittable {
    void hit(int damage);
    boolean isAlive();
    double getX();
    double getY();
    double getHitRadius();
}
