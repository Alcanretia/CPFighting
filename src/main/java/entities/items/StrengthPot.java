package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A collectible strength potion item.
 * When collected, it increases the player's attack power by a fixed amount.
 */
public class StrengthPot extends Item {

    /** Width of the strength potion sprite. */
    private static final double WIDTH = 32;

    /** Height of the strength potion sprite. */
    private static final double HEIGHT = 32;

    /** Amount of power restored when collected. */
    private static final int STRENGTH = 1;

    /** Sprite image for the strength potion. */
    private static final Image DMGUP_SPRITE = new Image("sprites/damage_up.png");

    /**
     * Constructs a new StrengthPot at the specified position.
     *
     * @param x X coordinate of the item center
     * @param y Y coordinate of the item center
     */
    public StrengthPot(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Collects the item and increases the player's attack power.
     *
     * @param player The player who collects the item
     */
    @Override
    public void collect(Player player) {
        player.setPower(player.getPower() + STRENGTH);
    }

    /**
     * Renders the strength potion at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(DMGUP_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
