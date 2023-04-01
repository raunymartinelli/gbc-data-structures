package gbc.ds.assignment2;

public class Move {
    public final int row;
    public final int col;

    public Move(int _row, int _col) {
        this.row = _row;
        this.col = _col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public static Move emptyMove() {
        return new Move(-1, -1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move m) {
            return this.row == m.getRow() && this.col == m.getCol();
        } else return super.equals(obj);
    }

    public static Move copyMove(Move _move)
    {
        return new Move(_move.getRow(), _move.getCol());
    }
}
