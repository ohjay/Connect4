package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

/**
 * A panel in which the user plays Connect4 against a computer.
 * Difficulties include "easy" and "hard".
 * @author Owen Jow
 */
public class VsComputerPanel extends ReguC4Panel {
    private boolean listenerEnabled = true, turn1 = true;
    protected int maxDepth = 42;
    
    public VsComputerPanel() {
        mouseListener = new VsComputerListener();
    }
    
    @Override
    public void activate() {
        super.activate();
        turn1 = true;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (!board.gameOver) {
            if (board.isPieceFalling) {
                board.animateFallingPiece();
            } else if (board.getCurrPlayer().equals(AI.COMPUTER_COLOR)) {
                listenerEnabled = false;
                if (turn1) {
                    board.addToColumn(board.interactivePiece, 3);
                    turn1 = false;
                } else {
                    // Run the minimax algorithm to get the computer's next move
                    board.addToColumn(board.interactivePiece, AI.getBestComputerMove(board, maxDepth));
                }
            } else {
                listenerEnabled = true;
            }
        }
        
        repaint();
    }
    
    class VsComputerListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (board.gameOver || board.toMMHighlighted) {
                Connect4.returnToMainMenu();
            } else if (listenerEnabled) {
                if (board.interactivePiece != null && !board.isPieceFalling) {
                    int nearestCol = board.getNearestCol(MouseData.x - Piece.REG_WIDTH / 2);
                    if (!board.isColumnFull(nearestCol)) {
                        board.addToColumn(board.interactivePiece, nearestCol);
                    }
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (!board.gameOver) {
                MouseData.x = evt.getX();
                if (listenerEnabled) {
                    if (MouseData.x >= Piece.REG_WIDTH / 2
                            && MouseData.x <= Connect4.WINDOW_LEN - Piece.REG_WIDTH / 2) {
                        if (!board.isPieceFalling) {
                            board.interactivePiece.setX(MouseData.x - Piece.REG_WIDTH / 2);
                        }
                    }
                }
                
                if (Board.TO_MM_RECT.contains(MouseData.x, evt.getY())) {
                    board.toMMHighlighted = true;
                } else {
                    board.toMMHighlighted = false;
                }
            }
        }
    }
}
