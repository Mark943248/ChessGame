package ChessGame.pieces;

public abstract class Piece {
    protected PieceColor color;

    public Piece(PieceColor color) {
        this.color = color;
    }

    public PieceColor getColor() {
        return color;
    };

    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board);

    public abstract String getSymbol();
}
