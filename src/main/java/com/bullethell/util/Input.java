package com.bullethell.util;

import javafx.scene.Scene;
import java.util.HashSet;

public class Input {
    private static HashSet<String> keys = new HashSet<>();
    private static HashSet<String> pressed = new HashSet<>();
    private static HashSet<String> released = new HashSet<>();

    public static void initialize() {}

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

    public static void update() {
        pressed.clear();
        released.clear();
    }

    public static boolean isHeld(String key) { return keys.contains(key); }
    public static boolean isPressed(String key) { return pressed.contains(key); }
    public static boolean isReleased(String key) { return released.contains(key); }
}