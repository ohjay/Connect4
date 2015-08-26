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
    static final Image SQUARE = new ImageIcon(Images.class.getResource("/images/c4Square.png")).getImage(), 
            RED_PIECE = new ImageIcon(Images.class.getResource("/images/c4RedPiece.png")).getImage(),
            YELLOW_PIECE = new ImageIcon(Images.class.getResource("/images/c4YellowPiece.png")).getImage(),
            GREEN_PIECE = new ImageIcon(Images.class.getResource("/images/c4GreenPiece.png")).getImage(),
            MAGENTA_PIECE = new ImageIcon(Images.class.getResource("/images/c4MagentaPiece.png")).getImage(),
            PURPLE_PIECE = new ImageIcon(Images.class.getResource("/images/c4PurplePiece.png")).getImage(),
            BLACK_PIECE = new ImageIcon(Images.class.getResource("/images/c4BlackPiece.png")).getImage(),
            CYAN_PIECE = new ImageIcon(Images.class.getResource("/images/c4CyanPiece.png")).getImage(),
            GRAY_PIECE = new ImageIcon(Images.class.getResource("/images/c4GrayPiece.png")).getImage(),
            LIGHT_GREEN_PIECE = new ImageIcon(Images.class.getResource("/images/c4LightGreenPiece.png")).getImage(),
            GOLD_BACKGROUND_6 = new ImageIcon(Images.class.getResource("/images/gold_bg6.png")).getImage(),
            GOLD_BACKGROUND_5 = new ImageIcon(Images.class.getResource("/images/gold_bg5.png")).getImage(),
            GOLD_BACKGROUND_4 = new ImageIcon(Images.class.getResource("/images/gold_bg4.png")).getImage(),
            GOLD_BACKGROUND_3 = new ImageIcon(Images.class.getResource("/images/gold_bg3.png")).getImage(),
            GOLD_BACKGROUND_2 = new ImageIcon(Images.class.getResource("/images/gold_bg2.png")).getImage(),
            GOLD_BACKGROUND_1 = new ImageIcon(Images.class.getResource("/images/gold_bg1.png")).getImage();
    // Main menu images
    static final Image MM_REG = new ImageIcon(Images.class.getResource("/images/mainMenuRegular.png")).getImage(),
            MM_VS_CPU = new ImageIcon(Images.class.getResource("/images/mainMenuVsCPU.png")).getImage(),
            MM_VS_HUMAN = new ImageIcon(Images.class.getResource("/images/mainMenuVsHuman.png")).getImage(),
            MM_SPECIAL = new ImageIcon(Images.class.getResource("/images/mainMenuSpecialModes.png")).getImage(),
            MM_BOARDS = new ImageIcon(Images.class.getResource("/images/mainMenuBoards.png")).getImage();
    // VS Human menu images
    static final Image VS_HU_REG = new ImageIcon(Images.class.getResource("/images/vsHuMenu.png")).getImage(),
            VS_HU_1C = new ImageIcon(Images.class.getResource("/images/vsHuMen1C.png")).getImage(),
            VS_HU_2C = new ImageIcon(Images.class.getResource("/images/vsHuMen2C.png")).getImage(),
            VS_HU_MM = new ImageIcon(Images.class.getResource("/images/vsHuMMH.png")).getImage();
    // VS CPU menu images
    static final Image VS_CPU_REG = new ImageIcon(Images.class.getResource("/images/vsCPUReg.png")).getImage(),
            VS_CPU_EASY = new ImageIcon(Images.class.getResource("/images/vsCPUEasy.png")).getImage(),
            VS_CPU_HARD = new ImageIcon(Images.class.getResource("/images/vsCPUHard.png")).getImage(),
            VS_CPU_MM = new ImageIcon(Images.class.getResource("/images/vsCPUMM.png")).getImage();
    // Menu sheets (a late discovery)
    static final Image SPECIAL_MENU_SHEET = new ImageIcon(Images.class.getResource("/images/specialMenuSheet.png")).getImage(),
            BOARDS_MENU_SHEET = new ImageIcon(Images.class.getResource("/images/boardsMenuSheet.png")).getImage();
}
