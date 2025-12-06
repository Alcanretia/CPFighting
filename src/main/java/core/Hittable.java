package core;

public interface Hittable {
    void onHit(int damage);
    void onDead();
    boolean isAlive();
    double getHitRadius();
}
