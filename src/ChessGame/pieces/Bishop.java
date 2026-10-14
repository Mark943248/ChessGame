package ChessGame.pieces;

public class Bishop extends Piece {
    public Bishop(PieceColor color) {
        super(color);
    }

    public String getSymbol() {
        return color==PieceColor.WHITE ? "♗" : "♝";
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
       if (Math.abs(endRow - startRow) != Math.abs(endCol - startCol)) return false; // moves diagnoally
       return Rook.isPathClear(startRow, startCol, endRow, endCol, board);
    }
}
