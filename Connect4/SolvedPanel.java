package Connect4;

/**
 * The panel in which a user plays Connect4 against a computer of HARD difficulty.
 * (This is another way of saying that the computer will play well... but not perfectly.)
 * @author Owen Jow
 */
public class SolvedPanel extends VsComputerPanel {
    /**
     * Constructs a "solved" panel with a minimax depth of 10.
     * This means that the computer will always be looking 10 turns ahead.
     */
    public SolvedPanel() {
        /* Implicit call to super() */
        maxDepth = 10;
    }
}
