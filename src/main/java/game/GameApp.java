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

        this.player = new Player(400, 300, 10, 5, 5,"Player",20);

        // Create RoomManager with grid size (example: 3×3 = 9 rooms)
        roomManager = RoomManager.getInstance(player);

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

    private void drawHUD(GraphicsContext gc) {

        // ===== HEALTH BAR =====
        double maxWidth = 200;     // width of the health bar
        double barHeight = 20;
        double x = 20, y = 20;

        double hpPercent = (double) player.getHp() / player.getMaxHp();
        double currentWidth = maxWidth * hpPercent;

        gc.setFill(Color.DARKRED);
        gc.fillRect(x, y, maxWidth, barHeight);

        gc.setFill(Color.RED);
        gc.fillRect(x, y, currentWidth, barHeight);

        gc.setStroke(Color.WHITE);
        gc.strokeRect(x, y, maxWidth, barHeight);

        // ===== DAMAGE FLASH =====
        if (player.getDamageFlash() > 0) {
            double alpha = player.getDamageFlash();
            gc.setFill(Color.rgb(255, 0, 0, alpha * 0.5));
            gc.fillRect(0, 0, GameApp.WIDTH, GameApp.HEIGHT);
        }

        // ===== COIN COUNTER =====
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font(20));
        gc.fillText("Coins: " + player.getCoin(), 20, 70);
    }
}
