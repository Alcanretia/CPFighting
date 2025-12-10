package util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class BGMManager {

    private static MediaPlayer currentPlayer;
    private static double volume = 0.3; // default 30% volume

    public static void play(String resourcePath) {
        stop(); // stop previous music if any

        try {
            Media media = new Media(BGMManager.class.getResource(resourcePath).toString());
            currentPlayer = new MediaPlayer(media);
            currentPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
            currentPlayer.setVolume(volume);
            currentPlayer.play();
        } catch (Exception e) {
            System.err.println("Failed to play BGM: " + resourcePath);
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (currentPlayer != null) {
            currentPlayer.stop();
            currentPlayer = null;
        }
    }

    public static void pause() {
        if (currentPlayer != null) {
            currentPlayer.pause();
        }
    }

    public static void resume() {
        if (currentPlayer != null) {
            currentPlayer.play();
        }
    }

    public static void setVolume(double vol) {
        volume = Math.max(0, Math.min(1, vol));
        if (currentPlayer != null) {
            currentPlayer.setVolume(volume);
        }
    }
}