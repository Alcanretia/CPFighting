package core;

import game.GameApp;
import javafx.scene.canvas.GraphicsContext;

public abstract class GameObject {
    protected double x,y;
    public GameObject(double x, double y){
        this.x = x;
        this.y = y;
    }
    public void setX(double x) {
        if (x > GameApp.WIDTH){
            this.x = 800;
        }
        else if (x < 0){
            this.x = 0;
        }
        else this.x = x;
    }

    public void setY(double y) {
        if (y > GameApp.HEIGHT){
            this.y = 600;
        }
        else if (y < 0){
            this.y = 0;
        }
        else this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }
    public abstract void update(double dt);
    public abstract void render(GraphicsContext gc);
}
