package gbc.ds.assignment2;

import java.util.ArrayList;
import java.util.List;

public class TicTacToeAlgorithms {
    private static final int BOARD_SIZE = TicTacToeController.BOARD_SIZE;

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

    private static int evaluate(char[][] _board) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            if (_board[row][0] == _board[row][1] && _board[row][1] == _board[row][2]) {
                if (_board[row][0] == TicTacToeController.player) {
                    return 10;
                } else if (_board[row][0] == TicTacToeController.opponent) {
                    return -10;
                }
            }
        }

        for (int col = 0; col < BOARD_SIZE; col++) {
            if (_board[0][col] == _board[1][col] && _board[1][col] == _board[2][col]) {
                if (_board[0][col] == TicTacToeController.player) {
                    return 10;
                } else if (_board[0][col] == TicTacToeController.opponent) {
                    return -10;
                }
            }
        }

        if (_board[0][0] == _board[1][1] && _board[1][1] == _board[2][2]) {
            if (_board[0][0] == TicTacToeController.player) return 10;
            else if (_board[0][0] == TicTacToeController.opponent) return -10;
        }

        if (_board[0][2] == _board[1][1] && _board[1][1] == _board[2][0]) {
            if (_board[0][2] == TicTacToeController.player) return 10;
            else if (_board[0][2] == TicTacToeController.opponent) return -10;
        }

        return 0;
    }

    private static int minMax(char[][] _board, boolean _is_max) {
        int score = evaluate(_board);

        if (!isMoveLeft(_board))
            return score;

        int best = _is_max ? -1000 : 1000;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int k = 0; k < BOARD_SIZE; k++) {
                if (_board[i][k] == '_') {
                    _board[i][k] = _is_max ? TicTacToeController.player : TicTacToeController.opponent;
                    if (_is_max)
                        best = Math.max(best, minMax(_board, false));
                    else
                        best = Math.min(best, minMax(_board, true));
                    _board[i][k] = '_';
                }
            }
        }

        return best;
    }

    public static Move findBestMove(char[][] _board, int _lr, int _lc) {
        int bestVal = -1000;
        Move bestMove = new Move(-1, -1);

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int k = 0; k < BOARD_SIZE; k++) {
                if (_board[i][k] == '_') {
                    _board[i][k] = TicTacToeController.opponent;
                    int moveVal = minMax(_board, true);
                    moveVal *= (_lr - i == 0 ? 1 : _lr - i) * (_lc - k == 0 ? 1 : _lc - k);

                    int score = evaluate(_board);
                    if (score == -10) {
                        return new Move(i, k);
                    }

                    _board[i][k] = '_';

                    if (moveVal > bestVal) {
                        bestMove = new Move(i, k);
                        bestVal = moveVal;
                    }
                }
            }
        }

        return bestMove;
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
        if (ev == 10) return "Player";
        else if (ev == -10) return "Opponent";
        else if (!isMoveLeft(_board)) return "Tie";
        else return "";
    }
}