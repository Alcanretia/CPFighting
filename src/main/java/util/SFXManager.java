package util;


import javafx.scene.media.AudioClip;
import java.util.HashMap;
import java.util.Map;

public class SFXManager {

    private static final Map<String, AudioClip> sounds = new HashMap<>();
    private static double globalVolume = 0.5; // default volume 50%

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

    public static void play(String name) {
        AudioClip clip = sounds.get(name);
        if (clip != null) {
            clip.play();
        } else {
            System.err.println("Sound not found: " + name);
        }
    }

    public static void setVolume(double volume) {
        globalVolume = Math.max(0, Math.min(1, volume));
        for (AudioClip clip : sounds.values()) {
            clip.setVolume(globalVolume);
        }
    }

    public static void stop(String name) {
        AudioClip clip = sounds.get(name);
        if (clip != null) clip.stop();
    }

    public static void stopAll() {
        for (AudioClip clip : sounds.values()) {
            clip.stop();
        }
    }
}
