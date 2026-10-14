package ChessGame.pieces;

public class King extends Piece{
    public King(PieceColor color) {
        super(color);
    }

    public String getSymbol() {
        return color==PieceColor.WHITE ? "♔" : "♚";
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int rDiff = Math.abs(endRow - startRow);
        int cDiff = Math.abs(endCol - startCol);
        return rDiff <= 1 && cDiff <= 1 && (rDiff + cDiff > 0);
    }
}
