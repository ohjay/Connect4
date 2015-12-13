package Connect4;

import javax.swing.Timer;

/**
 * A game mode in which players can place two pieces instead of one.
 * In an attempt at balance, only a single piece can be placed on the first turn.
 * @author Owen Jow
 */
public class FourByTwoPanel extends TwoPlayerPanel {
    /**
     * We'll need to create a special board for this mode.
     * (The default board switches turns every time a piece is dropped.)
     */
    @Override
    public void activate() {
        requestFocus();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        timer = new Timer(1, this);
        timer.start();
        board = new FourByTwoBoard();
    }
}
