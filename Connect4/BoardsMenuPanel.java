package Connect4;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * This is a panel to represent the menu panel for the Boards special mode 
 * (in which the user is able to play on custom Connect Four boards).
 * @author Owen Jow
 */
public class BoardsMenuPanel extends SheetMenuPanel {
    private static final int MM_X = 41, MM_Y = 153, MM_WIDTH = 269, MM_HEIGHT = 24,
            BOARD_SEL_X = 380, A_Y = 248, B_Y = 356, C_Y = 464, BOARD_SEL_WIDTH = 176,
            BOARD_SEL_HEIGHT = 31; // MM = main menu, BOARD_SEL = board selection
    private static final Rectangle MM_RECT = new Rectangle(MM_X, MM_Y, MM_WIDTH, MM_HEIGHT),
            BOARD_A_RECT = new Rectangle(BOARD_SEL_X, A_Y, BOARD_SEL_WIDTH, BOARD_SEL_HEIGHT),
            BOARD_B_RECT = new Rectangle(BOARD_SEL_X, B_Y, BOARD_SEL_WIDTH, BOARD_SEL_HEIGHT),
            BOARD_C_RECT = new Rectangle(BOARD_SEL_X, C_Y, BOARD_SEL_WIDTH, BOARD_SEL_HEIGHT);
    
    public BoardsMenuPanel() {
        menuSheet = Images.BOARDS_MENU_SHEET;
        mouseListener = new BoardsMenuMouseListener();
    }
    
    class BoardsMenuMouseListener extends MouseAdapter {
        /**
         * Enters either the main menu or one of the three Boards game modes
         * (Board A, Board B, or Board C).
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                if (imgIndex == 1) { // main menu
                    Panels.layout.show(Panels.contentPanel, "mainMenu");
                    Panels.mainMenuPanel.activate();
                    Panels.currPanel = Panels.mainMenuPanel;
                } else {
                    Panels.layout.show(Panels.contentPanel, "display");
                    Panels.displayPanel.activate(Panels.currPanel, "boardsMenu", Images.CREATOR_MSG);
                    Panels.currPanel = Panels.displayPanel;
                }
            } 
        }
        
        public void mouseMoved(MouseEvent evt) {
            // Retrieve cursor coordinates
            int x = evt.getX();
            int y = evt.getY();
            
            if (MM_RECT.contains(x, y)) { imgIndex = 1; } 
            else if (BOARD_A_RECT.contains(x, y)) { imgIndex = 2; }
            else if (BOARD_B_RECT.contains(x, y)) { imgIndex = 3; }
            else if (BOARD_C_RECT.contains(x, y)) { imgIndex = 4; }
            else { imgIndex = 0; }
            
            repaint();
        }
    }
}
