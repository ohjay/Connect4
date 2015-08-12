package Connect4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * An abstract class for regular Connect4 gameplay (gameplay with a 7x7 board and two players).
 * @author Owen Jow
 */
public abstract class ReguC4Panel extends KPanel {
    protected ReguBoard reguBoard;
    protected int bgId = 6;
    
    protected ReguC4Panel() {
        mouseListener = new ReguC4MouseListener();
    }
    
    /**
     * Resets the regular Connect4 board in addition to the standard activation method.
     */
    @Override
    public void activate() {
        super.activate();
        reguBoard = new ReguBoard();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        requestFocus();
        
        if (reguBoard.isPieceFalling) {
            reguBoard.animateFallingPiece();
        }
        
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        reguBoard.draw((Graphics2D) g, bgId);
    }
    
    /**
     * A mouse listener for "regular board" Connect4.
     */
    public class ReguC4MouseListener extends MouseAdapter {
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
