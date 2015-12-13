package Connect4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;

/**
 * A panel in which four players can duke it out on a double-sized board.
 *
 * Game logic is very similar to that of a regular two-player version;
 * the main differences are that the board is bigger and control rotates
 * among four players instead of two.
 * @author Owen Jow
 */
public class WarfarePanel extends KPanel {
    protected BigBoard board;
    protected int bgId = 3;
    
    @Override
    public void activate() {
        super.activate();
        board = new BigBoard();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw((Graphics2D) g, bgId);
    }
}
