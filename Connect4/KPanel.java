package Connect4;

import javax.swing.JPanel;

/**
 * An abstract representation of a panel, to be extended by all Connect4 panels.
 * Contains activation and deactivation methods that all panels will need to implement.
 * @author Owen Jow
 */
public abstract class KPanel extends JPanel {
    
    public void activate() {
        /* Override this! */
    }
    
    public void deactivate() {
        /* Override this too! */
    }
}
