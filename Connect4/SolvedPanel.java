package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.util.Arrays;

/**
 * The panel in which a user plays Connect4 against a computer of HARD difficulty.
 * (This is another way of saying that the computer will play perfectly.)
 * @author Owen Jow
 */
public class SolvedPanel extends ReguC4Panel {
    private boolean listenerEnabled = true;
    
    public SolvedPanel() {
        mouseListener = new SolvedMouseListener();
        bgId = 4;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (reguBoard.isPieceFalling) {
            reguBoard.animateFallingPiece();
        } else if (reguBoard.getCurrPlayer().equals(computerColor)) {
            listenerEnabled = false;
            // Run the minimax algorithm, which will update computerMove
            minimax(reguBoard, 0, null);
            reguBoard.addToColumn(reguBoard.interactivePiece, computerMove);
        } else {
            listenerEnabled = true;
        }
        
        repaint();
    }
    
    class SolvedMouseListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (listenerEnabled) {
                if (reguBoard.interactivePiece != null && !reguBoard.isPieceFalling) {
                    int nearestCol = ReguBoard.getNearestCol(MouseData.x);
                    if (!reguBoard.isColumnFull(nearestCol)) {
                        reguBoard.addToColumn(reguBoard.interactivePiece, nearestCol);
                    }
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (listenerEnabled) {
                if (evt.getX() <= Connect4.WINDOW_LEN - Piece.WIDTH) {
                    MouseData.x = evt.getX();
                    if (!reguBoard.isPieceFalling) {
                        reguBoard.interactivePiece.setX(MouseData.x);
                    }
                }
            }
        }
    }
    
    //================================================================================
    // Minimax solution logic (with alpha-beta pruning)
    //================================================================================
    
    private String computerColor = "black";
    private int computerMove; // the computer's next move, determined by the algorithm below
    
    /**
     * Evaluates all game possibilities with the minimax algorithm and returns the computer's 
     * maximal heuristic game score. Will also set the COMPUTER_MOVE variable to the value
     * of the optimal column in which the computer can play. This method will always assume
     * that the computer plays as the color COMPUTER_COLOR, which is given as an instance
     * variable of the SolvedPanel class.
     * 
     * @param board the board over which the algorithm is being run
     * @param depth the number of turns (/current recursive depth)
     * @param prevPiece the piece that was previously played
     * @return the column in which the computer should play next (as an integer from 0-6)
     */
    public int minimax(ReguBoard board, int depth, Piece prevPiece) {
        // Heuristic scoring
        if (prevPiece != null && board.makesFour(prevPiece)) {
            if (prevPiece.color.equals(computerColor)) {
                // The computer is the victor, which here is a positive result
                return 50 - depth; // (win as quickly as possible!)
            } else {
                // For a projected loss, optimize by extending the game for as long as possible
                return depth - 50;
            }
        } else if (board.isBoardFull()) {
            return 0; // heuristic score for a draw
        }
        
        // The game isn't over, so we'll continue the recursion
        int[] scores = new int[7]; // scores for each of the 7 potential moves
        for (int c = ReguBoard.BOARD_WIDTH - 1; c >= 0; c--) {
            if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                // Create a piece for the computer to [theoretically] place
                int lowRow = board.lowestOpenRow(c);
                Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                // Simulate the new board configuration
                Piece[][] newBoard = board.cloneBoard();
                newBoard[lowRow][c] = p;
                
                // Add the score for this move to the scores array
                scores[c] = minimax(new ReguBoard(newBoard, board.getNumPieces() + 1, 
                        board.otherPlayer()), depth + 1, p);
            } else {
                scores[c] = Integer.MIN_VALUE;
            }
        }
        
        // Min/max selection
        if (board.getCurrPlayer().equals(computerColor)) {
            // Since it's the computer's turn, we want the MAX heuristic score
            computerMove = maxValueIndex(scores);
            return scores[computerMove];
        } else {
            // As the computer, we want to MINimize the score for the player
            computerMove = minValueIndex(scores);
            return scores[computerMove];
        }
    }
    
    /**
     * Returns the index of the maximum value in the integer array ARR.
     * @param the array to be considered
     * @return array index of the max value
     */
    private static int maxValueIndex(int[] arr) {
        // Initial values will correspond to the last item in the array
        int maxIndex = arr.length - 1;
        int maxValue = arr[maxIndex];
        
        int value; // in order to cut down on the number of array references
        for (int i = maxIndex - 1; i >= 0; i--) { // descend backward just to be cool
            value = arr[i];
            if (value > maxValue) {
                maxIndex = i;
                maxValue = value;
            }
        }
        
        return maxIndex;
    }
    
    /**
     * Returns the index of the minimum value in the integer array ARR.
     * @param the array to be considered
     * @return array index of the min value
     */
    private static int minValueIndex(int[] arr) {
        int minIndex = arr.length - 1;
        int minValue = arr[minIndex];
        
        int value;
        for (int i = minIndex - 1; i >= 0; i--) {
            value = arr[i];
            if (value < minValue) {
                minIndex = i;
                minValue = value;
            }
        }
        
        return minIndex;
    }
}
