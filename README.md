# CPFighting

A top-down bullet hell game built with **pure Java and JavaFX**, in the spirit of *The Binding of Isaac*. Fight through an endless series of rooms, dodge bullets, collect items, spend coins in the shop, and take down bosses to push your score as high as possible.

The project is designed around **object-oriented programming principles**: shared behavior lives in abstract base classes, capabilities are described by interfaces, and each game entity defines its own behavior.

## Features

- **Top-down bullet hell combat** with separate movement and shooting controls
- **Endless room progression** with a score that grows as you clear rooms
- **Player abilities**: a dash with a cooldown, and a laser special ability
- **Three enemy types**
  - **Chaser** – moves toward you along one axis at a time and charges when lined up with you; drops a coin and fires four bullets in four directions when it dies
  - **Jumper** – randomly jumps around and fires four bullets after each jump
  - **Boss** – walks toward you, charges, shoots, and uses a spinning circular bullet attack as its special ability
- **Different room types**: combat rooms, boss rooms, treasure rooms, and a store room
- **Items**: coins, health potions, strength potions, max health upgrades, treasure boxes, and mystery boxes (random reward, paid for with coins)
- **Retro presentation** with pixel sprites, the Press Start 2P font, background music, and sound effects

## Controls

| Action | Key |
| --- | --- |
| Start game | `Enter` |
| Move | `W` `A` `S` `D` |
| Shoot | Arrow keys |
| Dash | `Space` |
| Special ability (laser) | `E` |
| Restart after dying | `R` |

## Getting Started

### Prerequisites

- JDK 22 or later (required by JavaFX 24)

The Gradle wrapper is included, so you do not need to install Gradle yourself.

### Run the game

```bash
git clone https://github.com/<your-username>/CPFighting.git
cd CPFighting
./gradlew run          # Windows: gradlew.bat run
```

The game opens in an 800×600 window. Press `Enter` to start.

### Build a runnable JAR

```bash
./gradlew jar
```

## Object-Oriented Design

### Class hierarchy

```
GameObject (abstract)               position, update(dt), render(gc)
├── Character (abstract)            hp, power, hitbox        implements Hittable
│   ├── Player                                               implements SpecialAbilityCapable
│   └── Enemy (abstract)
│       ├── Chaser
│       ├── Jumper
│       └── Boss                                             implements SpecialAbilityCapable
├── Bullet
└── Laser

Item (abstract)                     implements Collectible
├── Coin
├── HealthPot
├── StrengthPot
├── MaxHealthUpgrade
├── TreasureBox
└── MysteryBox

Room (abstract)
├── NormalRoom
├── CombatRoom
│   └── BossRoom
├── TreasureRoom
└── StoreRoom
```

### How the OOP principles show up

- **Abstraction** – `GameObject`, `Character`, `Enemy`, `Item`, and `Room` are abstract. They define what every game object must do without fixing how.
- **Inheritance** – `Character` adds health, power, and a hitbox on top of `GameObject`. `Player` and every `Enemy` reuse that instead of redefining it. `BossRoom` extends `CombatRoom`.
- **Polymorphism** – the game loop updates and renders a mix of players, enemies, bullets, and items through their shared types. Each class supplies its own `update` and `render`, and each item its own `collect` effect.
- **Interfaces** – small interfaces describe capabilities: `Hittable` (can take damage), `Collectible` (can be picked up), and `SpecialAbilityCapable` (has a special ability). `Player` and `Boss` both implement the last one without sharing a parent.
- **Encapsulation** – fields such as `hp` and `maxHp` are private, and setters enforce valid values (for example, hp is clamped between 0 and max HP, and positions are kept inside the screen).
- **Singleton** – `RoomManager` is a singleton that creates rooms, handles transitions, tracks cleared rooms, and scales difficulty.

### Room progression

The game starts in an empty `NormalRoom`. After that, `RoomManager` decides the next room from the last digit of the number of rooms cleared: 0 gives a boss room, 5 a treasure room, 7 a store room, and anything else a combat room. Boss health scales with a difficulty value that grows as the game goes on.

## Project Structure

```
CPFighting/
├── build.gradle
├── gradlew / gradlew.bat
└── src/main/
    ├── java/
    │   ├── main/        # Main (entry point)
    │   ├── game/        # GameApp (window and game loop), RoomManager
    │   ├── core/        # GameObject, Character, and the Hittable /
    │   │                #   Collectible / SpecialAbilityCapable interfaces
    │   ├── entities/
    │   │   ├── player/  # Player
    │   │   ├── enemies/ # Enemy, Chaser, Jumper, Boss
    │   │   ├── bullets/ # Bullet, Laser
    │   │   └── items/   # Item and its subclasses
    │   ├── rooms/       # Room and its subclasses, Door
    │   └── util/        # Input, BulletUtil, BGMManager, SFXManager, CharacterType
    └── resources/       # sprites, fonts, music, sound effects
```

## Tech Stack

- Java
- JavaFX 24 (graphics, media, controls)
- Gradle
