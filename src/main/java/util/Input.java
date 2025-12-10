package util;

import javafx.scene.Scene;
import java.util.HashSet;

/**
 * Handles keyboard input for the game.
 * <p>
 * Tracks which keys are currently held, pressed this frame, or released this frame.
 */
public class Input {
    private static HashSet<String> keys = new HashSet<>();
    private static HashSet<String> pressed = new HashSet<>();
    private static HashSet<String> released = new HashSet<>();

    /** Initializes the input system (currently empty placeholder). */
    public static void initialize() {}

    /**
     * Attaches keyboard listeners to the given scene.
     * @param scene the JavaFX scene to attach key listeners to
     */
    public static void attach(Scene scene) {
        scene.setOnKeyPressed(e -> {
            String k = e.getCode().toString();
            if (!keys.contains(k)) pressed.add(k);
            keys.add(k);
        });

        scene.setOnKeyReleased(e -> {
            String k = e.getCode().toString();
            keys.remove(k);
            released.add(k);
        });
    }

    /** Updates the input system, clearing pressed and released keys. */
    public static void update() {
        pressed.clear();
        released.clear();
    }

    /**
     * Checks if the specified key is currently held down.
     * @param key the key name
     * @return true if the key is being held down
     */
    public static boolean isHeld(String key) { return keys.contains(key); }

    /**
     * Checks if the specified key was pressed this frame.
     * @param key the key name
     * @return true if the key was pressed this frame
     */
    public static boolean isPressed(String key) { return pressed.contains(key); }

    /**
     * Checks if the specified key was released this frame.
     * @param key the key name
     * @return true if the key was released this frame
     */
    public static boolean isReleased(String key) { return released.contains(key); }
}
