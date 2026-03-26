import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

// Controls the main game loop.
// This class repeatedly updates the game while it is running.
public class Game_timer extends AnimationTimer {

    // Reference to the main game class
    Flappy_bird game;

    // Constructor: stores reference to the game
    public Game_timer(Flappy_bird game) {
        this.game = game;
    }

    // Starts the animation timer / game loop
    @Override
    public void start() {
        super.start();
    }

    // Runs every frame while the timer is active
    @Override
    public void handle(long now) {
        // if (!game.paused)

        if (!game.paused) {
            // When the game is active, update all systems
            updateCounters();
            updatePhysics();
            updateBirdymove();
            updateGraphics();
        } else {
            // When paused, only allow menu/start behavior
            updateMenuMove();
        }
    }

    // Updates counters used for timed events like obstacle spawning
    void updateCounters() {
        game.counterPoleSpawn--;

        // When counter reaches below 0, spawn a new obstacle
        if (game.counterPoleSpawn < 0) {
            game.spawnPole();
            game.counterPoleSpawn = game.intervalPoleSpawn;

            // Debug output showing current obstacle count
            System.out.println("Num Poles = " + game.obstacles.size());
        }
    }

    // Updates physics for bird and all obstacles
    void updatePhysics() {
        game.daBirdy.updatePhysics();

        for (int i = 0; i < game.obstacles.size(); i++) {
            game.obstacles.get(i).updatePhysics();
        }
    }

    // Handles movement/input while on the menu or paused
    void updateMenuMove() {
        if (game.birdyMove.spaceBar_pressed) {
            // Unpause/start the game if space is pressed
            game.menuPause(false);

            if (!game.paused) {
                game.menuVbox.setVisible(false);
            }

            // If bird is already hit, show dead bird sprite
            if (game.birdyMove.spaceBar_pressed && game.daBirdy.hit) {
                game.daBirdy.setFill(game.deadBird);
            }
        }
    }

    // Updates visual position/appearance of bird and obstacles
    void updateGraphics() {
        game.daBirdy.updateGraphics();

        for (int i = 0; i < game.obstacles.size(); i++) {
            game.obstacles.get(i).updateGraphics();
        }
    }

    // Handles bird movement behavior and sprite changes based on input
    void updateBirdymove() {

        // If space is pressed, move bird upward
        if (game.birdyMove.spaceBar_pressed) {
            game.daBirdy.vy = -4;
            game.daBirdy.setFill(game.fTwo);

            // If bird has already crashed, show dead sprite instead
            if (game.birdyMove.spaceBar_pressed && game.daBirdy.hit) {
                game.daBirdy.setFill(game.deadBird);
            }

            // If space is not pressed, bird falls downward
        } else if (!game.birdyMove.spaceBar_pressed) {
            game.daBirdy.vy += .3;
            game.daBirdy.setFill(game.fOne);

            // If bird has already crashed, show dead sprite instead
            if (!game.birdyMove.spaceBar_pressed && game.daBirdy.hit) {
                game.daBirdy.setFill(game.deadBird);
            }

        } else {
            // Fallback case
            game.daBirdy.vy = 0;
        }
    }
}