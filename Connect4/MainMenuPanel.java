package Connect4;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javafx.scene.shape.Circle;

/**
 * The main menu panel for the game. Includes portals to four different submenus.
 * @author Owen Jow
 */
public class MainMenuPanel extends MenuPanel {
    private static final int RADIUS = 93, LEFT_CENTER_X = 176, RIGHT_CENTER_X = 426, 
            TOP_CENTER_Y = 219, BOTTOM_CENTER_Y = 450;
    private static final Circle VS_CPU_CIRC = new Circle(LEFT_CENTER_X, TOP_CENTER_Y, RADIUS),
            VS_HU_CIRC = new Circle(RIGHT_CENTER_X, TOP_CENTER_Y, RADIUS),
            SPECIAL_CIRC = new Circle(LEFT_CENTER_X, BOTTOM_CENTER_Y, RADIUS),
            BOARDS_CIRC = new Circle(RIGHT_CENTER_X, BOTTOM_CENTER_Y, RADIUS);
    
    public MainMenuPanel() {
        menuImages = new Image[] { Images.mmReg, Images.mmVsCPU, Images.mmVsHuman,
                Images.mmSpecial, Images.mmBoards };
        mouseListener = new MMMouseListener();
    }
    
    public class MMMouseListener extends MouseAdapter {
        /**
         * Sends the user on his/her way to the next screen, if one of the icons is selected.
         * If none of the icons are selected, clicking does nothing.
         */
        public void mouseClicked(MouseEvent evt) {
            if (imgIndex != 0) {
                Panels.currPanel.deactivate();
                switch (imgIndex) {
                    case 1: 
                        Panels.layout.show(Panels.contentPanel, "vsCPUMenu");
                        Panels.vsCPUMenuPanel.activate();
                        Panels.currPanel = Panels.vsCPUMenuPanel;
                        break;
                    case 2:
                        Panels.layout.show(Panels.contentPanel, "vsHumanMenu");
                        Panels.vsHumanMenuPanel.activate();
                        Panels.currPanel = Panels.vsHumanMenuPanel;
                        break;
                    case 3:
                        Panels.layout.show(Panels.contentPanel, "specialMenu");
                        Panels.specialMenuPanel.activate();
                        Panels.currPanel = Panels.specialMenuPanel;
                        break;
                    default:
                        Panels.layout.show(Panels.contentPanel, "boardsMenu");
                        Panels.boardsMenuPanel.activate();
                        Panels.currPanel = Panels.boardsMenuPanel;
                        break;
                }
            }
        }
        
        /**
         * If the mouse is hovering over any of the portal icons, magnify that portal icon.
         */
        public void mouseMoved(MouseEvent evt) {
            // Get the coordinates of the cursor in its current position
            int x = evt.getX();
            int y = evt.getY();
            
            if (VS_CPU_CIRC.intersects(x, y, 1, 1)) {
                imgIndex = 1;
            } else if (VS_HU_CIRC.intersects(x, y, 1, 1)) {
                imgIndex = 2;
            } else if (SPECIAL_CIRC.intersects(x, y, 1, 1)) {
                imgIndex = 3;
            } else if (BOARDS_CIRC.intersects(x, y, 1, 1)) {
                imgIndex = 4;
            } else {
                imgIndex = 0;
            }
            
            repaint();
        }
    }
}
