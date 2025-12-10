package util;

import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages sound effects (SFX) in the game.
 * <p>
 * Allows loading, playing, stopping, and adjusting volume of sound effects.
 */
public class SFXManager {

    /** Stores loaded sound effects by name. */
    private static final Map<String, AudioClip> sounds = new HashMap<>();

    /** Global volume for all sound effects (0.0 to 1.0). */
    private static double globalVolume = 0.5;

    /**
     * Loads a sound effect from the given path and associates it with a name.
     * @param name name to reference the sound effect
     * @param path path to the sound resource
     */
    public static void load(String name, String path) {
        try {
            AudioClip clip = new AudioClip(SFXManager.class.getResource(path).toString());
            clip.setVolume(globalVolume);
            sounds.put(name, clip);
        } catch (Exception e) {
            System.err.println("Failed to load sound: " + path);
            e.printStackTrace();
        }
    }

    /**
     * Plays a loaded sound effect by name.
     * @param name the name of the sound effect
     */
    public static void play(String name) {
        AudioClip clip = sounds.get(name);
        if (clip != null) {
            clip.play();
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    /**
     * Sets the global volume for all sound effects.
     * @param volume volume between 0.0 (mute) and 1.0 (max)
     */
    public static void setVolume(double volume) {
        globalVolume = Math.max(0, Math.min(1, volume));
        for (AudioClip clip : sounds.values()) {
            clip.setVolume(globalVolume);
        }
    }

    /**
     * Stops a sound effect by name if it is playing.
     * @param name the name of the sound effect
     */
    public static void stop(String name) {
        AudioClip clip = sounds.get(name);
        if (clip != null) clip.stop();
    }

    /** Stops all currently playing sound effects. */
    public static void stopAll() {
        for (AudioClip clip : sounds.values()) {
            clip.stop();
        }
    }
}
