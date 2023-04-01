package gbc.ds.assignment2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class TicTacToeController implements Initializable {
    public static final int BOARD_SIZE = 3;
    public static final char[][] BOARD = {{'_', '_', '_'}, {'_', '_', '_'}, {'_', '_', '_'}};
    public static char player = 'o';
    public static char opponent = 'x';
    final Color BUTTON_COLOR = Color.WHITE;
    final Color HOVER_COLOR = Color.DARKGRAY;
    final Color CLICKED_COLOR = Color.LIGHTBLUE;

    @FXML
    private GridPane tictactoe_grid;
    @FXML
    private GridPane difficulty_grid;
    @FXML
    private GridPane symbols_grid;
    @FXML
    private GridPane details_grid;
    @FXML
    private TextField txt_name;

    private String player_name;
    private String difficulty;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tictactoe_grid.setDisable(true);
        difficulty_grid.setDisable(true);
        symbols_grid.setDisable(true);

        for (Node node : tictactoe_grid.getChildren()) {
            if (node instanceof Pane pane) {
                pane.setBackground(new Background(new BackgroundFill(BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                pane.setOnMouseEntered(event -> pane.setBackground(new Background(new BackgroundFill(HOVER_COLOR, CornerRadii.EMPTY, Insets.EMPTY))));
                pane.setOnMouseExited(event -> pane.setBackground(new Background(new BackgroundFill(BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY))));
                pane.setOnMouseClicked(event -> {
                    Pane p = (Pane) node;
                    onPositionClicked(p);
                    pane.setBackground(new Background(new BackgroundFill(CLICKED_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
                });
            }
        }

        configButtonGroup(difficulty_grid);
        configButtonGroup(symbols_grid);

        System.out.println(tictactoe_grid.getChildren());
    }

    private void onPositionClicked(Pane _pane) {
        if (_pane == null) return;

        Integer row = GridPane.getRowIndex(_pane);
        Integer col = GridPane.getColumnIndex(_pane);
        if (row == null || col == null) return;
        if (row == -1 || col == -1) return;
        if (!TicTacToeAlgorithms.isValidMove(BOARD, row, col)) return;

        Move move = new Move(row, col);
        BOARD[row][col] = player;
        setPanePlay(_pane, player);

        if (checkWinState()) return;

        opponentTurn(move);
    }

    private boolean checkWinState() {
        String state = TicTacToeAlgorithms.checkWinState(BOARD);
        String header_msg = "";
        if (state.equals("")) return false;
        if (state.equals("Tie")) header_msg = "It's a Tie!";
        else header_msg = state + " Wins!";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header_msg);
        alert.setContentText("Thanks for playing " + player_name + "!");
        alert.showAndWait();

        tictactoe_grid.setDisable(true);
        resetGame();
        return true;
    }

    private void opponentTurn(Move _player_move) {
        Move move = Objects.equals(difficulty, "Week AI") ? TicTacToeAlgorithms.getRandomPosition(BOARD) :
                TicTacToeAlgorithms.findBestMove(BOARD); //, _player_move.row, _player_move.col
        if (move.equals(Move.emptyMove())) return;
        BOARD[move.row][move.col] = opponent;
        Pane opponent_pane = (Pane) getNodeFromTicTacToeGrid(move.row, move.col);
        setPanePlay(opponent_pane, opponent);
        checkWinState();
    }

    private void setPanePlay(Pane _pane, char _play) {
        if (_pane == null) return;

        Label lb = new Label();
        lb.textProperty().set("" + _play);
        lb.textAlignmentProperty().set(TextAlignment.CENTER);
        lb.alignmentProperty().set(Pos.CENTER);
        lb.prefHeightProperty().bind(_pane.heightProperty());
        lb.prefWidthProperty().bind(_pane.widthProperty());
        lb.fontProperty().set(Font.font("Noto Sans", FontWeight.SEMI_BOLD, 30));
        _pane.getChildren().add(lb);
    }

    private Node getNodeFromTicTacToeGrid(int _row, int _col) {
        for (Node node : tictactoe_grid.getChildren()) {
            Integer columnIndex = GridPane.getColumnIndex(node);
            Integer rowIndex = GridPane.getRowIndex(node);
            if (columnIndex != null && rowIndex != null && columnIndex == _col && rowIndex == _row) {
                return node;
            }
        }
        return null;
    }

    private void resetGame() {
        for (Node node : details_grid.getChildren()) {
            node.setDisable(false);
        }

        configButtonGroup(difficulty_grid);
        configButtonGroup(symbols_grid);

        tictactoe_grid.setDisable(true);
        difficulty_grid.setDisable(true);
        symbols_grid.setDisable(true);

        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                BOARD[row][col] = '_';
            }
        }

        txt_name.setText("");

        for (Node node : tictactoe_grid.getChildren()) {
            if (node instanceof Pane pane) {
                pane.getChildren().clear();
            }
        }
    }

    private void configButtonGroup(GridPane _grid) {
        for (Node node : _grid.getChildren()) {
            Button btn = (Button) node;
            btn.setBackground(new Background(new BackgroundFill(BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setOnMouseEntered(event -> btn.setBackground(new Background(new BackgroundFill(HOVER_COLOR, CornerRadii.EMPTY, Insets.EMPTY))));
            btn.setOnMouseExited(event -> btn.setBackground(new Background(new BackgroundFill(BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY))));
        }
    }

    private void onClickButtonGroup(GridPane _grid, Button _active) {
        for (Node node : _grid.getChildren()) {
            Button btn = (Button) node;
            btn.setBackground(new Background(new BackgroundFill(BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            btn.setOnMouseEntered(null);
            btn.setOnMouseExited(null);
            if (btn.equals(_active)) {
                btn.setBackground(new Background(new BackgroundFill(HOVER_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        }
    }

    @FXML
    protected void onGoClick(ActionEvent _event) {
        String name = txt_name.getText().trim();
        if (name.equals("")) return;

        player_name = name;
        difficulty_grid.setDisable(false);
        txt_name.setDisable(true);
        ((Node) _event.getSource()).setDisable(true);
    }

    @FXML
    protected void onDifficultyClick(ActionEvent _event) {
        Button btn_source = (Button) _event.getSource();
        onClickButtonGroup(difficulty_grid, btn_source);
        difficulty = btn_source.getText();

        difficulty_grid.setDisable(true);
        symbols_grid.setDisable(false);
    }

    @FXML
    protected void onSymbolClick(ActionEvent _event) {
        Button btn_source = (Button) _event.getSource();
        onClickButtonGroup(symbols_grid, btn_source);
        player = btn_source.getText().equals("O's") ? 'o' : 'x';
        opponent = player == 'o' ? 'x' : 'o';

        symbols_grid.setDisable(true);

//        Move m = TicTacToeAlgorithms.findBestMove(BOARD, new Random().nextInt(0, 2), new Random().nextInt(0, 2));
//        while(!(m.col == 1 && m.row == 1))
//        {
//            int px = new Random().nextInt(0, 3);
//            int py = new Random().nextInt(0, 3);
//            m = TicTacToeAlgorithms.findBestMove(BOARD, px, py);
//        }

        if (UserFirstAskAlert().getButtonData().equals(ButtonBar.ButtonData.NO))
            opponentTurn(new Move(new Random().nextInt(0, 2), new Random().nextInt(0, 2))); //new Random().nextInt(0, 2), new Random().nextInt(0, 2)

        tictactoe_grid.setDisable(false);
    }

    private ButtonType UserFirstAskAlert() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm first player");
        alert.setHeaderText("Does player want to go first?");

        ButtonType btnyes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType btnno = new ButtonType("No", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(btnyes, btnno);

        Optional<ButtonType> result = alert.showAndWait();
        while (result.isEmpty()) {
            result = alert.showAndWait();
        }

        return result.get();
    }
}