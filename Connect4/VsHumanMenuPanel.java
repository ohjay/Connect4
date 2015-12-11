package Connect4;

import java.awt.Image;
import java.awt.Rectangle;
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
            C_HEIGHT = 120, MM_X = 159, MM_Y = 97, MM_WIDTH = 269, MM_HEIGHT = 22;
    private static final Rectangle ONEC_RECT = new Rectangle(ONEC_LEFT_X, ONEC_TOP_Y, C_WIDTH, C_HEIGHT),
            TWOC_RECT = new Rectangle(0, TWOC_TOP_Y, C_WIDTH, C_HEIGHT),
            MM_RECT = new Rectangle(MM_X + 6, MM_Y + 7, MM_WIDTH, MM_HEIGHT);
    
    public VsHumanMenuPanel() {
        menuImages = new Image[] { Images.VS_HU_REG, Images.VS_HU_MM, Images.VS_HU_1C, Images.VS_HU_2C };
        mouseListener = new VsHuMenMouseListener();
    }
    
    public class VsHuMenMouseListener extends MouseAdapter {
        /**
         * Enters either a single-computer game, a networked game, or the main menu.
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                if (imgIndex == 1) { // the user wants to go to the main menu
                    Panels.layout.show(Panels.contentPanel, "mainMenu");
                    Panels.mainMenuPanel.activate();
                    Panels.currPanel = Panels.mainMenuPanel;
                } else if (imgIndex == 2) { // it's game time
                    Panels.layout.show(Panels.contentPanel, "twoPlayer");
                    Panels.twoPlayerPanel.activate();
                    Panels.currPanel = Panels.twoPlayerPanel;
                } else {
                    // No networked connection! Sorry, users
                    Panels.layout.show(Panels.contentPanel, "creatorMsg");
                    Panels.creatorMsgPanel.activate(Panels.currPanel, "vsHumanMenu");
                    Panels.currPanel = Panels.creatorMsgPanel;
                }
            }
        }
        
        /**
         * Highlights any icons over which the cursor is currently positioned.
         */
        public void mouseMoved(MouseEvent evt) {
            // Get the coordinates of the cursor in its current position
            int x = evt.getX();
            int y = evt.getY();
            
            // Check if the cursor is currently hovering over any of the icons
            if (ONEC_RECT.contains(x, y)) { imgIndex = 2; } 
            else if (TWOC_RECT.contains(x, y)) { imgIndex = 3; } 
            else if (MM_RECT.contains(x, y)) { imgIndex = 1; } 
            else { imgIndex = 0; }
            
            repaint();
        }
    }
}
