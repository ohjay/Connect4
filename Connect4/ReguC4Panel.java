package Connect4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;

/**
 * An abstract class for regular Connect4 gameplay (gameplay with a 7x7 board and two players).
 * @author Owen Jow
 */
public abstract class ReguC4Panel extends KPanel implements ActionListener {
    private Timer timer;
    protected MouseAdapter mouseListener;
    protected ReguBoard reguBoard;
    
    /**
     * Constructs a regular Connect4 panel. 
     * Subclasses should always define their own constructors and both reuse this behavior
     * AND assign their own mouse listeners to the member variable MOUSE_LISTENER.
     */
    public ReguC4Panel() {
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
        reguBoard.draw((Graphics2D) g);
    }
    
    /**
     * Activates this panel, adding mouse listeners and starting the timer.
     */
    @Override
    public void activate() {
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        timer = new Timer(1, this);
        timer.start();
    }
    
    /**
     * Deactivates this panel, removing mouse listeners and stopping the timer.
     */
    @Override
    public void deactivate() {
        removeMouseListener(mouseListener);
        removeMouseMotionListener(mouseListener);
        timer.stop();
    }
}
