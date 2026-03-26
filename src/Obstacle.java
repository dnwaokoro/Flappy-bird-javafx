import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.Group;

// Represents a single obstacle (pair of pipes) in the game.
// Each obstacle consists of a top and bottom rectangle with a gap in between.
public class Obstacle extends Group {

    // Reference to main game class
    Flappy_bird game;

    // Size of the gap the bird must pass through
    final double gapSize = 180;

    // Width of each obstacle
    final double oWidth = 100;

    // Position and movement variables
    double oHeight, x, y, vx, vy, randomHeight;

    // Rectangles representing the top and bottom pipes
    Rectangle topRect, bottomRect, youLose;

    // Debug label (currently unused/commented)
    Label debugLabel;

    // Tracks loss state (not heavily used here)
    int lossCheck = 0;

    // Tracks if the bird has passed this obstacle (for scoring)
    boolean hasPassed = false;

    // Constructor: creates a new obstacle and adds it to the game
    public Obstacle(Flappy_bird game) {
        this.game = game;

        // Make obstacle very tall so it extends off screen
        oHeight = game.motionPane.getHeight() * 4;

        // Start position (right side of screen)
        x = 800;

        // Random vertical position for gap
        y = Math.random() * 400;

        // =========================
        // Top pipe setup
        // =========================

        topRect = new Rectangle(oWidth, oHeight, Color.GREEN);
        getChildren().add(topRect);

        // Position above the gap
        topRect.setY(-oHeight);

        // Apply texture/image
        topRect.setFill(game.poles);

        // =========================
        // Bottom pipe setup
        // =========================

        bottomRect = new Rectangle(oWidth, oHeight, Color.GREEN);
        getChildren().add(bottomRect);

        // Position below the gap
        bottomRect.setY(gapSize);

        // Apply texture/image
        bottomRect.setFill(game.poles);

        // Add this obstacle to the game's obstacle list
        game.obstacles.add(this);

        // Add this obstacle to the screen
        game.motionPane.getChildren().add(this);

        // Debugging (currently disabled)
//        System.out.println("obstacle y" + y);
//        debugLabel = new Label(String.format("%.2f", x));
//        getChildren().add(debugLabel);
    }

    // Updates movement and checks each frame
    void updatePhysics() {
        // Move obstacle to the left
        vx = -1.5;

        // Update position
        x += vx;
        y += vy;

        // Handle logic checks
        checkBoundary();
        checkBurdCollision();
        youLose();

        /*scoreCounting();*/
    }

    // Checks if the bird collides with this obstacle
    void checkBurdCollision() {

        double xOverlap;
        double yGapOver;

        // Bird position
        double xb = game.daBirdy.x;
        double yb = game.daBirdy.y;

        // Check horizontal overlap between bird and obstacle
        xOverlap = Math.min(xb + game.daBirdy.size, x + oWidth) - Math.max(xb, x);

        if (xOverlap > 0) {

            // Check if bird is within the vertical gap
            yGapOver = Math.min(yb + game.daBirdy.size, y + gapSize) - Math.max(yb, y);

            // If not fully inside gap → collision
            if (yGapOver < game.daBirdy.size - .1) {
                System.out.println("u looseeee");

                // Mark bird as hit (game over condition)
                game.daBirdy.hit = true;
            }
        }
    }

    // Removes obstacle if it goes off screen
    void checkBoundary() {
        if (x < -2 * oWidth) destroy();
    }

    // Removes obstacle from game and screen
    void destroy() {
        game.obstacles.remove(this);
        game.motionPane.getChildren().remove(this);
    }

    // (Currently unused) logic for increasing score when passing obstacle
    /*
    void scoreCounting(){
        if(hasPassed == true){
            game.scoreCounter++;
            hasPassed = false;
            game.scoreCounter = game.scoreCounter / 100;
            System.out.println(game.scoreCounter);
        }
    }
    */

    // Handles game-over conditions
    void youLose() {

        // Case 1: bird hit obstacle
        if (game.daBirdy.hit) {
            game.gameOver.setHeight(250);
            game.gameOver.setWidth(250);
            game.gameOver.setFill(game.lossScreen);

            game.paused = true;

            if(game.paused){
                // Show menu again
                game.menuVbox.setVisible(true);
                game.menuVbox.setAlignment(Pos.CENTER);

                // Stop game loop
                game.gameTimer.stop();
            }
        }

        // Case 2: bird falls below screen
        if (game.daBirdy.y > 731) {
            game.gameOver.setHeight(250);
            game.gameOver.setWidth(250);
            game.gameOver.setFill(game.lossScreen);

            game.daBirdy.hit = true;
            game.paused = true;

            if(game.paused){
                game.menuVbox.setVisible(true);
                game.menuVbox.setAlignment(Pos.CENTER);
                game.gameTimer.stop();
            }

            // Case 3: bird goes above screen
        } else if (game.daBirdy.y < -1) {
            game.gameOver.setHeight(250);
            game.gameOver.setWidth(250);
            game.gameOver.setFill(game.lossScreen);

            game.daBirdy.hit = true;
            game.paused = true;

            if(game.paused){
                game.menuVbox.setVisible(true);
                game.menuVbox.setAlignment(Pos.CENTER);
                game.gameTimer.stop();
            }
        }
    }

    // Updates visual position of obstacle on screen
    void updateGraphics() {
        setLayoutX(x);
        setLayoutY(y);

        // Debug display (currently disabled)
//        debugLabel.setText(String.format("%.2f", x));
    }
}