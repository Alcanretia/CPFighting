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
import rooms.Room;
import util.BGMManager;
import util.Input;
import util.SFXManager;

/**
 * Main game application class for JavaFX Bullet Hell.
 * Handles the game loop, rendering, input, and player/room management.
 */
public class GameApp extends Application {

    /** Flag to track if the game has started */
    private boolean gameStarted = false;

    /** Flag to track if the game is over */
    private boolean gameOver = false;

    /** Flag to ensure game-over sound effect only plays once */
    private boolean gameOverSFXPlayed = false;

    /** Reference to the player object */
    private Player player;

    /** Game canvas width */
    public static final double WIDTH = 800;

    /** Game canvas height */
    public static final double HEIGHT = 600;

    /** Room manager singleton */
    private RoomManager roomManager;

    /** Font used for HUD and screens */
    private Font pixelFont;

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return The player instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Initializes and starts the JavaFX application.
     *
     * @param stage The primary stage provided by JavaFX
     */
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        Input.initialize();

        this.player = new Player(400, 300, 10, 1,  "Player", 32);

        roomManager = RoomManager.getInstance(player);

        // Load sound effects
        SFXManager.load("shoot", "/sounds/shoot.mp3");
        SFXManager.load("laser", "/sounds/laser.mp3");
        SFXManager.load("take_damage", "/sounds/take_damage.mp3");
        SFXManager.load("coin", "/sounds/coin.mp3");
        SFXManager.load("game_start", "/sounds/game_start.mp3");
        SFXManager.load("game_over", "/sounds/game_over.mp3");

        Scene scene = new Scene(new StackPane(canvas));
        Input.attach(scene);

        stage.setScene(scene);
        stage.setTitle("JavaFX Bullet Hell - Infinite Rooms");
        stage.show();

        new AnimationTimer() {
            long last = 0;

            @Override
            public void handle(long now) {
                if (!gameStarted) {
                    drawStartScreen(gc);
                    if (Input.isPressed("ENTER")) {
                        SFXManager.play("game_start");
                        BGMManager.play("/music/bgm.mp3");
                        gameStarted = true;
                    }
                    return;
                }

                if (player.isDead()) {
                    gameOver = true;
                }

                if (gameOver) {
                    if (!gameOverSFXPlayed) {
                        SFXManager.stopAll();
                        BGMManager.stop();
                        SFXManager.play("game_over");
                        gameOverSFXPlayed = true;
                    }
                    drawDeadScreen(gc);
                    if (Input.isPressed("R")) {
                        restartGame();
                        gameOverSFXPlayed = false;
                    }
                    return;
                }

                // Normal game update/draw
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

    /**
     * Resets game state and player/room manager for a new game.
     */
    private void restartGame() {
        this.player = new Player(400, 300, 10, 1, "Player", 32);
        roomManager.resetInstance();
        roomManager = RoomManager.getInstance(player);

        gameOver = false;
        gameStarted = false; // optional → show start screen again
    }

    /**
     * Draws the start screen with instructions.
     *
     * @param gc GraphicsContext for drawing
     */
    private void drawStartScreen(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 20);

        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont);
        gc.fillText("PRESS ENTER TO START", WIDTH / 2 - 200, HEIGHT / 2);
    }

    /**
     * Draws the death/game-over screen with score and restart instructions.
     *
     * @param gc GraphicsContext for drawing
     */
    private void drawDeadScreen(GraphicsContext gc) {
        gc.setFill(Color.color(0, 0, 0, 0.6)); // transparent fade
        gc.fillRect(0, 0, WIDTH, HEIGHT);

        pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 20);
        gc.setFill(Color.RED);
        gc.setFont(pixelFont);
        gc.fillText("YOU DIED!", WIDTH / 2 - 90, HEIGHT / 2 - 40);

        gc.setFill(Color.WHITE);
        pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 10);
        gc.setFont(pixelFont);
        gc.fillText("Score : " + RoomManager.getInstance().getScore(), WIDTH / 2 - 50, HEIGHT / 2 - 15);
        gc.fillText("Press R to Restart", WIDTH / 2 - 90, HEIGHT / 2 + 20);
    }

    /**
     * Draws the heads-up display (HUD) including HP bar and coin counter.
     *
     * @param gc GraphicsContext for drawing
     */
    private void drawHUD(GraphicsContext gc) {
        gc.setImageSmoothing(false);

        pixelFont = Font.loadFont(getClass().getResourceAsStream("/fonts/PressStart2P.ttf"), 12);

        double barX = 20, barY = 20;
        double barWidth = 220, barHeight = 24, innerPadding = 3;

        double hp = Math.max(0, Math.min(player.getHp(), player.getMaxHp()));
        double hpPercent = hp / player.getMaxHp();

        double innerMaxWidth = barWidth - innerPadding * 2;
        double innerWidth = innerMaxWidth * hpPercent;

        gc.setFill(Color.rgb(30, 30, 30));
        gc.fillRect(barX, barY, barWidth, barHeight);

        gc.setFill(Color.rgb(80, 0, 0));
        gc.fillRect(barX + innerPadding, barY + innerPadding, innerMaxWidth, barHeight - innerPadding * 2);

        gc.setFill(Color.rgb(200, 40, 40));
        gc.fillRect(barX + innerPadding, barY + innerPadding, innerWidth, barHeight - innerPadding * 2);

        gc.setFill(Color.WHITE);
        gc.setFont(pixelFont);
        String hpText = (int) hp + " / " + (int) player.getMaxHp();
        gc.fillText(hpText, barX + 8, barY + barHeight - 7);

        if (player.getDamageFlash() > 0) {
            double alpha = player.getDamageFlash();
            gc.setFill(Color.rgb(255, 0, 0, alpha * 0.5));
            gc.fillRect(0, 0, GameApp.WIDTH, GameApp.HEIGHT);
        }

        gc.setFill(Color.GOLD);
        gc.fillText("Coins: " + player.getCoin(), 26, 70);
    }
}
