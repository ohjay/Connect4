package Connect4;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * A collection of images relevant to Connect4.
 * The set includes a generic Connect4 square and pieces in shades 
 * of red, yellow, green, and magenta.
 */
public class Images {
    // All of the images assigned here, once and for all
    // Basic board/piece images
    static final Image square = new ImageIcon(Images.class.getResource("/images/c4Square.png")).getImage(), 
            redPiece = new ImageIcon(Images.class.getResource("/images/c4RedPiece.png")).getImage(),
            yellowPiece = new ImageIcon(Images.class.getResource("/images/c4YellowPiece.png")).getImage(),
            greenPiece = new ImageIcon(Images.class.getResource("/images/c4GreenPiece.png")).getImage(),
            magentaPiece = new ImageIcon(Images.class.getResource("/images/c4MagentaPiece.png")).getImage();
    // Main menu images
    static final Image mmReg = new ImageIcon(Images.class.getResource("/images/mainMenuRegular.png")).getImage(),
            mmVsCPU = new ImageIcon(Images.class.getResource("/images/mainMenuVsCPU.png")).getImage(),
            mmVsHuman = new ImageIcon(Images.class.getResource("/images/mainMenuVsHuman.png")).getImage(),
            mmSpecial = new ImageIcon(Images.class.getResource("/images/mainMenuSpecialModes.png")).getImage(),
            mmBoards = new ImageIcon(Images.class.getResource("/images/mainMenuBoards.png")).getImage();
    // VS Human menu images
    static final Image vsHuReg = new ImageIcon(Images.class.getResource("/images/vsHuMenu.png")).getImage(),
            vsHu1C = new ImageIcon(Images.class.getResource("/images/vsHuMen1C.png")).getImage(),
            vsHu2C = new ImageIcon(Images.class.getResource("/images/vsHuMen2C.png")).getImage(),
            backToMM1 = new ImageIcon(Images.class.getResource("/images/backToMM.png")).getImage(),
            backToMM2 = new ImageIcon(Images.class.getResource("/images/backToMMH.png")).getImage();
}
