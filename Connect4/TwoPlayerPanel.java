package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel meant to handle human 1v1s on a standard-sized board.
 * @author Owen Jow
 */
public class TwoPlayerPanel extends ReguC4Panel {
    
    /**
     * Set up the mouse listener for a two-player game.
     */
    public TwoPlayerPanel() {
        mouseListener = new TwoPlayerMouseListener();
    }
    
    /**
     * A mouse listener for two-player "regular board" Connect4.
     */
    class TwoPlayerMouseListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (board.gameOver || board.toMMHighlighted) {
                Connect4.returnToMainMenu();
            } else if (board.interactivePiece != null && !board.isPieceFalling) {
                int nearestCol = ReguBoard.getNearestCol(MouseData.x - Piece.REG_WIDTH / 2);
                if (!board.isColumnFull(nearestCol)) {
                    board.addToColumn(board.interactivePiece, nearestCol);
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (!board.gameOver) {
                MouseData.x = evt.getX();
                if (MouseData.x >= Piece.REG_WIDTH / 2
                        && MouseData.x <= Connect4.WINDOW_LEN - Piece.REG_WIDTH / 2) {
                    if (!board.isPieceFalling) {
                        board.interactivePiece.setX(MouseData.x - Piece.REG_WIDTH / 2);
                    }
                }
                
                if (Board.TO_MM_RECT.contains(MouseData.x, evt.getY())) {
                    board.toMMHighlighted = true;
                } else {
                    board.toMMHighlighted = false;
                }
            }
        }
    }
}
