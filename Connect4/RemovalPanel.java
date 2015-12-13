package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import javafx.scene.shape.Circle;

/**
 * The panel for the Removal special mode, in which a player can
 * opt to remove a piece as his/her turn.
 */
public class RemovalPanel extends ReguC4Panel {
    /**
     * Constructor. Creates a mouse listener and sets the background.
     */
    public RemovalPanel() {
        mouseListener = new RemovalMouseListener();
        bgId = 2;
    }
    
    /**
     * Activates the panel with the correct board.
     */
    @Override
    public void activate() {
        requestFocus();
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);
        timer = new Timer(1, this);
        timer.start();
        board = new RemovalBoard();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (board.isPieceFalling || board.isColumnFalling()) {
            board.animateFallingPiece();
        }
        
        repaint();
    }
    
    /**
     * A mouse listener for two-player "regular board" Connect4.
     */
    class RemovalMouseListener extends MouseAdapter {
        /**
         * Checks if the user clicked on a piece, 
         * in which case we will remove that piece from the board.
         * 
         * Otherwise, proceeds as normal (and just drops a piece into a column).
         */
        public void mouseClicked(MouseEvent evt) {
            if (board.interactivePiece != null && !board.isPieceFalling && !board.isColumnFalling()) {
                if (board.shouldHidePiece) {
                    // The user may be trying to remove a piece. Let's see if they clicked on one:
                    // 1) Calculate the approximate array indices of the mouse coordinates
                    int mouseY = evt.getY();
                    int rowIndex = (int) (mouseY - ReguBoard.TOP_OFFSET) / ReguBoard.SQUARE_WIDTH;
                    int colIndex = (int) (MouseData.x - ReguBoard.L_OFFSET) / ReguBoard.SQUARE_WIDTH;
                    
                    // 2) See if that piece is the property of the current player
                    Piece piece = board.getBoard()[rowIndex][colIndex];
                    if (piece == null || piece.color != board.getCurrPlayer()) {
                        return; // if not, we can't go through with this
                    }
                    
                    // 3) See if we actually clicked on the piece
                    Circle pieceCirc = new Circle(piece.getX() + Piece.RADIUS, 
                            piece.getY() + Piece.RADIUS, Piece.RADIUS);
                    if (pieceCirc.intersects(MouseData.x, mouseY, 1, 1)) {
                        // ... we did
                        board.removePiece(rowIndex, colIndex); // so remove the piece. :)
                    }
                } else {
                    int nearestCol = ReguBoard.getNearestCol(MouseData.x);
                    if (!board.isColumnFull(nearestCol)) {
                        board.addToColumn(board.interactivePiece, nearestCol);
                    }
                }
            }
        }
        
        /**
         * Standard stuff. Registers mouse movement.
         * Also, if the user's cursor is currently contained by the board,
         * this method will hide the interactive piece.
         */
        public void mouseMoved(MouseEvent evt) {
            // Positional coordinates
            int mouseX = evt.getX();
            int mouseY = evt.getY();
            
            if (mouseX <= Connect4.WINDOW_LEN - Piece.WIDTH) {
                MouseData.x = mouseX;
                if (!board.isPieceFalling) {
                    board.interactivePiece.setX(mouseX);
                }
            }
            
            board.shouldHidePiece = (mouseX > ReguBoard.L_OFFSET 
                    && mouseX < ReguBoard.L_OFFSET + ReguBoard.BOARD_WIDTH * ReguBoard.SQUARE_WIDTH
                    && mouseY > ReguBoard.TOP_OFFSET 
                    && mouseY < ReguBoard.TOP_OFFSET + ReguBoard.BOARD_HEIGHT * ReguBoard.SQUARE_WIDTH);
        }
    }
}
