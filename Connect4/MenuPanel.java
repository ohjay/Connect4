package Connect4;

import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import javax.swing.JPanel;
import javax.swing.Timer;

public abstract class MenuPanel extends JPanel implements ActionListener {
    protected Image[] menuImages;
    private int imgIndex;
    private Timer timer;
    private MouseListener ml;
    
    public void actionPerformed(ActionEvent evt) {
        
    }
    
    public class MouseListener extends MouseAdapter {
        
    }
}
