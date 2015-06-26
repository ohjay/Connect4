package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel meant to handle human 1v1s on a standard-sized board.
 * @author Owen Jow
 */
public class TwoPlayerPanel extends ReguC4Panel {
    
    public TwoPlayerPanel() {
        super();
        mouseListener = new TwoPlayerPMouseListener();
    }
    
    /**
     * A mouse listener for two-player Connect4.
     */
    public class TwoPlayerPMouseListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (reguBoard.interactivePiece != null && !reguBoard.isPieceFalling) {
                int nearestCol = reguBoard.getNearestCol(MouseData.x);
                if (!reguBoard.isColumnFull(nearestCol)) {
                    reguBoard.addToColumn(reguBoard.interactivePiece, nearestCol);
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (evt.getX() <= Connect4.WINDOW_LEN - Piece.WIDTH) {
                MouseData.x = evt.getX();
                if (!reguBoard.isPieceFalling) {
                    reguBoard.interactivePiece.setX(MouseData.x);
                }
            }
        }
    }
}
