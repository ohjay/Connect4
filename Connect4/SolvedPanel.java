package Connect4;

/**
 * The panel in which a user plays Connect4 against a computer of HARD difficulty.
 * (This is another way of saying that the computer will play perfectly.)
 * @author Owen Jow
 */
public class SolvedPanel extends VsComputerPanel {
    /**
     * Constructs a "solved" panel with a minimax depth of 10.
     */
    public SolvedPanel() {
        /* Implicit call to super() */
        bgId = 4;
        maxDepth = 9;
    }
}
