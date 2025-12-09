package core;

import javafx.geometry.Rectangle2D;

public interface Hittable {

    void onHit(int damage);
    void onDead();
    boolean isAlive();
    double getDiameter();
    Rectangle2D getHitbox();
}
