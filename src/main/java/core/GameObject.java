package core;

import game.GameApp;
import javafx.scene.canvas.GraphicsContext;

/**
 * Abstract base class for all objects in the game world.
 * Each GameObject has a position (x, y) and must be able to update and render itself.
 */
public abstract class GameObject {
    /** X-coordinate of the object in the game world. */
    protected double x;

    /** Y-coordinate of the object in the game world. */
    protected double y;

    /**
     * Constructs a GameObject at the specified coordinates.
     * Coordinates are clamped within the game window bounds.
     *
     * @param x Initial X-coordinate.
     * @param y Initial Y-coordinate.
     */
    public GameObject(double x, double y){
        setX(x);
        setY(y);
    }

    /**
     * Sets the X-coordinate of the object.
     * Clamps the value between 0 and GameApp.WIDTH.
     *
     * @param x X-coordinate to set.
     */
    public void setX(double x) {
        if (x > GameApp.WIDTH){
            this.x = GameApp.WIDTH;
        } else if (x < 0){
            this.x = 0;
        } else {
            this.x = x;
        }
    }

    /**
     * Sets the Y-coordinate of the object.
     * Clamps the value between 0 and GameApp.HEIGHT.
     *
     * @param y Y-coordinate to set.
     */
    public void setY(double y) {
        if (y > GameApp.HEIGHT){
            this.y = GameApp.HEIGHT;
        } else if (y < 0){
            this.y = 0;
        } else {
            this.y = y;
        }
    }

    /**
     * Returns the X-coordinate of the object.
     *
     * @return Current X-coordinate.
     */
    public double getX() { return x; }

    /**
     * Returns the Y-coordinate of the object.
     *
     * @return Current Y-coordinate.
     */
    public double getY() { return y; }

    /**
     * Updates the state of the object.
     * This method is called every frame with the time delta.
     *
     * @param dt Time elapsed since the last update, in seconds.
     */
    public abstract void update(double dt);

    /**
     * Renders the object on the screen.
     *
     * @param gc GraphicsContext used for drawing.
     */
    public abstract void render(GraphicsContext gc);
}
