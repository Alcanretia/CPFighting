package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * A collectible treasure box item.
 * When collected, grants the player a fixed amount of coins.
 */
public class TreasureBox extends Item {

    /** Width of the treasure box sprite. */
    private static final double WIDTH = 32;

    /** Height of the treasure box sprite. */
    private static final double HEIGHT = 32;

    /** Amount of coins granted when collected. */
    private static final int COINAMOUNT = 20;

    /** Sprite image for the treasure box. */
    private static final Image TREASURE_SPRITE = new Image("sprites/treasure.png");

    /**
     * Constructs a new TreasureBox at the specified position.
     *
     * @param x X coordinate of the item center
     * @param y Y coordinate of the item center
     */
    public TreasureBox(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Collects the treasure box and grants coins to the player.
     *
     * @param player The player who collects the item
     */
    @Override
    public void collect(Player player) {
        player.addCoin(COINAMOUNT);
    }

    /**
     * Renders the treasure box at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(TREASURE_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());
    }
}
