package Connect4;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * In contrast to the basic MenuPanel, a SheetMenuPanel is an abstract class that delivers 
 * common logic for menus that use sheets as part of their underlying implementation. 
 * (Sheets: where different menu images are stacked side-by-side in the same overall file.) 
 * The primary reason to do this is to compress the size of the final .jar package.
 * 
 * This class includes variables/methods for drawing such menu panels and handling state-switches.
 * @author Owen Jow 
 */
public abstract class SheetMenuPanel extends KPanel {
    protected Image menuSheet;
    protected int imgIndex; // still controls which image is currently being displayed
    
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int sheetOffset = imgIndex * Connect4.WINDOW_LEN;
        g2.drawImage(menuSheet, 0, 0, Connect4.WINDOW_LEN, Connect4.WINDOW_WIDTH, 
            sheetOffset, 0, sheetOffset + Connect4.WINDOW_LEN, Connect4.WINDOW_WIDTH, null);
    }
}
