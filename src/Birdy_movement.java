import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


// Handles keyboard input for the bird (specifically the spacebar).
public class Birdy_movement {

    // Reference to the main game
    Flappy_bird game;

    // Tracks whether the spacebar is currently being pressed
    boolean spaceBar_pressed;

    // Constructor: sets up key listeners on the game scene
    public Birdy_movement(Flappy_bird game) {
        this.game = game;

        // Detect when a key is pressed
        game.gameScene.setOnKeyPressed(event -> {

            // Check which key was pressed
            switch (event.getCode()) {

                // If spacebar is pressed, set flag to true
                case SPACE -> spaceBar_pressed = true;
            }
        });

        // Detect when a key is released
        game.gameScene.setOnKeyReleased(event -> {

            // Check which key was released
            switch (event.getCode()) {

                // If spacebar is released, set flag to false
                case SPACE -> spaceBar_pressed = false;
            }
        });
    }
}