package Connect4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

/**
 * An abstract class for regular Connect4 gameplay (gameplay with a 7x7 board and two players).
 * @author Owen Jow
 */
public abstract class ReguC4Panel extends KPanel {
    protected ReguBoard board;
    protected int bgId = 4;
    
    /**
     * Resets the regular Connect4 board in addition to the standard activation method.
     */
    @Override
    public void activate() {
        super.activate();
        board = new ReguBoard();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (board.isPieceFalling) {
            board.animateFallingPiece();
        }
        
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw((Graphics2D) g, bgId);
    }
}
