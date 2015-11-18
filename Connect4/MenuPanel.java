package Connect4;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * A basic menu panel that is abstract but no less important. Serves as an intermediary 
 * storage unit for useful menu variables and methods.
 * @author Owen Jow 
 */
public abstract class MenuPanel extends KPanel {
    protected Image[] menuImages;
    protected int imgIndex; // controls which menu image is currently being displayed
    
    /**
     * Activates and resets the panel (so that the image displayed upon load 
     * is always the default).
     */
    @Override
    public void activate() {
        super.activate();
        imgIndex = 0;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[imgIndex], 0, 0, null);
    }
}
