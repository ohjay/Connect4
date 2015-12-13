package Connect4;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import javax.swing.Timer;

/**
 * An abstract representation of a panel, to be extended by all Connect4 panels.
 * Contains activation and deactivation methods that all panels will need to implement.
 * @author Owen Jow
 */
public abstract class KPanel extends JPanel implements ActionListener {
    protected Timer timer;
    protected MouseAdapter mouseListener;
    
    public void actionPerformed(ActionEvent evt) {
        /* Override this if you want anything to happen regularly! */
    }
    
    /**
     * Activates this panel, adding mouse listeners and starting the timer.
     */
    public void activate() {
        requestFocus();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        timer = new Timer(1, this);
        timer.start();
    }
    
    /**
     * Deactivates this panel, removing mouse listeners and stopping the timer.
     */
    public void deactivate() {
        removeMouseListener(mouseListener);
        removeMouseMotionListener(mouseListener);
        timer.stop();
    }
}
