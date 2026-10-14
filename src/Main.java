import ChessGame.board.Board;
import ChessGame.pieces.Piece;
import ChessGame.pieces.PieceColor;

import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {
    private final JButton[][] buttons = new JButton[8][8];
    private final Board board;

    private PieceColor currentTurnColor = PieceColor.WHITE;
    private int selectedRow = -1;
    private int selectedCol = -1;

    private final Color LIGHT_TILE = new Color(240, 217, 181);
    private final Color DARK_TILE = new Color(181, 136, 99);
    private final Color HIGHLIGHTCOLOR = new Color(186, 202, 68);
    private final Color CHECKCOLOR = new Color(0xDB0913);

    public Main() {
        this.board = new Board();
        setTitle("Java Chess Game - current turn White");
        setSize(650, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center on screen
        setLayout(new GridLayout(8, 8)); // 8x8 layout grid

        initializeBoardUI();
        updateBoardUI();
    }

    private void initializeBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JButton button = new JButton();
                button.setFocusPainted(false);
                button.setFont(new Font("Serif", Font.PLAIN, 44)); // Large font for chess Unicode symbols

                if ((row + col) % 2 == 0) {
                    button.setBackground(LIGHT_TILE);
                } else {
                    button.setBackground(DARK_TILE);
                }

                final int r = row;
                final int c = col;

                button.addActionListener(e -> handleTileClick(r, c));

                buttons[r][c] = button;
                add(button);
            }
        }
    }

    // Handles button clicks
    private void handleTileClick(int row, int col) {
        Piece clickedPiece = board.getPiece(row, col);

        // Case 1: No piece currently selected -> Select if it belongs to current player
        if (selectedRow == -1 && selectedCol == -1) {
            if (clickedPiece != null && clickedPiece.getColor() == currentTurnColor) {
                selectedRow = row;
                selectedCol = col;
                highlightTile(row, col);
            }
            return;
        }

        // Case 2: Clicked the SAME already-selected tile -> Deselect and stop
        if (selectedRow == row && selectedCol == col) {
            clearSelection();
            return;
        }

        // Case 3: Clicked ANOTHER piece of the same color -> Switch selection
        if (clickedPiece != null && clickedPiece.getColor() == currentTurnColor) {
            clearSelection();
            selectedRow = row;
            selectedCol = col;
            highlightTile(row, col);
            return;
        }

        // Case 4: Attempting to move selected piece to target tile (row, col)
        boolean moveSuccessful = board.movePiece(selectedRow, selectedCol, row, col);

        if (moveSuccessful) {
            // Switch turn
            currentTurnColor = (currentTurnColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

            clearSelection();
            updateBoardUI();

            // Game Over Checks
            if (board.isCheckmate(currentTurnColor)) {
                PieceColor winner = (currentTurnColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
                setTileRedInCheck(currentTurnColor);
                setTitle("CHECKMATE! " + winner + " Wins!");

                JOptionPane.showMessageDialog(this,
                        "Checkmate! " + winner + " wins the game!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (board.isStalemate(currentTurnColor)) {
                setTitle("STALEMATE! Game is a Draw.");

                JOptionPane.showMessageDialog(this,
                        "Stalemate! No legal moves available. It's a draw!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE);

            } else if (board.isKingInCheck(currentTurnColor)) {
                setTileRedInCheck(currentTurnColor);
                setTitle("Java Chess Game — Turn: " + currentTurnColor + " [CHECK!]");
            } else {
                setTitle("Java Chess Game — Turn: " + currentTurnColor);
            }
        } else {
            clearSelection();
            updateBoardUI();
        }
    }

    // Red background in check
    private void setTileRedInCheck(PieceColor color) {
        int [] kingPosition = board.findKing(color);
        if (kingPosition != null) {
            buttons[kingPosition[0]][kingPosition[1]].setBackground(CHECKCOLOR);
        }
    }

    // Highlights the selected piece
    private void highlightTile(int row, int col) {
        buttons[row][col].setBackground(HIGHLIGHTCOLOR);
    }

    private void clearSelection() {
        selectedRow = -1;
        selectedCol = -1;
        resetTilesColor();
    }

    private void resetTilesColor() {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                if ((r + c) % 2 == 0) {
                    buttons[r][c].setBackground(LIGHT_TILE);
                } else {
                    buttons[r][c].setBackground(DARK_TILE);
                }
            }
        }
    }


    // Refresh display to mirror current backend Board state
    public void updateBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board.getPiece(row, col);
                if (piece != null) {
                    buttons[row][col].setText(piece.getSymbol());
                } else {
                    buttons[row][col].setText("");
                }
            }
        }
    }

    void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main gui = new Main();
            gui.setVisible(true);
        });
    }
}