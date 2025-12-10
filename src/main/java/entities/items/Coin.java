package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import util.SFXManager;

/**
 * A collectible coin item.
 * When collected by the player, adds one coin to the player's inventory and plays a sound effect.
 */
public class Coin extends Item {

    /** Width of the coin sprite. */
    private static final double WIDTH = 32;

    /** Height of the coin sprite. */
    private static final double HEIGHT = 32;

    /** Coin sprite image. */
    private static Image COIN_SPRITE;

    /**
     * Constructs a new Coin at the specified position.
     *
     * @param x X coordinate of the coin center
     * @param y Y coordinate of the coin center
     */
    public Coin(double x, double y) {
        super(x, y, WIDTH, HEIGHT);
    }

    /**
     * Collects the coin, giving the player 1 coin and playing a sound effect.
     *
     * @param player The player who collects the coin
     */
    @Override
    public void collect(Player player) {
        SFXManager.play("coin");
        player.addCoin(1);
    }

    /**
     * Returns the coin sprite image. Loads it if it has not been loaded yet.
     *
     * @return Image representing the coin
     */
    public static Image getCoinSprite() {
        if (COIN_SPRITE == null) COIN_SPRITE = new Image("sprites/coin.png");
        return COIN_SPRITE;
    }

    /**
     * Renders the coin at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(getCoinSprite(), getX() - WIDTH / 2, getY() - HEIGHT / 2, WIDTH, HEIGHT);
    }
}
