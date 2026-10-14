package ChessGame.pieces;

public class Knight extends Piece {
    public Knight(PieceColor color) {
        super(color);
    }

    public String getSymbol() {
        return color==PieceColor.WHITE ? "♘" : "♞";
    }

    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
        int rDiff = Math.abs(endRow - startRow);
        int cDiff = Math.abs(endCol - startCol);
        // L-shape: 2x1 or 1x2 (Knights can jump over pieces)
        return (rDiff == 2 && cDiff == 1 || cDiff == 2 && rDiff == 1);
    }
}
