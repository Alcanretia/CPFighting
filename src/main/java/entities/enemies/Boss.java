package entities.enemies;

import core.SpecialAbilityCapable;
import entities.items.Coin;
import entities.player.Player;
import game.RoomManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import rooms.Room;
import entities.bullets.Bullet;
import util.CharacterType;

/**
 * Represents a boss enemy in the game.
 * The Boss has multiple attack abilities, can move and charge toward the player,
 * and uses a special ability periodically. Drops coins and increases difficulty upon death.
 */
public class Boss extends Enemy implements SpecialAbilityCapable {
    /** Reference to the player object for targeting and attacks. */
    private Player player;

    /** Maximum health points of the boss. */
    private int maxHp;

    /** Whether the boss is currently performing an action. */
    private volatile boolean acting = false;

    /** Target X position for movement/charge. */
    private double targetX;

    /** Target Y position for movement/charge. */
    private double targetY;

    /** Normal walking speed of the boss (pixels/sec). */
    private static double walkSpeed = 80;

    /** Charging speed of the boss (pixels/sec). */
    private static double chargeSpeed = 400;

    /** Cooldown for the special ability in milliseconds. */
    private static double specialAbilityCooldown = 20000;

    /** Timestamp of the last special ability usage. */
    private long lastSpecial = 0;

    /** Static image used for rendering the boss sprite. */
    private static final Image BOSS_SPRITE = new Image("sprites/boss.png");

    /**
     * Constructs a new Boss.
     *
     * @param x        Initial X position.
     * @param y        Initial Y position.
     * @param room     Room the boss is located in.
     * @param hp       Health points of the boss.
     * @param power    Attack power.
     * @param player   Reference to the player object.
     * @param diameter Diameter of the boss hitbox.
     */
    public Boss(double x, double y, Room room, int hp, int power, Player player, double diameter) {
        super(x, y, hp, power, room, diameter);
        this.player = player;
        this.maxHp = hp;

        Thread t = new Thread(this::loopBehavior);
        t.setDaemon(true);
        t.start();
    }

