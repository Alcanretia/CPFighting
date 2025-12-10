package util;

/**
 * Enumeration representing the different types of characters in the game.
 * <p>
 * Used to differentiate behavior and interactions, such as bullet collisions.
 */
public enum CharacterType {
    /** Player character type */
    PlayerType,

    /** Chaser enemy type */
    ChaserType,

    /** Jumper enemy type */
    JumperType,

    /** Boss enemy type */
    BossType
}
