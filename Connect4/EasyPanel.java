package Connect4;

/**
 * The panel in which a user plays Connect Four against a computer of easy difficulty.
 * (This means that the computer will not look very many turns into the future.)
 * @author Owen Jow
 */
public class EasyPanel extends VsComputerPanel {
    /**
     * Constructs an "easy" panel. 
     * Every time, the computer will look six turns into the future.
     */
    public EasyPanel() {
        /* Implicit call to super() */
        bgId = 5;
        maxDepth = 6;
    }
}