    /**
     * Internal AI loop that continuously decides the boss's actions
     * including walking, shooting, special attacks, and charging.
     */
    private void loopBehavior() {
        try {
            while (isAlive()) {
                while (!room.isActive() && isAlive()) {
                    Thread.sleep(100);
                }

                if (acting) {
                    Thread.sleep(20);
                    continue;
                }

                long now = System.currentTimeMillis();
                if (now - lastSpecial >= specialAbilityCooldown) {
                    useSpecialAbility();
                    lastSpecial = now;
                    continue;
                }

                int choice = (int) (Math.random() * 4);
                acting = true;
                switch (choice) {
                    case 0 -> walk();
                    case 1 -> shoot();
                    case 2 -> blurp();
                    case 3 -> charge();
                }
                acting = false;
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the boss's special ability: spinning circular bullet attack.
     */
    @Override
    public void useSpecialAbility() {
        acting = true;

        new Thread(() -> {
            try {
                long end = System.currentTimeMillis() + 3000; // 3-second duration
                double angle = 0;

                while (System.currentTimeMillis() < end && isAlive && room.isActive()) {
                    angle += Math.toRadians(10); // sweep speed

                    double vx = Math.cos(angle) * 400;
                    double vy = Math.sin(angle) * 400;

                    room.spawnBullet(new Bullet(x, y, vx, vy, this.getPower(), CharacterType.BossType));
                    Thread.sleep(20);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            acting = false;
        }).start();
    }

    /**
     * Makes the boss walk toward the player for a fixed duration.
     * @throws InterruptedException If thread is interrupted.
     */
    public void walk() throws InterruptedException {
        acting = true;
        long duration = 3000;
        long start = System.currentTimeMillis();

        while (System.currentTimeMillis() - start < duration && isAlive && room.isActive()) {
            double dx = player.getX() - x;
            double dy = player.getY() - y;
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist > 0) {
                dx /= dist;
                dy /= dist;
            }

            double speed = 90;
            x += dx * speed * 0.016;
            y += dy * speed * 0.016;

            Thread.sleep(16); // ~60 FPS
        }
        acting = false;
    }

    /**
     * Boss ability: shoots bullets in a circular pattern.
     * @throws InterruptedException If thread is interrupted.
     */
    public void shoot() throws InterruptedException {
        acting = true;
        long end = System.currentTimeMillis() + 3000;

        while (System.currentTimeMillis() < end) {
            shootCircle();
            Thread.sleep(1500);
        }

        acting = false;
    }

    /**
     * Spawns bullets in a circle around the boss.
     */
    public void shootCircle() {
        int count = 16;
        for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2) * (i / (double) count);
            double vx = Math.cos(angle) * 250;
            double vy = Math.sin(angle) * 250;
            room.spawnBullet(new Bullet(x, y, vx, vy, this.getPower(), CharacterType.BossType));
        }
    }

    /**
     * Boss ability: shoots shotgun-like bullets toward the player.
     * @throws InterruptedException If thread is interrupted.
     */
    public void blurp() throws InterruptedException {
        acting = true;
        long end = System.currentTimeMillis() + 3000;

        while (System.currentTimeMillis() < end) {
            shotgunOnce();
            Thread.sleep(1500);
        }

        acting = false;
    }

    /**
     * Shoots a single spread of bullets toward the player.
     */
    public void shotgunOnce() {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double base = Math.atan2(dy, dx);

        int pellets = 7;
        double spread = Math.toRadians(30);

        for (int i = 0; i < pellets; i++) {
            double angle = base + spread * (i - pellets / 2.0) / (pellets / 2.0);
            double vx = Math.cos(angle) * 350;
            double vy = Math.sin(angle) * 350;
            room.spawnBullet(new Bullet(x, y, vx, vy, this.getPower(), CharacterType.BossType));
        }
    }

    /**
     * Boss ability: charges toward a target near the player.
     * @throws InterruptedException If thread is interrupted.
     */
    public void charge() throws InterruptedException {
        acting = true;

        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len == 0) len = 1;

        targetX = x + (dx / len) * 200;
        targetY = y + (dy / len) * 200;

        targetX = Math.max(30, Math.min(room.getWidth() - 30, targetX));
        targetY = Math.max(30, Math.min(room.getHeight() - 30, targetY));

        smoothMoveSpeed(1.5, chargeSpeed);
        acting = false;
    }

    /**
     * Smoothly moves the boss toward a target position at a given speed.
     *
     * @param seconds Duration of movement.
     * @param speed   Speed in pixels per second.
     * @throws InterruptedException If thread is interrupted.
     */
    private void smoothMoveSpeed(double seconds, double speed) throws InterruptedException {
        double startX = x;
        double startY = y;

        double dx = targetX - x;
        double dy = targetY - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;

        double duration = seconds * 1000;
        double travelTime = dist / speed * 1000;

        double time = 0;
        double step = 16; // ~60 FPS

        while (time < Math.min(duration, travelTime)) {
            double t = time / Math.min(duration, travelTime);
            double smooth = (1 - Math.cos(t * Math.PI)) / 2;

            x = startX + (dx * smooth);
            y = startY + (dy * smooth);

            Thread.sleep((long) step);
            time += step;

            if (!isAlive) return;
        }

        x = targetX;
        y = targetY;
    }

    /**
     * Called when the boss dies.
     * Spawns coins and increases game difficulty.
     */
    @Override
    public void onDead() {
        room.spawnItems(new Coin(x - 10, y));
        room.spawnItems(new Coin(x, y + 10));
        room.spawnItems(new Coin(x, y));
        room.spawnItems(new Coin(x + 10, y));
        room.spawnItems(new Coin(x, y - 10));

        RoomManager.getInstance().increaseDifficulty();
    }

    /**
     * Updates the boss state each frame (damage flash, etc.).
     *
     * @param dt Delta time since last frame.
     */
    @Override
    public void update(double dt) {
        if (getDamageFlash() > 0) {
            setDamageFlash(getDamageFlash() - dt * 2);
            if (getDamageFlash() < 0) setDamageFlash(0);
        }
    }

    /**
     * Renders the boss, including sprite, damage flash, and HP bar.
     *
     * @param gc GraphicsContext for rendering.
     */
    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(getX() - (diameter / 2) - 1, getY() - (diameter / 2) - 1, diameter + 2, diameter + 2);
        gc.drawImage(BOSS_SPRITE, getX() - (diameter / 2), getY() - (diameter / 2), diameter, diameter);

        if (getDamageFlash() > 0) {
            gc.setGlobalAlpha(getDamageFlash());
            gc.setFill(Color.WHITE);
            gc.fillRect(x - diameter / 2, y - diameter / 2, diameter, diameter);
            gc.setGlobalAlpha(1);
        }

        double ratio = (double) this.getHp() / maxHp;
        ratio = Math.max(0, Math.min(1, ratio));

        gc.setFill(Color.RED);
        gc.fillRect(x - 30, y - 40, 60 * ratio, 6);
    }
}
