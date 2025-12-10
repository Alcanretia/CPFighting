package rooms;

import entities.items.*;
import entities.player.Player;
import javafx.scene.canvas.GraphicsContext;

/**
 * Represents a treasure room where the player can find random items.
 * <p>
 * When instantiated, the room spawns items at fixed positions based on random chance:
 * <ul>
 *     <li>60% chance: a HealthPot and a StrengthPot</li>
 *     <li>30% chance: a TreasureBox</li>
 *     <li>20% chance: two MaxHealthUpgrades and a StrengthPot</li>
 *     <li>10% chance: three MysteryBoxes with cost 0</li>
 * </ul>
 */
public class TreasureRoom extends Room {

    /**
     * Constructs a TreasureRoom with specified position, size, and player reference.
     * Spawns items randomly according to predefined probabilities.
     *
     * @param x      the x-coordinate of the top-left corner of the room
     * @param y      the y-coordinate of the top-left corner of the room
     * @param w      the width of the room
     * @param h      the height of the room
     * @param player the player in this room
     */
    public TreasureRoom(double x, double y, double w, double h, Player player) {
        super(x, y, w, h, player);
        double chance = Math.random();
        if (chance > 0.6) {
            spawnItems(new HealthPot(300, 300));
            spawnItems(new StrengthPot(500, 300));
        } else if (chance > 0.3) {
            spawnItems(new TreasureBox(400, 300));
        } else if (chance > 0.1) {
            spawnItems(new MaxHealthUpgrade(300, 300));
            spawnItems(new MaxHealthUpgrade(500, 300));
            spawnItems(new StrengthPot(400, 200));
        } else {
            spawnItems(new MysteryBox(200, 300, this, 0));
            spawnItems(new MysteryBox(400, 300, this, 0));
            spawnItems(new MysteryBox(600, 300, this, 0));
        }
    }
}
