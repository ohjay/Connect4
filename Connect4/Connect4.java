package Connect4;

import java.awt.CardLayout;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The classic game of Connect Four, enhanced with several modern modifications.
 * Built initially as a demonstration to a friend that the game is solved.
 * Modes of gameplay include:
 * - Easy mode (in which the computer will make mistakes)
 * - Solved mode (in which the computer will play perfectly)
 * - Two-player: single-computer (in which two humans play on one computer)
 * - Two-player: multiple-computer (in which humans play on different computers)
 * - Warfare (in which 3 or 4 players can play at the same time)
 * - Four by Two (in which players can place two at a time)
 * - Removal (in which players can opt to remove a piece as their turn, w/ up to 5 removals total)
 * - Boards (in which boards are differently shaped)
 * @author Owen Jow
 */
public class Connect4 extends JApplet {
    static final int WINDOW_LEN = 600, WINDOW_WIDTH = 600, WINDOW_X = 121, 
            WINDOW_Y = 121;
    
    public static void main(String[] args) {
        initializeGameData(); // so that the game doesn't lag anywhere while doing this
        launchWindow();
    }
    
    /**
     * Initializes any game data that might require relatively significant processing time to load.
     * Among the things set up are game panels, the card layout structure, 
     */
    private static void initializeGameData() {
        // Initialize base JPanel and card layout
        Panels.contentPanel = new JPanel();
        Panels.contentPanel.setLayout(new CardLayout());
        Panels.layout = (CardLayout) Panels.contentPanel.getLayout();
        
        // Initialize menu panels
        Panels.mainMenuPanel = new MainMenuPanel();
        Panels.contentPanel.add(Panels.mainMenuPanel, "mainMenu");
        Panels.vsCPUMenuPanel = new VsCPUMenuPanel();
        Panels.contentPanel.add(Panels.vsCPUMenuPanel, "vsCPUMenu");
        Panels.vsHumanMenuPanel = new VsHumanMenuPanel();
        Panels.contentPanel.add(Panels.vsHumanMenuPanel, "vsHumanMenu");
        Panels.specialMenuPanel = new SpecialMenuPanel();
        Panels.contentPanel.add(Panels.specialMenuPanel, "specialMenu");
        Panels.boardsMenuPanel = new BoardsMenuPanel();
        Panels.contentPanel.add(Panels.boardsMenuPanel, "boardsMenu");
        
        // Initialize game mode panels
        Panels.easyPanel = new EasyPanel();
        Panels.contentPanel.add(Panels.easyPanel, "easy");
        Panels.solvedPanel = new SolvedPanel();
        Panels.contentPanel.add(Panels.solvedPanel, "solved");
        Panels.twoPlayerPanel = new TwoPlayerPanel();
        Panels.contentPanel.add(Panels.twoPlayerPanel, "twoPlayer");
        Panels.warfarePanel = new WarfarePanel();
        Panels.contentPanel.add(Panels.warfarePanel, "warfare");
        Panels.fourByTwoPanel = new FourByTwoPanel();
        Panels.contentPanel.add(Panels.fourByTwoPanel, "fourByTwo");
        Panels.removalPanel = new RemovalPanel();
        Panels.contentPanel.add(Panels.removalPanel, "removal");
        Panels.boardsPanel = new BoardsPanel();
        Panels.contentPanel.add(Panels.boardsPanel, "boards");
    }
    
    /**
     * Launches the game window, which will contain everything that the user sees.
     */
    private static void launchWindow() {
        JFrame window = new JFrame("Connect4");
        window.setSize(WINDOW_LEN, WINDOW_WIDTH);
        window.setLocation(WINDOW_X, WINDOW_Y);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        
        Panels.mainMenuPanel.activate();
        Panels.currPanel = Panels.mainMenuPanel;
        window.setContentPane(Panels.contentPanel);
        window.setVisible(true);
    }
    
    /**
     * Returns the game to the main menu (from the current panel, which is presumably
     * not the main menu).
     */
    static void returnToMainMenu() {
        Panels.currPanel.deactivate(); // deactivate the current panel...
        Panels.layout.show(Panels.contentPanel, "mainMenu"); // go back to the main menu...
        Panels.mainMenuPanel.activate(); // ACTIVATE the main menu...
        Panels.currPanel = Panels.mainMenuPanel; // aaand update the current panel
    }
}
