package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The panel in which a user plays Connect Four against a computer of easy difficulty.
 * @author Owen Jow
 */
public class EasyPanel extends ReguC4Panel {
    
    public EasyPanel() {
        mouseListener = new EasyMouseListener();
        bgId = 5;
    }
    
    class EasyMouseListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (board.interactivePiece != null && !board.isPieceFalling) {
                int nearestCol = ReguBoard.getNearestCol(MouseData.x);
                if (!board.isColumnFull(nearestCol)) {
                    board.addToColumn(board.interactivePiece, nearestCol);
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (evt.getX() <= Connect4.WINDOW_LEN - Piece.WIDTH) {
                MouseData.x = evt.getX();
                if (!board.isPieceFalling) {
                    board.interactivePiece.setX(MouseData.x);
                }
            }
        }
    }
}
