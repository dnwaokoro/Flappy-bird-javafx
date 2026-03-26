import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


// Represents the player-controlled bird.
// Extends Rectangle, so the bird is visually a square/rectangle on screen.
public class Da_birdy extends Rectangle {

    // Reference to the main game class
    Flappy_bird game;

    // Tracks whether the bird has collided (game over condition)
    boolean hit = false;

    // Position (x, y), velocity (vy), acceleration (ay), and size of the bird
    double x, y, vy, ay, size;

    // Constructor: initializes the bird and adds it to the game
    public Da_birdy(Flappy_bird game) {
        this.game = game;

        // Set bird size
        size = 30;

        // Set initial dimensions
        setHeight(size);
        setWidth(size);

        // Temporary color (gets replaced later with image patterns)
        setFill(Color.HOTPINK);

        // Add bird to the motion pane so it appears on screen
        game.motionPane.getChildren().add(this);
    }

    // Updates physics each frame (movement logic)
    void updatePhysics() {

        // Apply acceleration (gravity-like effect)
        vy += ay;

        // Update vertical position based on velocity
        y += vy;
    }

    // Updates the bird's position and size visually on screen
    void updateGraphics() {

        // Set position on screen
        setLayoutX(x);
        setLayoutY(y);

        // Ensure size is applied visually
        setWidth(size);
        setHeight(size);
    }
}