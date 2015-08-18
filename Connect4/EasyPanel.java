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
            if (reguBoard.interactivePiece != null && !reguBoard.isPieceFalling) {
                int nearestCol = ReguBoard.getNearestCol(MouseData.x);
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
