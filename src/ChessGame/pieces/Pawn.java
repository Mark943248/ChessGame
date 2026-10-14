package ChessGame.pieces;

public class Pawn extends Piece {
    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public String getSymbol() {
        return color == PieceColor.WHITE ? "♙" : "♟";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int direction = (color == PieceColor.WHITE) ? -1 : 1; // White moves up (-1 row), Black moves down (+1 row)
        int startingRow = (color == PieceColor.WHITE) ? 6 : 1; // White starts at row(6) and black row(1)
        // Basic 1-step forward move (ignoring captures for now)
        if (startCol == endCol && endRow == startRow + direction && board[endRow][endCol] == null) return true;

        // 2 step intital move
        if (startCol == endCol && startRow == startingRow && endRow == startRow + (2 * direction)
                && board[startRow + direction][endCol] == null && board[endRow][endCol] == null) {
            return true;
        }

        // Diagonal capture
        if (Math.abs(endCol - startCol) == 1 && endRow == startRow + direction && board[endRow][endCol] != null && board[endRow][endCol].getColor() != this.color) {
            return true;
        }
        return false;

    }

}
