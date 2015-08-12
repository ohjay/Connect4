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
            magentaPiece = new ImageIcon(Images.class.getResource("/images/c4MagentaPiece.png")).getImage(),
            purplePiece = new ImageIcon(Images.class.getResource("/images/c4PurplePiece.png")).getImage(),
            blackPiece = new ImageIcon(Images.class.getResource("/images/c4BlackPiece.png")).getImage(),
            cyanPiece = new ImageIcon(Images.class.getResource("/images/c4CyanPiece.png")).getImage(),
            grayPiece = new ImageIcon(Images.class.getResource("/images/c4GrayPiece.png")).getImage(),
            lightGreenPiece = new ImageIcon(Images.class.getResource("/images/c4LightGreenPiece.png")).getImage(),
            goldBackground6 = new ImageIcon(Images.class.getResource("/images/gold_bg6.png")).getImage(),
            goldBackground5 = new ImageIcon(Images.class.getResource("/images/gold_bg5.png")).getImage(),
            goldBackground4 = new ImageIcon(Images.class.getResource("/images/gold_bg4.png")).getImage(),
            goldBackground3 = new ImageIcon(Images.class.getResource("/images/gold_bg3.png")).getImage(),
            goldBackground2 = new ImageIcon(Images.class.getResource("/images/gold_bg2.png")).getImage(),
            goldBackground1 = new ImageIcon(Images.class.getResource("/images/gold_bg1.png")).getImage();
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
            vsHuMM = new ImageIcon(Images.class.getResource("/images/vsHuMMH.png")).getImage();
    // VS CPU menu images
    static final Image vsCPUReg = new ImageIcon(Images.class.getResource("/images/vsCPUReg.png")).getImage(),
            vsCPUEasy = new ImageIcon(Images.class.getResource("/images/vsCPUEasy.png")).getImage(),
            vsCPUHard = new ImageIcon(Images.class.getResource("/images/vsCPUHard.png")).getImage(),
            vsCPUMM = new ImageIcon(Images.class.getResource("/images/vsCPUMM.png")).getImage();
}
