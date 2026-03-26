import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.util.ArrayList;



// Main class for the Flappy Bird game.
// This class sets up the window, loads images, creates core game objects,
// and starts the game loop.
public class Flappy_bird extends Application {

    // Image patterns used for different game visuals
    ImagePattern lossScreen;
    Label scoreKeeping;
    ImagePattern deadBird;
    ImagePattern poles;
    ImagePattern backImage;
    ImagePattern fOne;
    ImagePattern fTwo;

    // Main game scene
    Scene gameScene;

    // Pane where moving game objects are placed
    Pane motionPane;

    // Main bird object
    Da_birdy daBirdy;

    // Timer that repeatedly updates the game
    Game_timer gameTimer;

    // Background rectangle for the game
    Rectangle backGround;

    // Main container layering background, game objects, UI, and menu
    StackPane Container;

    // Handles bird movement input/behavior
    Birdy_movement birdyMove;

    // Stores all spawned obstacles
    ArrayList<Obstacle> obstacles;

    // Rectangle likely used for game-over screen display
    Rectangle gameOver;

    // Menu shown before game starts / while paused
    VBox menuVbox;
    Label menuLabel;

    // Tracks player score
    int scoreCounter;

    // Screen width and height
    double screenW, screenH;

    // Used to control obstacle spawning timing
    int counterPoleSpawn = 0;
    int intervalPoleSpawn = 280;

    // Tracks whether the game is paused
    boolean paused = true;

    // JavaFX start method: runs when the application launches
    public void start(Stage primaryStage) throws Exception {

        // =========================
        // Load game sprite images
        // =========================

        Image fOneIm = new Image(getClass().getResource("/images/da_burd.png").toExternalForm());
        fOne = new ImagePattern(fOneIm);

        Image fTwoIm = new Image(getClass().getResource("/images/da_burd2.png").toExternalForm());
        fTwo = new ImagePattern(fTwoIm);

        Image backImageIm = new Image(getClass().getResource("/images/Background.jpg").toExternalForm());
        backImage = new ImagePattern(backImageIm);

        Image poleIm = new Image(getClass().getResource("/images/Pole.png").toExternalForm());

        Image gameOverIm = new Image(getClass().getResource("/images/gameOver.png").toExternalForm());
        lossScreen = new ImagePattern(gameOverIm);

        Image deadBirdIm = new Image(getClass().getResource("/images/ded_burd.png").toExternalForm());
        deadBird = new ImagePattern(deadBirdIm);

        // Pole texture/pattern
        poles = new ImagePattern(poleIm);

        //TODO: Score label shown on screen
        scoreKeeping = new Label();

        // Screen dimensions
        screenH = 800;
        screenW = 800;

        // =========================
        // Create game objects/layout
        // =========================

        // Pane that will hold moving objects like bird and obstacles
        motionPane = new Pane();

        // Create the bird and pass this game object into it
        daBirdy = new Da_birdy(this);

        // Create background rectangle
        backGround = new Rectangle(screenH, screenW, Color.LIGHTBLUE);

        // Create game-over overlay rectangle
        gameOver = new Rectangle();

        // =========================
        // Start menu setup
        // =========================

        menuLabel = new Label("Hit space to play");
        menuVbox = new VBox(menuLabel);

        // StackPane layers items in order:
        // background -> moving objects -> game over screen -> score -> menu
        Container = new StackPane(backGround, motionPane, gameOver, scoreKeeping, menuVbox);

        // Start the game in paused/menu mode
        menuPause(paused);

        // =========================
        // Scene and game systems
        // =========================

        gameScene = new Scene(Container, screenH, screenW);
        gameTimer = new Game_timer(this);

        // List that will store obstacle objects as they are created
        obstacles = new ArrayList();

        // =========================
        // Stage/window settings
        // =========================

        primaryStage.setHeight(screenH);
        primaryStage.setWidth(screenW);
        primaryStage.setScene(gameScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Flappy_Bird ");
        primaryStage.show();

        // =========================
        // Bird starting properties
        // =========================

        // Initial movement/physics values for the bird
        daBirdy.ay += daBirdy.vy;
        daBirdy.vy = .1;
        daBirdy.x = 180;
        daBirdy.y = 300;

        // Create movement/input handler for the bird
        birdyMove = new Birdy_movement(this);

        // Start game loop
        gameTimer.start();

        // Apply background image
        backGround.setFill(backImage);
    }

    // Creates a new obstacle/pole object
    void spawnPole() {
        new Obstacle(this);
    }

    // Handles pausing the game and showing the start menu
    void menuPause(boolean paused ){
        this.paused = paused;

        if(paused){
            // Show menu text
            menuVbox.setVisible(true);
            menuVbox.setAlignment(Pos.CENTER);

            // Reset bird position and appearance while paused
            daBirdy.setLayoutX(180);
            daBirdy.setLayoutY(300);
            daBirdy.setFill(fOne);
        }
    }

    // Launches the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}