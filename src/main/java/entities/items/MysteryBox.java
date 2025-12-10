package entities.items;

import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import rooms.Room;

/**
 * A collectible mystery box item.
 * When collected, it randomly spawns another item in the room,
 * and can be purchased by the player for a cost (Sometimes cost 0 coin).
 */
public class MysteryBox extends Item {

    /** Width of the mystery box sprite. */
    private static final double WIDTH = 32;

    /** Height of the mystery box sprite. */
    private static final double HEIGHT = 32;

    /** Coin icon sprite for displaying cost. */
    private static final Image COIN_SPRITE = new Image("sprites/coin.png");

    /** Mystery box sprite. */
    private static final Image BOX_SPRITE = new Image("sprites/mystery_block.png");

    /** Cost of the mystery box in coins. */
    private int cost;

    /** The room this mystery box belongs to. */
    private Room room;

    /**
     * Constructs a new MysteryBox at the specified position, with a cost and associated room.
     *
     * @param x X coordinate of the box center
     * @param y Y coordinate of the box center
     * @param room Room in which the box is located
     * @param cost Coin cost to collect the box
     */
    public MysteryBox(double x, double y, Room room, int cost) {
        super(x, y, WIDTH, HEIGHT);
        this.room = room;
        this.cost = cost;
    }

    /**
     * Collects the mystery box, randomly spawning a new item in the room.
     *
     * @param player The player who collects the mystery box
     */
    @Override
    public void collect(Player player) {
        double chance = Math.random();
        if (chance > 0.8) room.spawnItems(new MaxHealthUpgrade(getX(), getY()));
        else if (chance > 0.6) room.spawnItems(new StrengthPot(getX(), getY()));
        else if (chance > 0.1) room.spawnItems(new HealthPot(getX(), getY()));
        else room.spawnItems(new TreasureBox(getX(), getY()));
    }

    /**
     * Updates the box state. If the player intersects and has sufficient coins,
     * collects the box and deducts coins from the player.
     *
     * @param dt Delta time since last update
     * @param player The player interacting with the box
     */
    @Override
    public void update(double dt, Player player) {
        if (player.getHitbox().intersects(this.getHitbox()) && !isCollected()) {
            if (player.sufficientAmount(cost)) {
                collect(player);
                player.useCoin(cost);
                setCollected(true);
            }
            // Adjust player position to avoid overlapping the box
            player.setX(getX());
            player.setY(getY() + 50);
        }
    }

    /**
     * Renders the mystery box and its cost (if any) at its current position.
     *
     * @param gc GraphicsContext used for rendering
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(BOX_SPRITE, getX() - (getWidth() / 2), getY() - (getHeight() / 2), getWidth(), getHeight());

        if (cost > 0) {
            double iconSize = 16;

            double textY = getY() + getHeight() / 2 + 25;
            double iconY = textY - iconSize + 2;

            gc.setImageSmoothing(false);
            Font pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 12);
            gc.setFont(pixelFont);

            String costStr = String.valueOf(cost);
            double textWidth = gc.getFont().getSize() * costStr.length() * 0.6;

            double totalWidth = iconSize + 4 + textWidth;
            double startX = getX() - totalWidth / 2;

            // Draw coin icon
            gc.drawImage(COIN_SPRITE, startX, iconY, iconSize, iconSize);

            // Draw cost text
            gc.setFill(Color.WHITE);
            gc.fillText(costStr, startX + iconSize + 4, textY);
        }
    }
}
