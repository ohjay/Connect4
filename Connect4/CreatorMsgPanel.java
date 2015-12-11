package Connect4;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The message to be displayed to users
 * after they try to access something that doesn't exist.
 * @author Owen Jow
 */
public class CreatorMsgPanel extends KPanel {
    private KPanel prevPanel;
    private String prevPanelStr;
    
    /**
     * Constructor. Creates a mouse listener for the panel.
     */
    public CreatorMsgPanel() {
        mouseListener = new CMPMouseListener();
    }
    
    /**
     * Every time a creator message screen is activated, 
     * it needs a reference to the activating panel so that it can
     * eventually return to that panel.
     */
    public void activate(KPanel prevPanel, String prevPanelStr) {
        this.prevPanel = prevPanel;
        this.prevPanelStr = prevPanelStr;
        
        requestFocus();
        addMouseListener(mouseListener);
    }
    
    @Override
    public void deactivate() {
        removeMouseListener(mouseListener);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(Images.CREATOR_MSG_SCREEN, 0, 0, null);
    }
    
    public class CMPMouseListener extends MouseAdapter {
        /**
         * If the user clicks at all, we should return to the previous screen.
         */
        public void mouseClicked(MouseEvent evt) {
            deactivate();
            Panels.layout.show(Panels.contentPanel, prevPanelStr);
            prevPanel.activate();
            Panels.currPanel = prevPanel;
        }
    }
}
