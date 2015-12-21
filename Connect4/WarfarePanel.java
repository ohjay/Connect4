package Connect4;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;

/**
 * A panel in which four players can duke it out on a double-sized board.
 *
 * Game logic is very similar to that of a regular two-player version;
 * the main differences are that the board is bigger and control rotates
 * among four players instead of two.
 * @author Owen Jow
 */
public class WarfarePanel extends KPanel {
    private WarfareBoard board;
    
    public WarfarePanel() {
        mouseListener = new WarfareMouseListener();
    }
    
    @Override
    public void activate() {
        super.activate();
        board = new WarfareBoard();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.BLACK);
        board.draw((Graphics2D) g, 7); // 7 being no background
    }
    
    class WarfareMouseListener extends MouseAdapter {
        /**
         * If the game is over, we'll return to the main menu.
         * Otherwise, if the interactive piece is currently positioned over an valid square,
         * we'll place it there.
         */
        public void mouseClicked(MouseEvent evt) {
            if (board.gameOver || board.toMMHighlighted) {
                Connect4.returnToMainMenu();
            } else if (MouseData.x > board.leftOffset 
                    && MouseData.x < board.leftOffset + board.boardWidth * board.squareWidth
                    && MouseData.y > board.topOffset 
                    && MouseData.y < board.topOffset + board.boardHeight * board.squareWidth) {
                // Set the player's piece on the board... if the square they clicked is open
                int rowIndex = (int) (MouseData.y - board.topOffset) / board.squareWidth;
                int colIndex = (int) (MouseData.x - board.leftOffset) / board.squareWidth;
                if (board.squareUnoccupied(rowIndex, colIndex)) {
                    board.addToSquare(board.interactivePiece, rowIndex, colIndex);
                }
            }
        }
        
        public void mouseMoved(MouseEvent evt) {
            if (!board.gameOver) {
                MouseData.x = evt.getX();
                MouseData.y = evt.getY();
                board.interactivePiece.setXY(MouseData.x - Piece.SMALL_WIDTH / 2, 
                        MouseData.y - Piece.SMALL_WIDTH / 2); // center the piece around the cursor
                
                if (Board.WF_MM_RECT.contains(MouseData.x, MouseData.y)) {
                    board.toMMHighlighted = true;
                } else {
                    board.toMMHighlighted = false;
                }
            }
        }
    }
}
