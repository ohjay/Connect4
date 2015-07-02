package Connect4;

import java.awt.Point;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public abstract class MenuPanel extends KPanel {
    protected Image[] menuImages;
    protected int imgIndex; // controls which menu image is currently being displayed
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(menuImages[imgIndex], 0, 0, null);
    }
}
