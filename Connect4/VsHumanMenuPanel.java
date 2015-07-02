package Connect4;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The menu that serves as a gateway to any games that involve a regular battle between humans.
 * From here, the user can either transition to a single-computer game, 
 * a game played over the network, or the main menu.
 * @author Owen Jow
 */
public class VsHumanMenuPanel extends MenuPanel {
    private static final int ONEC_LEFT_X = 48, ONEC_TOP_Y = 168, TWOC_TOP_Y = 378, C_WIDTH = 555, 
            C_HEIGHT = 120, MM_X = 163, MM_Y = 101, MM_WIDTH = 267, MM_HEIGHT = 20;
    private static final Rectangle ONEC_RECT = new Rectangle(ONEC_LEFT_X, ONEC_TOP_Y, C_WIDTH, C_HEIGHT),
            TWOC_RECT = new Rectangle(0, TWOC_TOP_Y, C_WIDTH, C_HEIGHT),
            MM_RECT = new Rectangle(MM_X + 6, MM_Y + 7, MM_WIDTH, MM_HEIGHT);
    private boolean isMMHighlighted;
    
    public VsHumanMenuPanel() {
        menuImages = new Image[] { Images.vsHuReg, Images.vsHu1C, Images.vsHu2C };
        mouseListener = new VsHuMenMouseListener();
    }
    
    @Override
    public void activate() {
        super.activate();
        isMMHighlighted = false;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[imgIndex], 0, 0, null);
        g2.drawImage((isMMHighlighted) ? Images.backToMM2 : Images.backToMM1, MM_X, MM_Y, null);
    }
    
    public class VsHuMenMouseListener extends MouseAdapter {
        /**
         * Enters either a single-computer game, a networked game, or the main menu.
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                Panels.layout.show(Panels.contentPanel, "twoPlayer");
                Panels.twoPlayerPanel.activate();
                Panels.currPanel = Panels.twoPlayerPanel;
            } else if (isMMHighlighted) {
                Panels.currPanel.deactivate();
                Panels.layout.show(Panels.contentPanel, "mainMenu");
                Panels.mainMenuPanel.activate();
                Panels.currPanel = Panels.mainMenuPanel;
            }
        }
        
        /**
         * Highlights any icons over which the cursor is currently positioned.
         */
        public void mouseMoved(MouseEvent evt) {
            // Get the coordinates of the cursor in its current position
            int x = evt.getX();
            int y = evt.getY();
            
            if (ONEC_RECT.intersects(x, y, 1, 1)) {
                imgIndex = 1;
            } else if (TWOC_RECT.intersects(x, y, 1, 1)) {
                imgIndex = 2;
            } else if (MM_RECT.intersects(x, y, 1, 1)) {
                isMMHighlighted = true;
            } else {
                imgIndex = 0;
                isMMHighlighted = false;
            }
            
            repaint();
        }
    }
}
