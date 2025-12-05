package com.bullethell.game;

import com.bullethell.entities.player.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.bullethell.util.Input;

import com.bullethell.rooms.Room;
import com.bullethell.rooms.CombatRoom;

public class GameApp extends Application {

    private Player player;
    public static final int WIDTH = 800, HEIGHT = 600;

    private RoomManager roomManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Input.initialize();

        // Create RoomManager with grid size (example: 3×3 = 9 rooms)
        roomManager = new RoomManager(3, 3);

        roomManager.createRooms();

        // --- Generate doors AFTER rooms are added ---
        roomManager.generateDoors();

        // --- Set starting room ---
        roomManager.setCurrentRoom(0);

        Player player = new Player(400, 300);
        Scene scene = new Scene(new StackPane(canvas));
        Input.attach(scene);

        stage.setScene(scene);
        stage.setTitle("JavaFX Bullet Hell - Gradle Starter");
        stage.show();

        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (last == 0) last = now;
                double dt = (now - last) / 1e9;
                last = now;

                Input.update();
                player.update(dt);
                player.render(gc);

                roomManager.update(dt, player);
                roomManager.render(gc, player);
            }
        }.start();
    }
}
