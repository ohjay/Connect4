package Connect4;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * The most basic of JPanels; simply displays a splash screen graphic.
 * For use upon startup, while game data is being initialized.
 * @author Owen Jow
 */
public class SplashScreen extends JPanel {
    private static final Image SPLASH_SCREEN = 
        new ImageIcon(SplashScreen.class.getResource("/images/c4SplashScreen.png")).getImage();
    
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(SPLASH_SCREEN, 0, 0, null);
    }
}
