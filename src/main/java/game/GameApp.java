package game;

import entities.player.Player;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import util.Input;

public class GameApp extends Application {

    private Player player;
    public static final double WIDTH = 800, HEIGHT = 600;

    private RoomManager roomManager;

    public static void main(String[] args) {
        launch(args);
    }

    public Player getPlayer(){
        return player;
    }

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Input.initialize();

        this.player = new Player(400, 300, 10, 5, 5,"Player",30);

        // Create RoomManager with grid size (example: 3×3 = 9 rooms)
        roomManager = new RoomManager(player);

        Scene scene = new Scene(new StackPane(canvas));
        Input.attach(scene);

        stage.setScene(scene);
        stage.setTitle("JavaFX Bullet Hell - Infinite Rooms");
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

                drawHUD(gc);
            }
        }.start();
    }

    private Font pixelFont; // load once

    private void drawHUD(GraphicsContext gc) {

        gc.setImageSmoothing(false);

        // ====== Load font once ======
        if (pixelFont == null) {
            pixelFont = Font.loadFont(
                    getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 12
            );
        }

        // ====== HP BAR SETTINGS ======
        double barX = 20, barY = 20;
        double barWidth = 220;
        double barHeight = 24;
        double innerPadding = 3;

        // ====== HP CLAMP ======
        double hp = Math.max(0, Math.min(player.getHp(), player.getMaxHp()));
        double hpPercent = hp / player.getMaxHp();

        // Compute inside width safely
        double innerMaxWidth = barWidth - innerPadding * 2;
        double innerWidth = innerMaxWidth * hpPercent;

        // ====== OUTER BORDER ======
        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(barX, barY, barWidth, barHeight);

        // ====== INNER BACKGROUND ======
        gc.setFill(Color.rgb(80, 0, 0));
        gc.fillRect(
                barX + innerPadding,
                barY + innerPadding,
                innerMaxWidth,
                barHeight - innerPadding * 2
        );

        // ====== HP FILL ======
        gc.setFill(Color.rgb(200, 40, 40));
        gc.fillRect(
                barX + innerPadding,
                barY + innerPadding,
                innerWidth,
                barHeight - innerPadding * 2
        );

        // ====== HP TEXT ======
        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont);

        String hpText = (int) hp + " / " + (int) player.getMaxHp();
        gc.fillText(hpText, barX + 8, barY + barHeight - 7);

        // ===== DAMAGE FLASH OVERLAY =====
        if (player.getDamageFlash() > 0) {
            double alpha = player.getDamageFlash();
            gc.setFill(Color.rgb(255, 0, 0, alpha * 0.5));
            gc.fillRect(0, 0, GameApp.WIDTH, GameApp.HEIGHT);
        }

        // ===== COIN COUNTER =====
        gc.setFill(Color.GOLD);
        gc.fillText("Coins: " + player.getCoin(), 26, 70);
    }
}
