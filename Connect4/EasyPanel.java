package Connect4;

import java.awt.event.MouseAdapter;

public class EasyPanel extends ReguC4Panel {
    
    public EasyPanel() {
        super();
        mouseListener = new EasyPMouseListener();
    }
    
    public class EasyPMouseListener extends MouseAdapter {
        /* To do: add mouse listener behavior */
    }
}
