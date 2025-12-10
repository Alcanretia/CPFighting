package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A collectible item that increases the player's maximum health.
 * When collected, it increases the player's max HP and restores HP to full.
 */
public class MaxHealthUpgrade extends Item {

    /** Width of the upgrade item sprite. */
    private static final double WIDTH = 32;

    /** Height of the upgrade item sprite. */
    private static final double HEIGHT = 32;

    /** Amount to increase the player's maximum health by. */
    private static final int MAXHEALTHUP = 5;

    /** Sprite image for the health upgrade. */
    private static final Image MAXHP_SPRITE = new Image("sprites/hp_up.png");

    /**
     * Constructs a new MaxHealthUpgrade at the specified position.
     *
     * @param x X coordinate of the item center
     * @param y Y coordinate of the item center
     */
    public MaxHealthUpgrade(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Collects the item, increasing the player's max HP and restoring current HP to full.
     *
     * @param player The player who collects the item
     */
    @Override
    public void collect(Player player) {
        player.setMaxHp(player.getMaxHp() + MAXHEALTHUP);
        player.setHp(player.getMaxHp());
    }

    /**
     * Renders the max health upgrade at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(MAXHP_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
