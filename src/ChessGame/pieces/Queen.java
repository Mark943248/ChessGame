package ChessGame.pieces;

public class Queen extends Piece {
    public Queen(PieceColor color) {
        super(color);
    }

    public String getSymbol() {
        return color==PieceColor.WHITE ? "♕" : "♛";
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        boolean straight = (startRow == endRow || startCol == endCol);
        boolean diagonal = (Math.abs(endRow - startRow) == Math.abs(endCol - startCol));
        if (!straight && !diagonal) return false;
        return Rook.isPathClear(startRow, startCol, endRow, endCol, board);
    }
}
