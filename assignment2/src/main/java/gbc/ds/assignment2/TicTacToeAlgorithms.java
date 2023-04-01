package gbc.ds.assignment2;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeAlgorithms {
    private static final int BOARD_SIZE = TicTacToeController.BOARD_SIZE;
    private static final int PLAYER_WEIGHT = -10;
    private static final int AI_WEIGHT = 10;

    public static boolean isMoveLeft(char[][] _board) {
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++)
                if (_board[row][col] == '_') return true;
        return false;
    }

    public static boolean isValidMove(char[][] _board, Move _move) {
        return _board[_move.row][_move.col] == '_';
    }

    public static boolean isValidMove(char[][] _board, int _row, int _col) {
        return _board[_row][_col] == '_';
    }

    private static int evaluateSequence(char... _info) {
        char ref = _info[0];
        for (char c : _info) {
            if (c != ref) return 0;
        }

        if (ref == TicTacToeController.player) return PLAYER_WEIGHT;
        else if (ref == TicTacToeController.opponent) return AI_WEIGHT;
        else return 0;
    }

    private static int evaluate(char[][] _board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            int val = evaluateSequence(_board[row][0], _board[row][1], _board[row][2]);
            if (val != 0) return val;
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            int val = evaluateSequence(_board[0][col], _board[1][col], _board[2][col]);
            if (val != 0) return val;
        }

        int diagonal = evaluateSequence(_board[0][0], _board[1][1], _board[2][2]);
        if (diagonal != 0) return diagonal;

        diagonal = evaluateSequence(_board[0][2], _board[1][1], _board[2][0]);
//        if (!isMoveLeft(_board) && diagonal == 0) return PLAYER_WEIGHT;

        return diagonal;
    }

    private static List<Move> getPossibleMoves(char[][] _board) {
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int k = 0; k < BOARD_SIZE; k++) {
                if (_board[i][k] == '_') moves.add(new Move(i, k));
            }
        }
        return moves;
    }

    public static Pair<Integer, Move> minMax(char[][] _board, boolean _is_max, int _depth, int _alpha, int _beta) {
        Move best_move = Move.emptyMove();
        int best_score = _is_max ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        if (!isMoveLeft(_board) || evaluate(_board) != 0) {
            return new Pair<>(evaluate(_board), best_move);
        }

        List<Move> moves = getPossibleMoves(_board);

        for (Move move : moves) {
            _board[move.getRow()][move.getCol()] = _is_max ? TicTacToeController.opponent : TicTacToeController.player;
            int score = minMax(_board, !_is_max, _depth + 1, _alpha, _beta).getKey();

            if (_is_max) {
                if (best_score < score) {
                    best_score = score - _depth * 10;
                    best_move = Move.copyMove(move);

                    _alpha = Math.max(_alpha, best_score);
                    _board[move.getRow()][move.getCol()] = '_';
                    if (_beta <= _alpha) break;
                }
            } else {
                if (best_score > score) {
                    best_score = score + _depth * 10;
                    best_move = Move.copyMove(move);

                    _beta = Math.min(_beta, best_score);
                    _board[move.getRow()][move.getCol()] = '_';
                    if (_beta <= _alpha) break;
                }
            }

            _board[move.getRow()][move.getCol()] = '_';
        }

        return new Pair<>(best_score, best_move);
    }

    public static Move findBestMove(char[][] _board) {
        Pair<Integer, Move> move = minMax(_board, true, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
//        System.out.println("Best move val: " + Integer.toString(move.getKey()));
        return move.getValue();
    }

    public static Move getRandomPosition(char[][] board) {
        List<Move> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Move move = new Move(i, j);
                if (TicTacToeAlgorithms.isValidMove(board, move)) {
                    emptyPositions.add(move);
                }
            }
        }

        if (!isMoveLeft(board)) {
            return Move.emptyMove();
        }

        int randomIndex = (int) (Math.random() * 100) % emptyPositions.size();
        return emptyPositions.get(randomIndex);
    }

    public static String checkWinState(char[][] _board) {
        int ev = evaluate(_board);
        if (ev == PLAYER_WEIGHT) return "Player";
        else if (ev == AI_WEIGHT) return "Opponent";
        else if (!isMoveLeft(_board)) return "Tie";
        else return "";
    }
}