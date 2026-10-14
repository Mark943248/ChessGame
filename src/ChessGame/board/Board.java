package ChessGame.board;

import ChessGame.pieces.*;

public class Board {
    private Piece[][] grid = new Piece[8][8];

    public Board() {
        resetBoard();
    }

    public void resetBoard() {
        // Place black & white Pawns
        for (int col = 0; col < 8; col++) {
            grid[1][col] = new Pawn(PieceColor.BLACK);
            grid[6][col] = new Pawn(PieceColor.WHITE);
        }
        // Black Major pieces on (Row0)
        grid[0][0] = new Rook(PieceColor.BLACK);
        grid[0][1] = new Knight(PieceColor.BLACK);
        grid[0][2] = new Bishop(PieceColor.BLACK);
        grid[0][3] = new Queen(PieceColor.BLACK);
        grid[0][4] = new King(PieceColor.BLACK);
        grid[0][5] = new Bishop(PieceColor.BLACK);
        grid[0][6] = new Knight(PieceColor.BLACK);
        grid[0][7] = new Rook(PieceColor.BLACK);

        // White Major Pieces on (Row 7)
        grid[7][0] = new Rook(PieceColor.WHITE);
        grid[7][1] = new Knight(PieceColor.WHITE);
        grid[7][2] = new Bishop(PieceColor.WHITE);
        grid[7][3] = new Queen(PieceColor.WHITE);
        grid[7][4] = new King(PieceColor.WHITE);
        grid[7][5] = new Bishop(PieceColor.WHITE);
        grid[7][6] = new Knight(PieceColor.WHITE);
        grid[7][7] = new Rook(PieceColor.WHITE);
    }

    public Piece getPiece(int row, int col) {
        return grid[row][col];
    }

    // finds position of the king to determine if check
    public int[] findKing(PieceColor color) {
        for (int row = 0; row < 8; row++) {
            for(int col = 0; col < 8; col++) {
                Piece piece = grid[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    // determines if king in check
    public boolean isKingInCheck(PieceColor color) {
        int[] kingPosition = findKing(color);
        if (kingPosition == null) return false;

        PieceColor enemyColor = (color == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                Piece enemy = grid[r][c];
                if (enemy != null && enemy.getColor() == enemyColor) {
                    if (enemy.isValidMove(r, c, kingPosition[0], kingPosition[1], grid)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }



    public boolean movePiece(int startRow, int startCol, int endRow, int endCol) {
        Piece source = grid[startRow][startCol];
        Piece target = grid[endRow][endCol];

        if (source == null) return false;
        if (target != null && target.getColor() == source.getColor()) return false;
        if (!source.isValidMove(startRow, startCol, endRow, endCol, grid)) return false;

        // Simulate move to test if king is left in check
        grid[endRow][endCol] = source;
        grid[startRow][startCol] = null;

        if (isKingInCheck(source.getColor())) {
            // Undo move if illegal (leaves king in check)
            grid[startRow][startCol] = source;
            grid[endRow][endCol] = target;
            return false;
        }

        return true;
    }

    public boolean hasValidMoves(PieceColor color) {
        for (int startRow = 0; startRow < 8; startRow++) {
            for (int startCol = 0; startCol < 8; startCol++) {
                Piece piece = grid[startRow][startCol];
                if (piece != null && piece.getColor() == color) {
                    for (int endRow = 0; endRow < 8; endRow++) {
                        for (int endCol = 0; endCol < 8; endCol++) {
                            if (piece.isValidMove(startRow, startCol, endRow, endCol, grid)) {
                                Piece target = grid[endRow][endCol];
                                // Prevent capturing friendly pieces during simulation
                                if (target != null && target.getColor() == color) {
                                    continue;
                                }
                                // Simulate the move
                                grid[endRow][endCol] = piece;
                                grid[startRow][startCol] = null;

                                boolean kingSafe = !isKingInCheck(color);

                                // Undo the simulated move
                                grid[startRow][startCol] = piece;
                                grid[endRow][endCol] = target;

                                // Found at least 1 legal move!
                                if (kingSafe) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isCheckmate(PieceColor color) {
        return isKingInCheck(color) && !hasValidMoves(color);
    }

    public boolean isStalemate(PieceColor color) {
        return !isKingInCheck(color) && !hasValidMoves(color);
    }

}
