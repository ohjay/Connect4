package Connect4;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * The portal to the three special game modes. From this menu, the user
 * will be able to choose between four different options: Warfare, Four by Two,
 * Removal, and a return to the main menu.
 * @author Owen Jow
 */
public class SpecialMenuPanel extends SheetMenuPanel {
    private static final int MM_X = 169, MM_Y = 147, MM_WIDTH = 269, MM_HEIGHT = 24,
            WARF_X = 16, TWOFO_X = 211, REMO_X = 407, BOX_Y = 183, BOX_WIDTH = 184,
            BOX_HEIGHT = 298; // WARF = warfare, TWOFO = four by two, REMO = removal
    private static final Rectangle MM_RECT = new Rectangle(MM_X, MM_Y, MM_WIDTH, MM_HEIGHT),
            WARF_RECT = new Rectangle(WARF_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT),
            TWOFO_RECT = new Rectangle(TWOFO_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT),
            REMO_RECT = new Rectangle(REMO_X, BOX_Y, BOX_WIDTH, BOX_HEIGHT);
    
    public SpecialMenuPanel() {
        menuSheet = Images.SPECIAL_MENU_SHEET;
        mouseListener = new SpecialMouseListener();
    }
    
    class SpecialMouseListener extends MouseAdapter {
        /**
         * Enters either one of the three special game modes or the main menu.
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                if (imgIndex == 1) { // main menu
                    Panels.layout.show(Panels.contentPanel, "mainMenu");
                    Panels.mainMenuPanel.activate();
                    Panels.currPanel = Panels.mainMenuPanel;
                } else if (imgIndex == 2) { // Warfare
                    Panels.layout.show(Panels.contentPanel, "warfare");
                    Panels.warfarePanel.activate();
                    Panels.currPanel = Panels.warfarePanel;
                } else if (imgIndex == 3) { // Four by Two
                    Panels.layout.show(Panels.contentPanel, "fourByTwo");
                    Panels.fourByTwoPanel.activate();
                    Panels.currPanel = Panels.fourByTwoPanel;
                } else { // Removal
                    Panels.layout.show(Panels.contentPanel, "removal");
                    Panels.removalPanel.activate();
                    Panels.currPanel = Panels.removalPanel;
                }
            } 
        }
        
        /**
         * Highlights any icons under the cursor's current position.
         */
        public void mouseMoved(MouseEvent evt) {
            // Retrieve cursor coordinates
            int x = evt.getX();
            int y = evt.getY();
            
            if (MM_RECT.contains(x, y)) { imgIndex = 1; } 
            else if (WARF_RECT.contains(x, y)) { imgIndex = 2; }
            else if (TWOFO_RECT.contains(x, y)) { imgIndex = 3; }
            else if (REMO_RECT.contains(x, y)) { imgIndex = 4; }
            else { imgIndex = 0; }
            
            repaint();
        }
    }
}
