package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A collectible health potion item.
 * When collected by the player, restores a fixed amount of HP.
 */
public class HealthPot extends Item {

    /** Width of the health potion sprite. */
    private static final double WIDTH = 32;

    /** Height of the health potion sprite. */
    private static final double HEIGHT = 32;

    /** Amount of HP restored when collected. */
    private static final int HEALING = 5;

    /** Sprite image for the health potion. */
    private static final Image HEALTHPOT_SPRITE = new Image("sprites/health_pot.png");

    /**
     * Constructs a new HealthPot at the specified position.
     *
     * @param x X coordinate of the health potion center
     * @param y Y coordinate of the health potion center
     */
    public HealthPot(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Collects the health potion and restores HP to the player.
     *
     * @param player The player who collects the health potion
     */
    @Override
    public void collect(Player player) {
        player.setHp(player.getHp() + HEALING);
    }

    /**
     * Renders the health potion at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(HEALTHPOT_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
