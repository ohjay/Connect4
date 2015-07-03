package Connect4;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VsCPUMenuPanel extends MenuPanel {
    private static final int MM_X = 167, MM_Y = 115, MM_WIDTH = 269, MM_HEIGHT = 21,
            EASY_X = 58, HARD_X = 328, REG_Y = 296, EXT_Y = 171, SQ_WIDTH = 219,
            EXT_HEIGHT = 343; // ext = extended
    private static final Rectangle MM_RECT = new Rectangle(MM_X, MM_Y, MM_WIDTH, MM_HEIGHT),
            EASY_REG_RECT = new Rectangle(EASY_X, REG_Y, SQ_WIDTH, SQ_WIDTH),
            EASY_EXT_RECT = new Rectangle(EASY_X, EXT_Y, SQ_WIDTH, EXT_HEIGHT),
            HARD_REG_RECT = new Rectangle(HARD_X, REG_Y, SQ_WIDTH, SQ_WIDTH),
            HARD_EXT_RECT = new Rectangle(HARD_X, EXT_Y, SQ_WIDTH, EXT_HEIGHT);
    
    public VsCPUMenuPanel() {
        menuImages = new Image[] { Images.vsCPUReg, Images.vsCPUEasy, 
                Images.vsCPUHard, Images.vsCPUMM };
        mouseListener = new VsCPUMouseListener();
    }
    
    public class VsCPUMouseListener extends MouseAdapter {
        /**
         * Enters either a game against an easy computer, a game against a hard computer, 
         * or the main menu.
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                if (imgIndex == 1) { // easy CPU
                    Panels.layout.show(Panels.contentPanel, "easy");
                    Panels.easyPanel.activate();
                    Panels.currPanel = Panels.easyPanel;
                } else if (imgIndex == 2) { // hard CPU
                    Panels.layout.show(Panels.contentPanel, "solved");
                    Panels.solvedPanel.activate();
                    Panels.currPanel = Panels.solvedPanel;
                } else { // main menu
                    Panels.layout.show(Panels.contentPanel, "mainMenu");
                    Panels.mainMenuPanel.activate();
                    Panels.currPanel = Panels.mainMenuPanel;
                }
            } 
        }
        
        /**
         * Highlights any icons under the cursor's current position.
         */
        public void mouseMoved(MouseEvent evt) {
            // Get the coordinates of the cursor in its current position
            int x = evt.getX();
            int y = evt.getY();
            
            if (EASY_REG_RECT.contains(x, y)) { imgIndex = 1; } 
            else if (HARD_REG_RECT.contains(x, y)) { imgIndex = 2; }
            else if (MM_RECT.contains(x, y)) { imgIndex = 3; }
            else if (!EASY_EXT_RECT.contains(x, y) && !HARD_EXT_RECT.contains(x, y)) {
                imgIndex = 0;
            }
            
            repaint();
        }
    }
}
