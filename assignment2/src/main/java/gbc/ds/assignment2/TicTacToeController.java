package gbc.ds.assignment2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {

    private final Integer[][] board = new Integer[3][3];

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Task 1
    private Pair<Integer, Integer> getRandomPosition() throws Exception /*TODO: Remove the exception thrown*/ {
        // TODO: Get two numbers from 0 - 2 considerations:
        //          - Only randomize the numbers that are not 0 in the board.
        //          - You can randomize a 1D array and then transform to 2D position.
        throw new Exception("Not implemented yet");
    }

    // Task 2
    // TODO: Add below all the functions for detecting a click in a Pane inside the GridPane.
    //          - The function must return the position of the Pane, for instance
    //                  if I click Pane in position (0, 1) the function must return that coordinate.
    //                  hint: for returning the coordinate, use a Pair<Integer, Integer>
    //          - In case there is not a way to detect which Pane is begin hit and return the coordinate,
    //                  please, make a field in this class and set the coordinate to the field.

    // Task 3
    // TODO: Add below all the functions for the game to start, the user must enter his/her name and then hit GO,
    //          When GO is hit, Difficulty (Week & Intelligent AI) must become enabled and the user must select one of them,
    //          there must be a function that sets the difficulty to a field in this class for later usage.
    //          After you select a difficulty the player must be able to click a Pane.
    //          Bonus: make the Pane to glow of something when you hover it.
    //          Bonus 2: make the Pane to have a click animation (change color or something, nothing fancy).
}
