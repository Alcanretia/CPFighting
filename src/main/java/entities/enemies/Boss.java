package entities.enemies;

import core.SpecialAbilityCapable;
import entities.items.Coin;
import entities.player.Player;
import game.RoomManager;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import rooms.Room;
import entities.bullets.Bullet;
import util.CharacterType;

public class Boss extends Enemy implements SpecialAbilityCapable {
    private Player player;
    private int maxHp;

    private volatile boolean acting = false;

    private double targetX, targetY;
    private static double walkSpeed = 80;
    private static double chargeSpeed = 400;

    private static double specialAbilityCooldown = 20000;
    private long lastSpecial = 0;

    public Boss(double x, double y, Room room, int hp, int power, Player player,double diameter){
        super(x,y,hp,power,room,diameter);
        this.player = player;
        this.maxHp = hp;

        Thread t = new Thread(this::loopBehavior);
        t.setDaemon(true);
        t.start();
    }

    private void loopBehavior(){
        try {
            while (isAlive()) {
                // Ability and algo
                while (!room.isActive() && isAlive()){
                    Thread.sleep(100);
                }

                if (acting){
                    Thread.sleep(20);
                    continue;
                }
                long now = System.currentTimeMillis();
                if (now - lastSpecial >= specialAbilityCooldown){
                    useSpecialAbility();
                    lastSpecial = now;
                    continue;
                }

                int choice = (int)(Math.random() * 4);
                acting = true;
                switch (choice){
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

    @Override
    public void useSpecialAbility() {
        // circle laser?
        acting = true;

        new Thread(() -> {
            try {
                long end = System.currentTimeMillis() + 3000; // 3 sec laser
                double angle = 0;

                while (System.currentTimeMillis() < end && isAlive && room.isActive()) {

                    angle += Math.toRadians(10); // sweep speed

                    // one bullet = "fake laser"
                    double vx = Math.cos(angle) * 400;
                    double vy = Math.sin(angle) * 400;

                    room.spawnBullet(new Bullet(x, y, vx, vy,this.getPower(), CharacterType.BossType));

                    Thread.sleep(20); // frequency of bullets
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            acting = false;
        }).start();
    }
    public void walk() throws InterruptedException{
        acting = true;

        long duration = 3000;
        long start = System.currentTimeMillis();


        while (System.currentTimeMillis() - start < duration &&
                isAlive && room.isActive()) {

            // Direction toward player
            double dx = player.getX() - x;
            double dy = player.getY() - y;

            double dist = Math.sqrt(dx*dx + dy*dy);
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

    ///  Ability 1 ///
    public void shoot() throws InterruptedException{
        // shoot 8-16 bullets around them
        acting = true;

        long end = System.currentTimeMillis() + 3000; // 3 seconds

        while (System.currentTimeMillis() < end) {
            shootCircle();
            Thread.sleep(1500); // 1.5 sec
        }

        acting = false;
    }

    public void shootCircle(){
        int count = 16;
        for (int i = 0; i < count; i++) {
            double angle = (Math.PI * 2) * (i / (double) count);
            double vx = Math.cos(angle) * 250;
            double vy = Math.sin(angle) * 250;
            room.spawnBullet(new Bullet(x, y, vx, vy,this.getPower(),CharacterType.BossType));
        }
    }

    /// Ability 2 ///
    public void blurp() throws InterruptedException{
        // shoot shotgun-like bullets at player
        acting = true;

        long end = System.currentTimeMillis() + 3000;

        while (System.currentTimeMillis() < end) {
            shotgunOnce();
            Thread.sleep(1500);
        }

        acting = false;
    }

    public void shotgunOnce(){
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double base = Math.atan2(dy, dx);

        int pellets = 7;
        double spread = Math.toRadians(30);

        for (int i = 0; i < pellets; i++) {
            double angle = base + spread * (i - pellets / 2.0) / (pellets / 2.0);
            double vx = Math.cos(angle) * 350;
            double vy = Math.sin(angle) * 350;
            room.spawnBullet(new Bullet(x, y, vx, vy,this.getPower(),CharacterType.BossType));
        }
    }

    ///  Ability 3 ///
    public void charge() throws InterruptedException{
        // charge
        acting = true;

        double dx = player.getX() - x;
        double dy = player.getY() - y;

        double len = Math.sqrt(dx*dx + dy*dy);
        if (len == 0) len = 1;

        targetX = x + (dx/len) * 200;
        targetY = y + (dy/len) * 200;

        targetX = Math.max(30, Math.min(room.getWidth() - 30, targetX));
        targetY = Math.max(30, Math.min(room.getHeight() - 30, targetY));

        smoothMoveSpeed(1.5, chargeSpeed); // 1.5 sec

        acting = false;
    }

    private void smoothMoveSpeed(double seconds, double speed) throws InterruptedException {
        double startX = x;
        double startY = y;

        double dx = targetX - x;
        double dy = targetY - y;

        double dist = Math.sqrt(dx*dx + dy*dy);
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

            Thread.sleep((long)step);
            time += step;

            if (!isAlive) return;
        }

        x = targetX;
        y = targetY;
    }

    @Override
    public void onDead(){
        // Increase Difficulty?
        room.spawnItems(new Coin(x-10,y));
        room.spawnItems(new Coin(x,y+10));
        room.spawnItems(new Coin(x,y));
        room.spawnItems(new Coin(x+10,y));
        room.spawnItems(new Coin(x,y-10));

        RoomManager.getInstance().increaseDifficulty();
    }


    ///  JavaFX
    @Override
    public void update(double dt){
        // already on thread
        if (getDamageFlash() > 0) {
            setDamageFlash(getDamageFlash() - dt * 2);
            if (getDamageFlash()< 0) setDamageFlash(0);
        }
    }

    @Override
    public void render(GraphicsContext gc){
        gc.setFill(acting ? Color.PURPLE : Color.BLUE);
        gc.fillRect(x - 30, y - 30, 60, 60);

        if (getDamageFlash() > 0) {
            gc.setGlobalAlpha(getDamageFlash()); // 0 = transparent, 1 = solid
            gc.setFill(Color.WHITE);
            gc.fillRect(x - diameter/2, y - diameter/2, diameter, diameter);
            gc.setGlobalAlpha(1); // reset alpha
        }

        // HP bar
        double ratio = (double) this.getHp() / maxHp;
        ratio = Math.max(0, Math.min(1, ratio)); // clamp 0–1

        gc.setFill(Color.RED);
        gc.fillRect(x - 30, y - 40, 60 * ratio, 6);
    }
}
