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
    static final Image square = new ImageIcon(Images.class.getResource("/images/c4Square.png")).getImage(), 
            redPiece = new ImageIcon(Images.class.getResource("/images/c4RedPiece.png")).getImage(),
            yellowPiece = new ImageIcon(Images.class.getResource("/images/c4YellowPiece.png")).getImage(),
            greenPiece = new ImageIcon(Images.class.getResource("/images/c4GreenPiece.png")).getImage(),
            magentaPiece = new ImageIcon(Images.class.getResource("/images/c4MagentaPiece.png")).getImage();
}