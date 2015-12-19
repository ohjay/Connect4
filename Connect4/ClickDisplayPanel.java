package Connect4;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * A panel that exists purely for display. When clicked, the game will transition
 * to another screen (that would have been specified upon activation).
 * @author Owen Jow
 */
public class ClickDisplayPanel extends KPanel {
    private KPanel nextPanel;
    private String nextPanelStr;
    private Image displayImg;
    
    /**
     * Constructor. Creates a mouse listener for the panel.
     */
    public ClickDisplayPanel() {
        mouseListener = new CDPMouseListener();
    }
    
    /**
     * Every time a click display screen is activated, 
     * it needs a reference to the next panel so that it can
     * eventually transition to that panel.
     */
    public void activate(KPanel nextPanel, String nextPanelStr, Image displayImg) {
        this.nextPanel = nextPanel;
        this.nextPanelStr = nextPanelStr;
        this.displayImg = displayImg;
        
        requestFocus();
        addMouseListener(mouseListener);
    }
    
    @Override
    public void deactivate() {
        removeMouseListener(mouseListener);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(displayImg, 0, 0, null);
    }
    
    public class CDPMouseListener extends MouseAdapter {
        /**
         * If the user clicks at all, we should move on to the next screen.
         */
        public void mouseClicked(MouseEvent evt) {
            deactivate();
            Panels.layout.show(Panels.contentPanel, nextPanelStr);
            nextPanel.activate();
            Panels.currPanel = nextPanel;
        }
    }
}
