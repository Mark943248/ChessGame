package ChessGame.pieces;

public class Rook extends Piece {

     public Rook(PieceColor color) {
         super(color);
     }

     public String getSymbol() {
         return color==PieceColor.WHITE ? "♖" : "♜";
     }

     public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, Piece[][] board) {
         if (startRow != endRow && startCol != endCol)  {return false;} // moves in a straight line not diagnol movment
         return isPathClear(startRow, startCol, endRow, endCol, board);
     }

     // checks if the path is clear to move
     public static boolean isPathClear(int startRow, int startCol, int endRow, int endCol, Piece[][] b) {
         int rDir = Integer.compare(endRow, startRow);
         int cDir = Integer.compare(endCol, startCol);

         int rowStep = startRow + rDir;
         int colStep = startCol + cDir;

         while (rowStep != endRow || colStep != endCol) {
             if (b[rowStep][colStep] != null) return false; // Path blocked

             // Only increment if we haven't reached the destination on that axis
             if (rowStep != endRow) rowStep += rDir;
             if (colStep != endCol) colStep += cDir;
         }

         return true;
     }
}
