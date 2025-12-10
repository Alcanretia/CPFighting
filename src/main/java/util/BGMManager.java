package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * A simple manager for playing background music (BGM) in a JavaFX application.
 * <p>
 * Supports playing, looping, and stopping a single BGM track at a time.
 */
public class BGMManager {

    private static MediaPlayer currentPlayer;
    private static final double VOLUME = 0.3; // default 30% volume

    /**
     * Plays a BGM track from the given resource path.
     * If another track is already playing, it stops it first.
     *
     * @param resourcePath the path to the music file relative to the classpath
     */
    public static void play(String resourcePath) {
        stop(); // stop previous music if any

        try {
            Media media = new Media(BGMManager.class.getResource(resourcePath).toString());
            currentPlayer = new MediaPlayer(media);
            currentPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
            currentPlayer.setVolume(VOLUME);
            currentPlayer.play();
        } catch (Exception e) {
            System.err.println("Failed to play BGM: " + resourcePath);
            e.printStackTrace();
        }
    }

    /**
     * Stops the currently playing BGM, if any.
     */
    public static void stop() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer = null;
        }
    }
}
