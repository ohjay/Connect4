package Connect4;

import java.awt.CardLayout;
import javax.swing.JPanel;

/**
 * A collection of game panels, for use by any aforementioned panels 
 * that want to transition into each other.
 * @author Owen Jow
 */
public class Panels {
    static JPanel contentPanel;
    static VsCPUMenuPanel vsCPUMenuPanel;
    static VsHumanMenuPanel vsHumanMenuPanel;
    static SpecialMenuPanel specialMenuPanel;
    static BoardsMenuPanel boardsMenuPanel;
    static EasyPanel easyPanel;
    static SolvedPanel solvedPanel;
    static TwoPlayerPanel twoPlayerPanel;
    static WarfarePanel warfarePanel;
    static FourByTwoPanel fourByTwoPanel;
    static RemovalPanel removalPanel;
    static BoardsPanel boardsPanel;
    static CardLayout layout;
}
