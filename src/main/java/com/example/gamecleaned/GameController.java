package com.example.gamecleaned;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.util.Duration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    private Stage stage;
    private Scene scene;
    private int gameTicks = 0;
    private Direction direction = Direction.RIGHT;

    @FXML
    private Rectangle blueBox;

    @FXML
    private Rectangle greenBox;

    @FXML
    private Rectangle redBox;

    @FXML
    private Rectangle yellowBox;

    @FXML
    private Rectangle snakeHead = new Rectangle(250,250,50,50);

    double xPos = snakeHead.getLayoutX();
    double yPos = snakeHead.getLayoutY();

    @FXML
    private Text sequenceID;

    @FXML
    private TextField currentScoreIndicator;

    @FXML
    private TextField highestScoreIndicator;

    @FXML
    private Button bttnCheck;

    @FXML
    private Button mainMenuBttn;

    @FXML
    private Button memoryGameBttn;

    @FXML
    private Button startSequenceBttn;

    @FXML
    private AnchorPane anchorPane;


    ArrayList<Rectangle> boxes = new ArrayList<>();
    ArrayList<Rectangle> snakeBody = new ArrayList<>();
    List<SnakeGame> positions = new ArrayList<>();

    Game Game = new Game();
    MemoryGame memoryGame = new MemoryGame();
    SnakeGame snakeGame = new SnakeGame(snakeHead.getX() +xPos, snakeHead.getY() + yPos);
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e ->{
        positions.add(snakeGame);
        moveSnakeHead(snakeHead);
//        for (int i = 1; i < snakeBody.size(); i++) {
//            moveSnakeTail(snakeBody.get(i),i);
//        }
        gameTicks++;
    }));

    void initializeMemoryGame() throws IOException {
        boxes.add(redBox);
        boxes.add(yellowBox);
        boxes.add(greenBox);
        boxes.add(blueBox);
        memoryGame.playerSequence.clear();
        memoryGame.sequence.clear();
        highestScoreIndicator.setText(Game.retrieveRecord("Game/src/record.txt"));
        memoryGame.generateSequence();
        sequenceID.setText(memoryGame.displaySequence());
        memoryGame.indicateSequence(boxes);
    }

    void initializeSnakeGame() {
        snakeBody.add(snakeHead);
        snakeHead.setFill(Color.RED);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        anchorPane.getChildren().addAll(snakeHead); //anchor pane was null, meaning it did not have an fxID, also
        //snake-game.fxml did not even have an anchorPane, it was just pane.
        //when you run into issues, always check fxml aswell as your code.
        //remember that you are working on JavaFX now, not just java.
    }


    @FXML
    void switchToMainMenu(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("start-up.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switchToMemoryGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void startMemoryBttnClicked(ActionEvent event) throws IOException {
        System.out.println("New memory game has started!");
        initializeMemoryGame();
    }

    @FXML
    void startSnakeBttnClicked(ActionEvent event) {
        System.out.println("New snake game has started!");
        initializeSnakeGame();
    }

    @FXML
    public void switchToSnakeGame(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("snake-game.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void redBoxClicked(MouseEvent event) {
        System.out.println("Red box has been clicked!");
        memoryGame.playerSequence.add(1);
        System.out.println(memoryGame.playerSequence);
    }

    @FXML
    void yellowBoxClicked(MouseEvent event) {
        System.out.println("Yellow box has been clicked!");
        memoryGame.playerSequence.add(2);
        System.out.println(memoryGame.playerSequence);
    }

    @FXML
    void greenBoxClicked(MouseEvent event) {
        System.out.println("Green box has been clicked!");
        memoryGame.playerSequence.add(3);
        System.out.println(memoryGame.playerSequence);

    }

    @FXML
    void blueBoxClicked(MouseEvent event) {
        System.out.println("Blue box has been clicked!");
        memoryGame.playerSequence.add(4);
        System.out.println(memoryGame.playerSequence);
    }


    @FXML
    void keyPressed(KeyEvent event) {
        if(event.getCode().equals(KeyCode.W) && direction != Direction.DOWN){
            System.out.println("Direction UP is being called!");
            direction = Direction.UP;
        } else if(event.getCode().equals(KeyCode.S) && direction != Direction.UP){
            System.out.println("Direction DOWN is being called!");
            direction = Direction.DOWN;
        }else if(event.getCode().equals(KeyCode.A) && direction != Direction.RIGHT){
            System.out.println("Direction LEFT is being called!");
            direction = Direction.LEFT;
        }else if(event.getCode().equals(KeyCode.D) && direction != Direction.LEFT){
            System.out.println("Direction RIGHT is being called!");
            direction = Direction.RIGHT;
        } //not my code
    }

    @FXML
    void checkPlayerSequence(MouseEvent event) throws IOException { //comfirm sequence bttn
        boolean success = memoryGame.compareSequence();
        if (success) {
            System.out.println("you got it!");
            currentScoreIndicator.setText(String.valueOf(memoryGame.getCurrentScore())); //indicates player current score.
            memoryGame.successfulAttempt(boxes); //generates a new sequence with +1 element of player's current score, erases current playerSequence
            sequenceID.setText(memoryGame.displaySequence()); //comment out later
        }
        else {
            System.out.println("WRONG, it was : ");
            System.out.println(memoryGame.sequence.toString());
            memoryGame.checkRecord("Game/src/record.txt"); //moved this above below and now works. compareScore sets highestScore = currentScore and that is why condition was never met.
            highestScoreIndicator.setText(String.valueOf(memoryGame.compareScore())); //power of inheritance!! ^^
            memoryGame.failedAttempt(boxes); //method same as successfulAttempt(), however currentScore is reset to 0.
            currentScoreIndicator.setText(String.valueOf(memoryGame.getCurrentScore()));
            sequenceID.setText(memoryGame.displaySequence());
        }
    }

    private void moveSnakeHead(Rectangle snakeHead){
        if(direction.equals(Direction.RIGHT)){
            xPos = xPos + 50;
            snakeHead.setTranslateX(xPos);
        } else if(direction.equals(Direction.LEFT)) {
            xPos = xPos - 50;
            snakeHead.setTranslateX(xPos);
        }else if(direction.equals(Direction.UP)) {
            yPos = yPos - 50;
            snakeHead.setTranslateY(yPos);
        }else if(direction.equals(Direction.DOWN)) {
            yPos = yPos + 50;
            snakeHead.setTranslateY(yPos);
        } //not my code
    }

}
