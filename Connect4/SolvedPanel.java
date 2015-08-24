package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

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
            minimax(reguBoard, 0, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
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
    private int maxDepth = 9; /* maxDepth should always be an odd number, so that heuristicEval 
                                * will only ever be called right after the computer has moved */
    
    /**
     * Evaluates all game possibilities with the minimax algorithm and returns the computer's 
     * maximal heuristic game score. Will also set the COMPUTER_MOVE variable to the value
     * of the optimal column in which the computer can play. This method will always assume
     * that the computer plays as the color COMPUTER_COLOR, which is given as an instance
     * variable of the SolvedPanel class.
     * 
     * @param board the board over which the algorithm is being run
     * @param depth the number of turns (/current recursive depth). Always begins at 0
     * @param prevPiece the piece that was previously played
     * @param alpha the maximum score that the computer is guaranteed to get
     * @param beta the minimum score that the player is guaranteed to get
     * @return the column in which the computer should play next (as an integer from 0-6)
     */
    public int minimax(ReguBoard board, int depth, Piece prevPiece, int alpha, int beta) {
        // Heuristic scoring
        if (prevPiece != null && board.makesFour(prevPiece)) {
            if (depth % 2 == 1) {
                // Victory for the previous side, which here is a positive result
                return 50 - depth; // (win as quickly as possible!)
            } else {
                // For a projected loss, optimize by extending the game for as long as possible
                return depth - 50;
            }
        } else if (board.isBoardFull()) {
            return 0; // heuristic score for a draw
        } else if (depth == maxDepth) { // if we go any farther, we'll be in recursive Limbo forever
            return heuristicEval(board);
        }
        
        // The game isn't over, so we'll continue the recursion
        if (board.getCurrPlayer().equals(computerColor)) {
            // Since it's the computer's turn, we want the MAX heuristic score
            int maxScore = Integer.MIN_VALUE; 
            for (int c = ReguBoard.BOARD_WIDTH - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                    
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                    
                    // Check if the move yields the best score we've seen so far
                    int score = minimax(b, depth + 1, p, alpha, beta);
                    if (score > maxScore) {
                        maxScore = score;
                        computerMove = c;
                        
                        // Alpha-beta pruning (alpha cutoff)
                        alpha = Math.max(alpha, maxScore);
                        if (beta <= alpha) { break; }
                    }
                }
            }
            
            return maxScore;
        } else {
            // It's the player's turn, whose score we want to MINimize
            int minScore = Integer.MAX_VALUE;
            for (int c = ReguBoard.BOARD_WIDTH - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                
                    int score = minimax(b, depth + 1, p, alpha, beta);
                    if (score < minScore) {
                        minScore = score;
                        computerMove = c;
                        
                        // Cut off fruitless subtrees (beta cutoff)
                        beta = Math.min(beta, minScore);
                        if (beta <= alpha) { break; }
                    }
                }
            }
            
            return minScore;
        }
    }
    
    /**
     * Heuristically evalutes a Connect4 board and assigns to it a score 
     * representing the strength of the computer's position. [This is the 
     * value that will be returned.]
     * In order to quantify positional strength, this method will take into account
     * the number of connected threes and twos for each side. We will assume
     * that there exist no four-in-a-rows for either player.
     * @param board the Connect4 board to evaluate
     * @return a score heuristic
     */
    public int heuristicEval(ReguBoard board) {
        int heuristicScore = 0; // the total score
        
        Piece[][] boardArr = board.getBoard();
        int height = ReguBoard.BOARD_HEIGHT, width = ReguBoard.BOARD_WIDTH;
        String oneColor = "", twoColor = ""; // the color of length 1/2 connected chains
        
        // Check for vertical threes and twos
        for (int c = 0; c < width; c++) {
            for (int r = height - 1; r >= 1; r--) { // top row is irrelevant
                if (boardArr[r][c] == null) {
                    if (!twoColor.isEmpty()) {
                        // Connect 2, with potential
                        heuristicScore += (twoColor.equals(computerColor)) ? 2 : -2;
                    } 
                    
                    oneColor = ""; twoColor = ""; // reset values
                    break; 
                } else if (twoColor.equals(boardArr[r][c].color)) {
                    if (boardArr[r - 1][c] == null) {
                        // Connect 3, with potential
                        heuristicScore += (twoColor.equals(computerColor)) ? 5 : -5;
                    } else {
                        // Connect 3 nullified by the opposing side
                        heuristicScore += (twoColor.equals(computerColor)) ? -7 : 7;
                    }
                    oneColor = ""; twoColor = "";
                    break;
                } else if (oneColor.equals(boardArr[r][c].color)) {
                    // Connect 2... so far
                    twoColor = oneColor;
                    oneColor = "";
                } else {
                    // The chain was broken by the opposite color
                    if (!twoColor.isEmpty()) {
                        // Give the breaker a score bonus!
                        heuristicScore += (twoColor.equals(computerColor)) ? -5 : 5;
                        twoColor = "";
                    } else if (!oneColor.isEmpty()) {
                        heuristicScore += (oneColor.equals(computerColor)) ? -3 : 3;
                    }
                    oneColor = boardArr[r][c].color;
                }
            }
            
            oneColor = ""; twoColor = ""; // resetting these for the next loop iteration
        }
        
        // Check for horizontal threes and twos [also give bonuses for each column]
        for (int r = height - 1; r >= 0; r--) {
            for (int c = 0; c < width; c++) {
                if (boardArr[r][c] == null) {
                    if (!twoColor.isEmpty()) {
                        // There were two pieces connected (with potential)
                        heuristicScore += (twoColor.equals(computerColor)) ? 2 : -2;
                    }
                    oneColor = ""; twoColor = "";
                } else {
                    // Add in a bonus for each piece (we'll give more points to pieces near the center)
                    if (boardArr[r][c] != null) {
                        heuristicScore += (twoColor.equals(computerColor)) ? 
                                4 - Math.abs(3 - c) : -4 + Math.abs(3 - c);
                    }
                    
                    if (boardArr[r][c].color.equals(twoColor)) {
                        // We have a three in a row
                        if ((c > 2 && boardArr[r][c - 3] == null) || (c < 6 && boardArr[r][c + 1] == null)) {
                            // ...and it has potential!
                            heuristicScore += (twoColor.equals(computerColor)) ? 5 : -5;
                        } else if ((c > 2 && boardArr[r][c - 3] != null) || (c < 6 && boardArr[r][c + 1] != null)) {
                            // Combo breaker :/ ...still, good for one side!
                            heuristicScore += (twoColor.equals(computerColor)) ? -7 : 7;
                        }
                        twoColor = "";
                    } else if (boardArr[r][c].color.equals(oneColor)) {
                        // So far we have two in a row
                        twoColor = oneColor;
                        oneColor = "";
                    } else {
                        // Chain broken by the opposing color!
                        if (!twoColor.isEmpty()) { // assign some bonuses
                            heuristicScore += (twoColor.equals(computerColor)) ? -5 : 5;
                        } else if (!oneColor.isEmpty()) {
                            heuristicScore += (oneColor.equals(computerColor)) ? -3 : 3;
                        }
                        
                        if (c > 2 && !twoColor.isEmpty() && boardArr[r][c - 3] == null) {
                            // There was a Connect 2
                            heuristicScore += (twoColor.equals(computerColor)) ? 2 : -2;
                            twoColor = "";
                        }
                    
                        oneColor = boardArr[r][c].color;
                    }
                }
            }
            
            // Make sure that there wasn't a far-right Connect 2 with potential
            if (!twoColor.isEmpty() && boardArr[r][4] == null) {
                // ...and there was!
                heuristicScore += (twoColor.equals(computerColor)) ? 2 : -2;
            }
            
            oneColor = ""; twoColor = "";
        }
        
        // Check for diagonal threes (note: threes only!)
        for (int c = 0; c < 4; c++) {
            String origColor; // the color of the first piece
            for (int r = 3; r >= 0; r--) {
                if (boardArr[r][c] == null) { break; } // because there can't be any pieces above this one
                origColor = boardArr[r][c].color;
                if (boardArr[r + 1][c + 1] != null && origColor.equals(boardArr[r + 1][c + 1].color)
                        && boardArr[r + 2][c + 2] != null && origColor.equals(boardArr[r + 2][c + 2].color)
                        && ((r > 0 && c > 0 && boardArr[r - 1][c - 1] == null) 
                        || (r < 3 && c < 4 && boardArr[r + 3][c + 3] == null))) {
                    heuristicScore += (origColor.equals(computerColor)) ? 6 : -6; // diagonals are good
                }
            }
            
            for (int r = 5; r >= 2; r--) {
                if (boardArr[r][c] == null) { break; }
                origColor = boardArr[r][c].color;
                if (boardArr[r - 1][c + 1] != null && origColor.equals(boardArr[r - 1][c + 1].color)
                        && boardArr[r - 2][c + 2] != null && origColor.equals(boardArr[r - 2][c + 2].color)
                        && ((r < 5 && c > 0 && boardArr[r + 1][c - 1] == null) 
                        || (r > 2 && c < 4 && boardArr[r - 3][c + 3] == null))) {
                    heuristicScore += (origColor.equals(computerColor)) ? 6 : -6;
                }
            }
        }
        
        return heuristicScore;
    }
}
