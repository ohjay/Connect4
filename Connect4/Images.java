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
            BLACK_PIECE = new ImageIcon(Images.class.getResource("/images/c4BlackPiece.png")).getImage(),
            GOLD_BACKGROUND_6 = new ImageIcon(Images.class.getResource("/images/gold_bg6.png")).getImage(),
            GOLD_BACKGROUND_5 = new ImageIcon(Images.class.getResource("/images/gold_bg5.png")).getImage(),
            GOLD_BACKGROUND_4 = new ImageIcon(Images.class.getResource("/images/gold_bg4.png")).getImage(),
            GOLD_BACKGROUND_2 = new ImageIcon(Images.class.getResource("/images/gold_bg2.png")).getImage();
    // Small board/piece images (33x33 and 36x36)
    static final Image SMALL_SQUARE = new ImageIcon(Images.class.getResource("/images/c4Square_small.png")).getImage(),
            SMALL_RED = new ImageIcon(Images.class.getResource("/images/c4RedPiece_small.png")).getImage(),
            SMALL_YELLOW = new ImageIcon(Images.class.getResource("/images/c4YellowPiece_small.png")).getImage(),
            SMALL_GREEN = new ImageIcon(Images.class.getResource("/images/c4GreenPiece_small.png")).getImage(),
            SMALL_MAGENTA = new ImageIcon(Images.class.getResource("/images/c4MagentaPiece_small.png")).getImage();
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
    // Creator message
    static final Image CREATOR_MSG = new ImageIcon(Images.class.getResource("/images/creatorMessage.png")).getImage();
    // Pre-game instruction messages
    static final Image VS_CPU_INSTRS = new ImageIcon(Images.class.getResource("/images/vsCompInstrs.png")).getImage(),
            VS_HUMAN_INSTRS = new ImageIcon(Images.class.getResource("/images/vsHumanInstrs.png")).getImage(),
            WARFARE_INSTRS = new ImageIcon(Images.class.getResource("/images/warfareInstrs.png")).getImage(),
            FOUR_BY_TWO_INSTRS = new ImageIcon(Images.class.getResource("/images/fourByTwoInstrs.png")).getImage(),
            REMOVAL_INSTRS = new ImageIcon(Images.class.getResource("/images/removalInstrs.png")).getImage();
}
